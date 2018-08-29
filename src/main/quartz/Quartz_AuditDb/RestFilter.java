package com.ghx.cc.commitment.webservices.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghx.cc.commitment.resource.BeanNameConstants;
import com.ghx.cc.commitment.resource.Constants;
import com.ghx.cc.commitment.service.GpoService;
import com.ghx.cc.commitment.webservices.rest.RestConstants;
import com.ghx.cc.commitment.work.shared.ActivityHelper;

@Component(value = "RestFilter")
public class RestFilter implements Filter {
	protected static final Logger LOG = Logger.getLogger(RestFilter.class);

	@Autowired
	private AuditDbLogger auditDbLogger;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// must be include because this implement the Filter interface
	}

	@Override
	public void destroy() {
		// must be include because this implement the Filter interface
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		ClientCertificateFilterRequest clientCertificateFilterRequest = new ClientCertificateFilterRequest((HttpServletRequest) servletRequest);
		AuditFilterRequest auditRequest = new AuditFilterRequest(clientCertificateFilterRequest);
		
		auditDbLogger.logRequest(auditRequest);
		AuditFilterResponse auditResponse = new AuditFilterResponse((HttpServletResponse) servletResponse, auditRequest);

		try {
			if (isAuthorized(clientCertificateFilterRequest)) {
				filterChain.doFilter(auditRequest, auditResponse);
			} else {
				auditResponse.setStatus(Status.FORBIDDEN.getStatusCode());
				auditResponse.getWriter().write("This request is not authorized due to missing or invalid client certificate.");
			}

			auditDbLogger.logResponse(auditResponse);
		} catch (Throwable t) {
			LOG.error(t.getMessage(), t);
			auditResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			auditResponse.getWriter().write(Constants.BLANK_SPACE);
			
			auditDbLogger.logResponse(auditResponse);
		}
	}

	protected boolean isAuthorized(HttpServletRequest request) throws Exception {
		boolean isAuthorized = false;
		
		GpoService gpoService = (GpoService) ActivityHelper.getBean(BeanNameConstants.GPO_SERVICE);
		String sslClientCN = request.getHeader(RestConstants.SSL_CLIENT_CN);
		
		if (sslClientCN != null && gpoService.getGpoBySslClientCN(sslClientCN) != null) {
			isAuthorized = true;
		}
		
		return isAuthorized;
	}

}
