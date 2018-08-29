package com.ghx.cc.commitment.webservices.rest.filter;

import static com.ghx.cc.commitment.webservices.rest.RestConstants.HEADER_ASYNC_CALLBACK_URL;
import static com.ghx.cc.commitment.webservices.rest.RestConstants.JOBS_PATH;
import static com.ghx.cc.commitment.webservices.rest.RestConstants.JOB_ID;
import static com.ghx.cc.commitment.webservices.rest.RestConstants.SSL_CLIENT_CN;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.xml.sax.SAXException;

import com.ghx.cc.commitment.business.AsynchronousJobBusiness;
import com.ghx.cc.commitment.dto.GpoDTO;
import com.ghx.cc.commitment.dto.JobDTO;
import com.ghx.cc.commitment.enumeration.JobStatusEnum;
import com.ghx.cc.commitment.enumeration.OutboundRestCallStatusEnum;
import com.ghx.cc.commitment.exception.PayloadParseException;
import com.ghx.cc.commitment.service.GpoService;
import com.ghx.cc.commitment.util.QuartzUtil;
import com.ghx.cc.commitment.util.RestClientUtil;
import com.ghx.cc.commitment.webservices.rest.annotation.Asynchronous;
import com.ghx.cc.commitment.webservices.rest.exception.RESTRuntimeExceptionMapper;
import com.ghx.cc.commitment.webservices.rest.util.AsynchronousUtil;
import com.ghx.cc.commitment.webservices.rest.util.JAXBUtil;
import com.ghx.cc.commitment.webservices.rest.util.Param;
import com.ghx.cc.commitment.webservices.rest.util.RestHelper;
import com.ghx.cc.commitment.webservices.rest.util.RestMethodDetail;
import com.ghx.cc.commitment.webservices.rest.validation.AbstractValidatingReader;
import com.ghx.cc.commitment.webservices.rest.vo.response.HeaderVO;
import com.ghx.cc.commitment.webservices.rest.vo.response.HeadersVO;
import com.ghx.cc.commitment.webservices.rest.vo.response.ResponseVO;
import com.ghx.cc.commitment.work.shared.ActivityHelper;
import com.google.common.net.HttpHeaders;

/**
 * @author dgruber
 */
@Asynchronous
@Priority(2)
public class AsynchronousFilter implements ContainerRequestFilter {

	@Autowired
	private AsynchronousJobBusiness asynchronousJobBusiness;
	
	@Autowired
	private GpoService gpoService;
	
	@Autowired
	@Qualifier("rinScheduler")
	private Scheduler rinScheduler;
	
	@Autowired
	@Qualifier("rinScheduler2")
	private Scheduler rinScheduler2;
	
	@Autowired
	@Qualifier("rinScheduler3")
	private Scheduler rinScheduler3;
	
	@Autowired
	@Qualifier("rinScheduler4")
	private Scheduler rinScheduler4;
	
	@Autowired
	@Qualifier("rinScheduler5")
	private Scheduler rinScheduler5;
	
