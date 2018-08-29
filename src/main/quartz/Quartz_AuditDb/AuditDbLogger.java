package com.ghx.cc.commitment.webservices.rest.filter;

import static com.ghx.cc.commitment.webservices.rest.RestConstants.REST_CALL_INBOUND;
import static com.ghx.cc.commitment.webservices.rest.RestConstants.REST_CALL_OUTBOUND;
import static com.ghx.cc.commitment.webservices.rest.RestConstants.SSL_CLIENT_CERT;
import static com.ghx.cc.commitment.webservices.rest.RestConstants.SSL_CLIENT_CN;

import java.sql.Types;
import java.util.Arrays;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.stereotype.Service;

import com.ghx.cc.commitment.dto.ContractDTO;
import com.ghx.cc.commitment.dto.JobDTO;
import com.ghx.cc.commitment.dto.SupplierDTO;
import com.ghx.cc.commitment.service.ContractService;
import com.ghx.cc.commitment.service.JobService;
import com.ghx.cc.commitment.service.SupplierService;
import com.ghx.cc.commitment.webservices.rest.util.AsynchronousUtil;

@Service(value = "auditDbLogger")
public class AuditDbLogger {
	protected static final Logger LOG = Logger.getLogger(AuditDbLogger.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private JobService jobService;

	private static final int PARAM_LIMIT = 200;
	private static final int HEADER_LIMIT = 500;
	private static final String VENDOR_NUMBER = "VendorNumber";

	private static final String INSERT_AUDIT_WITH_REQUEST = "INSERT INTO REST_AUDIT (RA_AUDIT_ID, RA_REQUEST_DATE, RA_CALL_TYPE, RA_REST_METHOD, RA_REST_URI, "
			+ "RA_REQUEST_HEADERS, RA_REQUEST_PARAMETERS, RA_REQUEST_BODY_BLOB, RA_REMOTE_IP_ADDRESS, RA_AUTH_CN, RA_ERROR_NONCE, RA_ASYNC_FLAG, RA_SUPPLIER_ID)"
			+ " VALUES (?,SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


	private static final String UPDATE_AUDIT_WITH_RES = "UPDATE REST_AUDIT SET RA_AUTH_CN=?, RA_RESPONSE_DATE=SYSDATE, RA_RESPONSE_CODE=?, RA_RESPONSE_HEADERS=?, "
			+ "RA_RESPONSE_BODY_BLOB=?, RA_ERROR_NONCE=? WHERE RA_AUDIT_ID=?";

	private static final String GET_REST_AUDIT_ID = "SELECT REST_AUDIT_ID_SEQ.nextval FROM DUAL";

	public void logRequest(AuditFilterRequest auditFilterRequest) {
		int restAuditId = jdbcTemplate.queryForObject(GET_REST_AUDIT_ID, Integer.class);

		String hdrStr = this.prepareStringFromHashMap(auditFilterRequest
				.getHeaderMap());
		String paramStr = this.prepareStringFromHashMap(auditFilterRequest
				.getParameterMap());

		boolean processAsynchronously = AsynchronousUtil.processAsynchronously(auditFilterRequest.getHeaderMap());

		int supplierId = getSupplierId(auditFilterRequest.getRequestURL().toString(),auditFilterRequest.getContent());
		
		jdbcTemplate.update(
				INSERT_AUDIT_WITH_REQUEST,
				new Object[] {
						restAuditId,
						REST_CALL_INBOUND,
						auditFilterRequest.getMethod(),
						auditFilterRequest.getRequestURL().toString(),
						truncatedString(hdrStr, HEADER_LIMIT),
						truncatedString(paramStr, PARAM_LIMIT),
						new SqlLobValue(auditFilterRequest.getContent()),
						auditFilterRequest.getRemoteAddr(),
						auditFilterRequest.getHeader(SSL_CLIENT_CN),
						null,
						BooleanUtils.toInteger(processAsynchronously),
						supplierId
				},
				new int[] {
						Types.INTEGER,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.BLOB,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.VARCHAR,
						Types.INTEGER,
						Types.INTEGER
				});

		auditFilterRequest.setAuditLogId(restAuditId);
	}

	private String truncatedString(String paramStr, int limit) {
		int length = limit;
		if (paramStr.length() < limit) {
			length = paramStr.length();
		}
		return paramStr.substring(0, length);
	}

	public void logResponse(AuditFilterResponse auditFilterResponse) {
		String hdrStr = this.prepareStringFromHashMap(auditFilterResponse
				.getHeaderMap());
		
		jdbcTemplate.update(UPDATE_AUDIT_WITH_RES,
				new Object[] {
					auditFilterResponse.getAuditRequest().getHeader(SSL_CLIENT_CN),
					auditFilterResponse.getStatusCode(),
					truncatedString(hdrStr, HEADER_LIMIT),
					new SqlLobValue(auditFilterResponse.getContent()),
					auditFilterResponse.getHeaderMap().get("error-nonce"),
					auditFilterResponse.getAuditRequest().getAuditLogId()
				},
				new int[] {
					Types.VARCHAR,
					Types.INTEGER,
					Types.VARCHAR,
					Types.BLOB, 
					Types.VARCHAR, 
					Types.INTEGER 
				});
	}

	public int logRestEntity(String url, String callbackId, Entity<?> entity, MultivaluedMap<String, Object> headerMap) {
		return logRestEntity(url,callbackId,entity,headerMap,null,null,null);
	}
	
	public int logRestEntity(String url, String callbackId, Entity<?> entity, MultivaluedMap<String, Object> headerMap,String requestURL,String requestPayload, String errorNonce) {
		int restAuditId = jdbcTemplate.queryForObject(GET_REST_AUDIT_ID, Integer.class);

		String hdrStr = this.prepareStringFromHashMap(headerMap);
		String paramStr = "";

		int supplierId = getSupplierIdForOutbound(callbackId,requestURL,requestPayload);
		
		jdbcTemplate.update(
				INSERT_AUDIT_WITH_REQUEST,
				new Object[] {
					restAuditId,
					REST_CALL_OUTBOUND,
					"POST",
					url,
					truncatedString(hdrStr, HEADER_LIMIT),
					truncatedString(paramStr, PARAM_LIMIT),
					new SqlLobValue(entity.getEntity().toString()),
					null,
					null,
					errorNonce,
					0,
					supplierId
				},
				new int[] {
					Types.INTEGER,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.BLOB,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.INTEGER,
					Types.INTEGER
				});
		return restAuditId;
	}

	public void logRestResponse(int auditLogId, Response response, String errorNonce) {

		SqlLobValue sqlLob = null;
		response.bufferEntity();
		String responseData = response.readEntity(String.class);
		if(responseData != null) {
			sqlLob = new SqlLobValue(responseData);
		}
		String hdrStr = this.prepareStringFromHashMap(response.getHeaders());
		jdbcTemplate.update(UPDATE_AUDIT_WITH_RES,
				new Object[] {
					null,
					response.getStatus(),
					truncatedString(hdrStr, HEADER_LIMIT),
					sqlLob,
					errorNonce,
					auditLogId
				},
				new int[] {
						Types.VARCHAR,
						Types.INTEGER,
						Types.VARCHAR,
						Types.BLOB,
						Types.VARCHAR,
						Types.INTEGER
				});
	}

	public void logRestException(int auditLogId, Object exceptionMessage, String errorNonce) {
		SqlLobValue sqlLob = null;
		String responseData = exceptionMessage.toString();
		if(responseData != null) {
			sqlLob = new SqlLobValue(responseData);
		}
		jdbcTemplate.update(UPDATE_AUDIT_WITH_RES,
				new Object[] { 
					null,
					500,
					null,
					sqlLob,
					errorNonce,
					auditLogId
				},
				new int[] {
					Types.VARCHAR,
					Types.INTEGER,
					Types.VARCHAR,
					Types.BLOB,
					Types.VARCHAR,
					Types.INTEGER
				});
	}

	@SuppressWarnings("rawtypes")
	private String prepareStringFromHashMap(Map hashMap) {
		StringBuilder propertyStringBuilder = new StringBuilder();
		if (hashMap != null) {
			for (Object key : hashMap.keySet()) {
				if (omitSslHeader(key.toString())) {
					Object value = hashMap.get(key);
					if (value instanceof String[]) {
						value = Arrays.toString((String[]) value);
					}
					propertyStringBuilder.append(key + ":" + value + ";");
				}
			}
		}
		return propertyStringBuilder.toString();
	}

	private boolean omitSslHeader(String header) {
		return !header.equalsIgnoreCase(SSL_CLIENT_CERT)
				&& !header.equalsIgnoreCase(SSL_CLIENT_CN);
	}
	
	
	/************* GETTING SUPPLIERID TO INSERT FOR MIGRATION *************/
	
	private int getSupplierId(String url,String content){
		int supplierId = 0;
		if(url.contains("/migration/")){
			if(isContractURL(url)){
				supplierId = getSupplierIdForMigrationContract(content);
			}else{
				supplierId = getSupplierIdForMigrationOffer(url);
			}
		}
		return supplierId;
	}
	
	private int getSupplierIdForMigrationContract(String content) {
		int supplierId = 0;
		String vendorNumber = getTagValueFromContent(content,VENDOR_NUMBER);
		SupplierDTO supplierDTO = supplierService.getSupplierByVendorId(vendorNumber);
		if(supplierDTO != null)
		{
			supplierId = Integer.parseInt(supplierDTO.getOrgId());
		}
		return supplierId;
	}
	
	private int getSupplierIdForMigrationOffer(String url) {
		int supplierId = 0;
		String contractResourceId = url.split("/contracts/")[1].split("/offers")[0];
		ContractDTO contractDTO = contractService.getContractInfoByContractResourceId(contractResourceId);
		if(contractDTO != null){
			supplierId = contractDTO.getSupplierId();
		}
		return supplierId;
	}
	
	private boolean isContractURL(String url){
		String arr[] = url.split("/contracts/");
		return arr.length == 1;
	}
	
	private String getTagValueFromContent(String content,String tagName){
		String[] arr = content.split(tagName);
		String value= arr[1].replace("</", "").replace(">","");
		return value;
	}
	
	private int getSupplierIdForOutbound(String callbackId,String requestURL,String requestPayload){
		if(requestURL!=null && requestPayload!=null){
			return getSupplierId(requestURL,requestPayload);
		} else {
			if(callbackId != null && jobService.doesCallBackExists(callbackId)) {
				JobDTO jobDto = jobService.getJobByCallbackId(callbackId);
				requestURL = jobDto.getRequestUrl();
				requestPayload = jobDto.getPayload();
				return getSupplierId(requestURL,requestPayload);
			}
		}
		return 0;
	}
}
