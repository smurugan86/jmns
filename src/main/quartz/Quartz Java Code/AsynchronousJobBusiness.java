package com.ghx.cc.commitment.business;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.ghx.cc.commitment.dto.JobDTO;
import com.ghx.cc.commitment.enumeration.JobStatusEnum;
import com.ghx.cc.commitment.enumeration.OutboundRestCallStatusEnum;
import com.ghx.cc.commitment.exception.JobIdNotFoundException;
import com.ghx.cc.commitment.resource.XmlConstants;
import com.ghx.cc.commitment.service.JobService;
import com.ghx.cc.commitment.webservices.rest.exception.RESTRuntimeExceptionMapper;
import com.ghx.cc.commitment.webservices.rest.util.JAXBUtil;
import com.ghx.cc.commitment.webservices.rest.util.RestHelper;
import com.ghx.cc.commitment.webservices.rest.vo.response.OfferRevisionAcknowledgementResponseVO;
import com.ghx.cc.commitment.webservices.rest.vo.response.ResponseVO;
import com.ghx.cc.commitment.work.shared.ActivityHelper;

/**
 * @author dgruber
 */
@Component("asynchronousJobBusiness")
public class AsynchronousJobBusiness {
	
	@Autowired
	private JobService jobService;
	
	/**
	 * @param containerRequestContext
	 * @return
	 */
	public int persistJobDTO(JobDTO jobDTO) {
		return jobService.createJobRecord(jobDTO);
	}

	public JobDTO getJobDTOByJobId(int jobId) {
		try {
			return jobService.getJobDTOByJobId(jobId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new JobIdNotFoundException(jobId);
		}
	}

	public int moveJobToInprogress(int jobId) {
		return jobService.moveJobToInprogress(jobId);
	}

	public int updateJobSummaryStatus(JobDTO jobDTO) {
		return jobService.updateJobSummaryStatus(jobDTO);
	}

	public void updatedCallbackStatus(int jobId, OutboundRestCallStatusEnum outboundRestCallStatus) {
		jobService.updatedCallbackStatus(jobId, outboundRestCallStatus);
	}

	public int updateCallbackReference(int jobId, int callbackId) {
		return jobService.updateCallbackReference(jobId, callbackId);
	}

	public void doUpdateJobTransactional(JobDTO jobDTO) {
		executeRestOperation(jobDTO);
	}
	
	/**
	 * Given a Job record, this method identifies the resourceclass,
	 * resourcemethod to be invoked, then it gets hold of the resource object in
	 * spring context Sets asynchronous UriInfo object on the resource to mimic
	 * UriInfo synchronous rest operations, and Invokes the method, with the
	 * payload and parameters from JobDTO After the return of the invocation,
	 * it maps the response to the status fields in JobDTO
	 * 
	 * All possible exceptions with in the rest operation should be handled with
	 * in this method
	 * 
	 * @param jobDTO
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void executeRestOperation(JobDTO jobDTO) {
		Response response = null;
		
		try{
			Class resourceClass = Class.forName(jobDTO.getResourceClassName());
			Object resource = ActivityHelper.APPLICATION_CONTEXT.getBean(resourceClass);

			response = RestHelper.executeMethodCall(resource, jobDTO.getResourceMethodName(), jobDTO.getRequestUrl(), jobDTO.getPayload(), jobDTO.getParams());
			
			/**
			 * So far, our unsuccessful rest processing has been through 'throwing exception' and mapping it explicitly using RestRuntimeExceptionMapper
			 * If that model changes, and we have unsuccessful rest return codes without an exception beign thrown, 
			 * then, the above call would return successfully, and this job would be marked as success.
			 * If at all, if we get into that situation, there would be a need for additional 'if block' based on http return codes
			 * before marking it as success or failure 
			 */
			
			this.updateJobDTO(jobDTO, JobStatusEnum.SUCCESS, getResponseMessage(response), response.getStatus(), response.getHeaderString(XmlConstants.LOCATION));
			updateJobSummaryStatus(jobDTO);
		}catch (Throwable e) {
			e.printStackTrace();
			response = new RESTRuntimeExceptionMapper().toResponse((RuntimeException) e.getCause());
			this.updateJobDTO(jobDTO, JobStatusEnum.FAILURE, getResponseMessage(response), response.getStatus(), null);
		}
		
		return;
	}

	private void updateJobDTO(JobDTO jobDTO,JobStatusEnum status, String responseString , int opStatusCode, String location) {
		jobDTO.setStatus(status);
		jobDTO.setResourceLocation(location);
		jobDTO.setResponse(responseString);
		jobDTO.setOperationStatusCode(opStatusCode);
	}
	
	private String getResponseMessage(Response response) {
		if(response.getEntity() instanceof ResponseVO || response.getEntity() instanceof OfferRevisionAcknowledgementResponseVO) {
			return JAXBUtil.unmarshallObjectToXml(response.getEntity());
		} else {
			return (String)response.getEntity();
		}
	}
}