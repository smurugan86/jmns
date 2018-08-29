package com.ghx.cc.commitment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import com.ghx.cc.commitment.dto.JobCallbackDTO;
import com.ghx.cc.commitment.dto.JobDTO;
import com.ghx.cc.commitment.enumeration.JobStatusEnum;
import com.ghx.cc.commitment.enumeration.OutboundRestCallStatusEnum;

/**
 * @author dgruber
 *
 */
@Repository("jobDao")
public class JobDao extends AbstractBaseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jobDTO
	 * @return
	 */
	public int createJobRecord(JobDTO jobDTO) {
		int jobId = jdbcTemplate.queryForObject("SELECT RRA_ID_SEQ.nextval FROM dual", Integer.class);

		String sql = "INSERT INTO REST_RECORD_ASYNCHRONOUS(RRA_ID, RRA_STATUS, RRA_REQUEST_URL, " +
					 "RRA_RESOURCE_CLASS_NAME, RRA_RESOURCE_METHOD_NAME, RRA_PAYLOAD_BLOB, RRA_PARAM_BLOB, " +
					 "RRA_CALLBACK_STATUS, RRA_CALLBACK_URL, RRA_INSERT_TIME, RRA_INSERTING_HOST, RRA_HTTP_METHOD, RRA_ORG_ID) " +
					 "VALUES(?,?,?,?,?,?,?,?,?,SYSDATE, ?, ?, ?)";

		jdbcTemplate.update(sql,
					new Object[] { jobId, jobDTO.getStatus(), jobDTO.getRequestUrl() ,
						jobDTO.getResourceClassName(), jobDTO.getResourceMethodName(), jobDTO.getPayloadAsBlob(), jobDTO.getParamsAsBlob(), 
						jobDTO.getCallbackStatus(), jobDTO.getCallbackUrl(), JobDTO.HOST_NAME, jobDTO.getHttpMethod(), jobDTO.getOrgId()}, 
					new int[] {Types.INTEGER, Types.VARCHAR, Types.VARCHAR,  
						Types.VARCHAR, Types.VARCHAR, Types.BLOB,Types.BLOB,
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR , Types.VARCHAR});

		return jobId;
	}
	
	/**
	 * @param jobId
	 * @return
	 */
	public JobDTO getJobDTOByJobId(int jobId) {
		String sql = "SELECT RRA_ID AS id, RRA_STATUS jobStatus, " +
						"RRA_REQUEST_URL AS requestUrl, RRA_RESOURCE_CLASS_NAME AS resourceClassname, RRA_RESOURCE_METHOD_NAME AS resourceMethodName, " +
						"RRA_PAYLOAD_BLOB AS payloadBlob, RRA_PARAM_BLOB AS paramBlob , RRA_RESOURCE_LOCATION AS resourceLocation, RRA_STATUS AS status, " +
						"RRA_CALLBACK_STATUS AS callbackStatus, RRA_CALLBACK_URL AS callbackUrl, RRA_ORG_ID as orgId," +
						"RRA_INSERT_TIME AS insertTime, RRA_UPDATE_TIME AS updateTime, RRA_START_TIME AS startTime, RRA_END_TIME AS endTime, " +
						"RRA_INSERTING_HOST AS insertingHost, RRA_PROCESSING_HOST AS processingHost, RRA_HTTP_METHOD as httpMethod, RRA_OPERATION_STATUS_CODE as operationStatusCode, RRA_RESPONSE_BLOB as response " +
						"FROM REST_RECORD_ASYNCHRONOUS WHERE RRA_ID = ?";

		return jdbcTemplate.queryForObject(sql, new Object[]{jobId}, new int[]{Types.INTEGER}, jobDTORowMapper);
	}

	public int moveJobToInprogress(int jobId) {
		String updateStatusStmt = "UPDATE REST_RECORD_ASYNCHRONOUS SET RRA_STATUS=?, RRA_START_TIME=SYSDATE, RRA_UPDATE_TIME=SYSDATE, RRA_PROCESSING_HOST=? WHERE RRA_ID = ?";
		return jdbcTemplate.update(updateStatusStmt, new Object[]{JobStatusEnum.INPROGRESS.toString(), JobDTO.HOST_NAME, jobId});
	}
	
	public int updateJobSummaryStatus(JobDTO jobDTO) {
		String updateSummaryStmt = "UPDATE REST_RECORD_ASYNCHRONOUS SET RRA_STATUS=?, RRA_RESPONSE_BLOB=?, RRA_RESOURCE_LOCATION=?, RRA_UPDATE_TIME=SYSDATE, RRA_END_TIME=SYSDATE, RRA_OPERATION_STATUS_CODE=? WHERE RRA_ID=?";

		return jdbcTemplate.update(updateSummaryStmt, new Object[]{jobDTO.getStatus().toString(), jobDTO.getResponseAsBlob(), jobDTO.getResourceLocation(), jobDTO.getOperationStatusCode(), jobDTO.getId()},
				new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
	}

	public int updateCallbackReference(int jobId, int callbackId) {
		String updateSummaryStmt = "UPDATE REST_RECORD_ASYNCHRONOUS SET RRA_OBRC_ID=? WHERE RRA_ID=?";

		return jdbcTemplate.update(updateSummaryStmt, new Object[]{callbackId, jobId},
				new int[]{Types.INTEGER, Types.INTEGER});
	}
	
	/**
	 * @param jobDTO
	 */
	public void updatedCallbackStatus(int jobId, OutboundRestCallStatusEnum callbackStatus) {
		String sql = "UPDATE rest_record_asynchronous SET rra_callback_status = ?, RRA_UPDATE_TIME=SYSDATE WHERE rra_id = ?";
		jdbcTemplate.update(sql, new Object[] {callbackStatus.toString(), jobId});
	}

	public boolean doesCallBackExists(String callbackId) {
		String sql = "SELECT count(*) FROM REST_RECORD_ASYNCHRONOUS WHERE RRA_OBRC_ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {callbackId}, Integer.class) > 0 ? true : false;
	}

	/**
	 * @param callbackId
	 * @return
	 */
	public JobDTO getJobByCallbackId(String callbackId) {
		String sql = "SELECT RRA_ID AS id, " +
				"RRA_REQUEST_URL AS requestUrl, "+
				"RRA_PAYLOAD_BLOB AS payloadBlob " +
				"FROM REST_RECORD_ASYNCHRONOUS WHERE RRA_OBRC_ID = ?";
		
        return jdbcTemplate.queryForObject(sql, new Object[]{callbackId}, new int[]{Types.INTEGER}, jobIdRequestURLandPayloadMapper);
	}
	
	public JobDTO getRequestUrlandCallbackStatusByCallbackId(int callbackId){
		String sql = "select rra.RRA_ID AS id, "+
				" rra.RRA_REQUEST_URL AS requestUrl, "+
				" obrc.OBRC_ID AS callbackId, "+
				" obrc.OBRC_STATUS AS callbackStatus "+
				" from REST_RECORD_ASYNCHRONOUS rra  "+
				" INNER JOIN  Outbound_rest_call obrc ON obrc.OBRC_ID = rra.RRA_OBRC_ID "+
				" where obrc.OBRC_ID = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{callbackId}, new int[]{Types.INTEGER}, jobIdRequestURLandCallbackStatusMapper);
	}
	
	public JobCallbackDTO getJobCallbackDTOByJobId(int jobId){
		String sql = "SELECT r.RRA_ID AS jobId, "+
				" r.RRA_STATUS AS jobStatus, "+
				" r.RRA_REQUEST_URL AS jobRequestUrl, "+
				" r.RRA_RESPONSE_BLOB AS jobResponse, "+
				" r.RRA_CALLBACK_STATUS AS rraCallbackStatus, "+
				" r.RRA_OPERATION_STATUS_CODE AS rraOpStatusCode, "+
				" r.RRA_CALLBACK_URL AS callbackUrl, "+
				" r.RRA_INSERT_TIME AS jobInsertTime, "+
				" r.RRA_UPDATE_TIME AS jobUpdateTime, "+
				" r.rra_obrc_id AS callbackId, "+
				" o.obrc_response_blob AS callbackResponse, "+
				" o.obrc_status AS callbackStatus, "+
				" o.obrc_return_code AS callbackHttpStatus, "+
				" o.obrc_insert_time AS callbackInsertTime, "+
				" o.obrc_update_time AS callbackUpdateTime "+
				" FROM REST_RECORD_ASYNCHRONOUS r "+
				" LEFT OUTER JOIN outbound_rest_call o ON o.obrc_id = r.rra_obrc_id "+
				" WHERE r.RRA_ID = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{jobId}, new int[]{Types.INTEGER}, JobCallbackDTORowMapper);
	}
	
	final RowMapper<JobDTO> jobIdRequestURLandPayloadMapper = new RowMapper<JobDTO>() {
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public JobDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			JobDTO jobDTO = new JobDTO();
			jobDTO.setId(resultSet.getInt("id"));
			jobDTO.setRequestUrl(resultSet.getString("requestUrl"));
			jobDTO.setPayload(new String(resultSet.getBytes("payloadBlob")));
			return jobDTO;
		}
	};
	
	final RowMapper<JobDTO> jobIdRequestURLandCallbackStatusMapper = new RowMapper<JobDTO>() {
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public JobDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			JobDTO jobDTO = new JobDTO();
			jobDTO.setId(resultSet.getInt("id"));
			jobDTO.setRequestUrl(resultSet.getString("requestUrl"));
			jobDTO.setCallbackId(resultSet.getInt("callbackId"));
			jobDTO.setCallbackStatus(OutboundRestCallStatusEnum.valueOf(resultSet.getString("callbackStatus")));
			return jobDTO;
		}
	};
	
	final RowMapper<JobCallbackDTO> JobCallbackDTORowMapper = new RowMapper<JobCallbackDTO>() {
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public JobCallbackDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			JobCallbackDTO jobCallbackDTO = new JobCallbackDTO();
			jobCallbackDTO.setJobId(resultSet.getInt("jobId"));
			jobCallbackDTO.setJobStatus(JobStatusEnum.valueOf(resultSet.getString("jobStatus")));
			jobCallbackDTO.setJobHttpStatus(resultSet.getString("rraOpStatusCode"));
			jobCallbackDTO.setJobRequestUrl(resultSet.getString("jobRequestUrl"));
			jobCallbackDTO.setJobResponse(resultSet.getBytes("jobResponse") != null ? new String(resultSet.getBytes("jobResponse")) : "");
			jobCallbackDTO.setCallbackId(resultSet.getString("callbackId"));
			jobCallbackDTO.setCallbackUrl(resultSet.getString("callbackUrl"));
			jobCallbackDTO.setCallbackPayload(jobCallbackDTO.getJobResponse());
			jobCallbackDTO.setCallbackResponse(resultSet.getBytes("callbackResponse") != null ? SerializationUtils.deserialize(resultSet.getBytes("callbackResponse")).toString() : "");
			jobCallbackDTO.setCallbackStatus(jobCallbackDTO.getCallbackId() != null ?  OutboundRestCallStatusEnum.valueOf(resultSet.getString("callbackStatus"))
																						: OutboundRestCallStatusEnum.valueOf(resultSet.getString("rraCallbackStatus")));
			jobCallbackDTO.setCallbackHttpStatus(resultSet.getString("callbackHttpStatus") != null ? resultSet.getString("callbackHttpStatus") : "");
			jobCallbackDTO.setCallbackInsertTime(jobCallbackDTO.getCallbackId() != null ? resultSet.getDate("callbackInsertTime") : resultSet.getDate("jobInsertTime"));
			jobCallbackDTO.setCallbackUpdateTime(jobCallbackDTO.getCallbackId() != null ? resultSet.getDate("callbackUpdateTime") : resultSet.getDate("jobUpdateTime"));
			return jobCallbackDTO;
		}
	};
	
	final RowMapper<JobDTO> jobDTORowMapper = new RowMapper<JobDTO>() {
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public JobDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			JobDTO jobDTO = new JobDTO();
			jobDTO.setId(resultSet.getInt("id"));
			jobDTO.setStatus(JobStatusEnum.valueOf(resultSet.getString("jobStatus")));
			jobDTO.setRequestUrl(resultSet.getString("requestUrl"));
			jobDTO.setResourceClassName(resultSet.getString("resourceClassname"));
			jobDTO.setResourceMethodName(resultSet.getString("resourceMethodName"));
			jobDTO.setPayload(new String(resultSet.getBytes("payloadBlob")));
			jobDTO.setParams((List)SerializationUtils.deserialize(resultSet.getBytes("paramBlob")));
			jobDTO.setResourceLocation(resultSet.getString("resourceLocation"));
			jobDTO.setCallbackStatus(OutboundRestCallStatusEnum.valueOf(resultSet.getString("callbackStatus")));
			jobDTO.setCallbackUrl(resultSet.getString("callbackUrl"));
			jobDTO.setInsertTime(resultSet.getDate("insertTime"));
			jobDTO.setUpdateTime(resultSet.getDate("updateTime"));
			jobDTO.setStartTime(resultSet.getDate("startTime"));
			jobDTO.setEndTime(resultSet.getDate("endTime"));
			jobDTO.setInsertingHost(resultSet.getString("insertingHost"));
			jobDTO.setProcessingHost(resultSet.getString("processingHost"));
			jobDTO.setHttpMethod(resultSet.getString("httpMethod"));
			jobDTO.setOperationStatusCode(resultSet.getInt("operationStatusCode"));
			jobDTO.setResponse( resultSet.getBytes("response") != null ? new String(resultSet.getBytes("response")) : null);
			jobDTO.setOrgId(resultSet.getInt("orgId"));

			return jobDTO;
		}
	};

}