	private int counter = 0;

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		try {
			if(AsynchronousUtil.processAsynchronously(containerRequestContext.getHeaders())) {
				JobDTO jobDTO = this.prepareJobDTO(containerRequestContext);
				int jobId = asynchronousJobBusiness.persistJobDTO(jobDTO);
				this.createQuartzJob(jobId, jobDTO.getResourceMethodName());
				containerRequestContext.abortWith(generateResponse(jobId));
			}
		} catch(SAXException e) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new PayloadParseException(new JAXBException(e)));
			containerRequestContext.abortWith(response);
		} catch (WebApplicationException e) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new WebApplicationException(e.getMessage(), Status.BAD_REQUEST));
			containerRequestContext.abortWith(response);
		} catch (Throwable t) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new RuntimeException(t.getMessage()));
			containerRequestContext.abortWith(response);
		}
	}

	private JobDTO prepareJobDTO(ContainerRequestContext containerRequestContext) throws IOException, SAXException {

		UriRoutingContext routingContext = (UriRoutingContext) containerRequestContext.getUriInfo();
		ResourceMethodInvoker invoker = (ResourceMethodInvoker) routingContext.getInflector();

		RestMethodDetail restMethodDetail = RestMethodDetail.getRestMethodDetailInstance(invoker.getResourceMethod());
		String payload = IOUtils.toString(containerRequestContext.getEntityStream());
		
		if(AbstractValidatingReader.getValidationSchema(restMethodDetail.getPayloadClass()) != null)  {
			JAXBUtil.validatePayload(payload, AbstractValidatingReader.getValidationSchema(restMethodDetail.getPayloadClass()));
		}

		JobDTO jobDTO = new JobDTO();
		jobDTO.setStatus(JobStatusEnum.NEW);

		jobDTO.setRequestUrl(containerRequestContext.getUriInfo().getRequestUri().toString());

		jobDTO.setResourceClassName(invoker.getResourceClass().getName());
		jobDTO.setResourceMethodName(invoker.getResourceMethod().getName());

		List<Param> params = RestHelper.deriveParamsFromRequest(containerRequestContext, restMethodDetail);
		jobDTO.setParams(params);

		jobDTO.setPayload(payload);

		String callbackUrl = containerRequestContext.getHeaderString(HEADER_ASYNC_CALLBACK_URL);
		jobDTO.setCallbackUrl(callbackUrl);
		
		if (callbackUrl == null) {
			jobDTO.setCallbackStatus(OutboundRestCallStatusEnum.NA);
		} else {
			jobDTO.setCallbackStatus(OutboundRestCallStatusEnum.PENDING);
		}
		
		String sslClientCn = containerRequestContext.getHeaderString(SSL_CLIENT_CN);
		GpoDTO gpoDTO = gpoService.getGpoBySslClientCN(sslClientCn);
		String orgId = gpoDTO.getOrgId();
		
		if(orgId != null) {
			jobDTO.setOrgId(Integer.parseInt(orgId));
		}
		jobDTO.setHttpMethod(containerRequestContext.getMethod());

		return jobDTO;
	}

	private void createQuartzJob(int jobId, String jobName) throws SchedulerException {
		JobDetail jobDetail = (JobDetail) ActivityHelper.getBean(jobName);
		SimpleTrigger trigger = QuartzUtil.buildTrigger(jobName + jobId, "RIN", new Date(), jobDetail);
		trigger.getJobDataMap().put(JOB_ID, jobId);
		scheduleJob(trigger);
	}
	
	private void scheduleJob(SimpleTrigger trigger) throws SchedulerException {
		switch (counter % 5) {
			case 1:
				rinScheduler.scheduleJob(trigger);
				break;
				
			case 2:
				rinScheduler2.scheduleJob(trigger);
				break;
				
			case 3:
				rinScheduler3.scheduleJob(trigger);
				break;
				
			case 4:
				rinScheduler4.scheduleJob(trigger);
				break;
			
			default:
				rinScheduler5.scheduleJob(trigger);
				break;
		}
		incrementCounterSynchronized();
	}

	private synchronized void incrementCounterSynchronized() {
		if(counter == 5) {
			counter = 0;
		}
		counter++;
	}

	protected Response generateResponse(int jobId) {
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatusCode(Status.ACCEPTED.getStatusCode());
		
		String jobUrl = RestClientUtil.prepareUrl(String.valueOf(jobId), JOBS_PATH, JOB_ID).toString();
		
		HeadersVO headers = new HeadersVO();
		
		HeaderVO header = new HeaderVO();
		header.setName(HttpHeaders.LOCATION);
		header.setValue(jobUrl);
		headers.getHeaders().add(header);
		
		header = new HeaderVO();
		header.setName(HttpHeaders.CONTENT_TYPE);
		header.setValue(MediaType.APPLICATION_XML);
		headers.getHeaders().add(header);
		
		responseVO.setHeaders(headers);
		
		return Response.status(responseVO.getStatusCode()).entity(responseVO).type(MediaType.APPLICATION_XML).header(HttpHeaders.LOCATION, jobUrl).build();
	}
}