package com.ghx.cc.commitment.webservices.rest.filter;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ghx.cc.commitment.dto.ContractDTO;
import com.ghx.cc.commitment.dto.GpoDTO;
import com.ghx.cc.commitment.dto.HybridOfferDetailDTO;
import com.ghx.cc.commitment.dto.JobDTO;
import com.ghx.cc.commitment.dto.OutboundRestCallDTO;
import com.ghx.cc.commitment.exception.CallbackIdNotFoundException;
import com.ghx.cc.commitment.exception.ContractNotFoundException;
import com.ghx.cc.commitment.exception.InvalidCallbackIdException;
import com.ghx.cc.commitment.exception.InvalidJobIdException;
import com.ghx.cc.commitment.exception.NotAuthorizedForContractException;
import com.ghx.cc.commitment.exception.NotAuthorizedForJobException;
import com.ghx.cc.commitment.exception.NotAuthorizedForOfferException;
import com.ghx.cc.commitment.exception.NotAuthorizedForOutboundRestCallException;
import com.ghx.cc.commitment.exception.OfferNotFoundException;
import com.ghx.cc.commitment.service.ContractService;
import com.ghx.cc.commitment.service.GpoService;
import com.ghx.cc.commitment.service.JobService;
import com.ghx.cc.commitment.service.OfferService;
import com.ghx.cc.commitment.service.OutboundRestCallService;
import com.ghx.cc.commitment.webservices.rest.RestConstants;
import com.ghx.cc.commitment.webservices.rest.annotation.Authorization;
import com.ghx.cc.commitment.webservices.rest.exception.RESTRuntimeExceptionMapper;
import com.ghx.cc.commitment.webservices.rest.util.RestHelper;
import com.ghx.cc.commitment.webservices.rest.util.RestMethodDetail;

/**
 * @author kguduri
 * @date 05/25/2014 This filter verifies authenticity of the current request based on the previous Async request 
 * that was made by the same GPO. If  not authorized sends Forbidden(403) response back, else continues with
 * the processing of the request.
 */

@Authorization
@Priority(1)
public class AuthorizationFilter implements ContainerRequestFilter {
	
	private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class);
	
	@Autowired
	private OutboundRestCallService outboundRestCallService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private GpoService gpoService;

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		String sslClientCn = containerRequestContext.getHeaderString(RestConstants.SSL_CLIENT_CN);
		GpoDTO gpoDTO = gpoService.getGpoBySslClientCN(sslClientCn);
		int orgId = Integer.parseInt(gpoDTO.getOrgId());
		
		UriRoutingContext routingContext = (UriRoutingContext) containerRequestContext.getUriInfo();
		ResourceMethodInvoker invoker = (ResourceMethodInvoker) routingContext.getInflector();
		RestMethodDetail restMethodDetail = RestMethodDetail.getRestMethodDetailInstance(invoker.getResourceMethod());
		Map<String, String> params = RestHelper.derivePathParamMap(containerRequestContext.getUriInfo().getAbsolutePath().toString(), restMethodDetail);
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			boolean isAuthorized = evaluatePathParam(containerRequestContext, orgId, entry);
			if(!isAuthorized) {
				return; //stop processing any more params and return the existing response
			}
		}
		
	}
	
	private boolean evaluatePathParam(ContainerRequestContext containerRequestContext, int orgId, Entry<String, String> entry) {
		LOG.info("Evaluating path parameter with key = " + entry.getKey() + " : value = " + entry.getValue() + ".");
		
		boolean isAuthorized = true;
	    
    	switch (entry.getKey()) {
			case RestConstants.CONTRACT_RESOURCE_ID:
				isAuthorized = authorizeContractResourceId(containerRequestContext, orgId, entry.getValue());
				break;
				
			case RestConstants.OFFER_RESOURCE_ID:
				isAuthorized = authorizeOfferResourceId(containerRequestContext, orgId, entry.getValue());
				break;
				
			case RestConstants.OUTBOUND_REST_CALL_ID:
				isAuthorized = authorizeOutboundRestCallId(containerRequestContext, orgId, entry.getValue());
				break;
				
			case RestConstants.JOB_ID:
				isAuthorized = authorizeJobId(containerRequestContext, orgId, entry.getValue());
				break;
				
			default:
		    	LOG.info("No Authorization required for path parameter with key = " + entry.getKey() + " : value = " + entry.getValue() + ".  continuing...");
				break;
				
		}
    	
    	return isAuthorized;
	}

	private boolean authorizeContractResourceId(ContainerRequestContext containerRequestContext, int orgId, String contractResourceId) {
		try {
			ContractDTO contractDTO = contractService.getContractInfoByContractResourceId(contractResourceId);
			if (orgId != contractDTO.getGpoId()) {
				Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new NotAuthorizedForContractException());
				containerRequestContext.abortWith(response);
				return false;
			}
			return true;
			
		} catch (EmptyResultDataAccessException erdae) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new ContractNotFoundException());
			containerRequestContext.abortWith(response);
			return false;
		}
	}

	private boolean authorizeOfferResourceId(ContainerRequestContext containerRequestContext, int orgId, String offerResourceId) {
		try {
			HybridOfferDetailDTO hybridOfferDetailDTO = offerService.getHybridOfferDetailsByOfferResourceId(offerResourceId);
			if (orgId != hybridOfferDetailDTO.getGpoId()) {
				Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new NotAuthorizedForOfferException(), containerRequestContext);
				containerRequestContext.abortWith(response);
				return false;
			}
			return true;
			
		} catch (EmptyResultDataAccessException erdae) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new OfferNotFoundException());
			containerRequestContext.abortWith(response);
			return false;
		}
		
	}

	private boolean authorizeOutboundRestCallId(ContainerRequestContext containerRequestContext, int orgId, String outboundRestCallId) {
		try {
			OutboundRestCallDTO outboundRestCallDTO = outboundRestCallService.getOutboundRestCallDTOById(Integer.parseInt(outboundRestCallId));
			if (orgId != outboundRestCallDTO.getOrgId()) {
				Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new NotAuthorizedForOutboundRestCallException());
				containerRequestContext.abortWith(response);
				return false;
			}
			return true;
			
		} catch (NumberFormatException nfe) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new InvalidCallbackIdException(outboundRestCallId));
			containerRequestContext.abortWith(response);
			return false;
			
		} catch (EmptyResultDataAccessException erdae) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new CallbackIdNotFoundException(Integer.parseInt(outboundRestCallId)));
			containerRequestContext.abortWith(response);
			return false;
		}
	}

	private boolean authorizeJobId(ContainerRequestContext containerRequestContext, int orgId, String jobId) {
		try {
			JobDTO jobDTO = jobService.getJobDTOByJobId(Integer.parseInt(jobId));
			if (orgId != jobDTO.getOrgId()) {
				Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new NotAuthorizedForJobException());
				containerRequestContext.abortWith(response);
				return false;
			}
			return true;
			
		} catch (NumberFormatException nfe) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new InvalidJobIdException(jobId));
			containerRequestContext.abortWith(response);
			return false;
			
		} catch (EmptyResultDataAccessException erdae) {
			Response response = RESTRuntimeExceptionMapper.mapExceptionToResponse(new CallbackIdNotFoundException(Integer.parseInt(jobId)));
			containerRequestContext.abortWith(response);
			return false;
		}
	}

}
