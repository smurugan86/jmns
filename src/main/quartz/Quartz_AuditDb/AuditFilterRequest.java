package com.ghx.cc.commitment.webservices.rest.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;

import com.ghx.cc.commitment.webservices.rest.RestConstants;

public class AuditFilterRequest extends HttpServletRequestWrapper {
	private AuditFilterWrapperInputStream servletStream;
	private int auditLogId;
	private String content;

	public AuditFilterRequest(HttpServletRequest request) throws IOException {
		super(request);
		this.servletStream = new AuditFilterWrapperInputStream();
		content = IOUtils.toString(request.getInputStream());
		servletStream.stream = new ByteArrayInputStream(content.getBytes());
	}

	public String getContent() {
		return content;
	}

	public int getAuditLogId() {
		return auditLogId;
	}

	public void setAuditLogId(int auditLogId) {
		this.auditLogId = auditLogId;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return servletStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(servletStream));
	}

	@SuppressWarnings("rawtypes")
	public MultivaluedMap<String, String> getHeaderMap() {
		MultivaluedMap<String, String> headerMap = new MultivaluedHashMap<String, String>();
		Enumeration headerEnum = this.getHeaderNames();
		while (headerEnum.hasMoreElements()) {
			String header = (String) headerEnum.nextElement();
			headerMap.putSingle(header, this.getHeader(header));
		}
		return headerMap;
	}
	
	@Override
	public StringBuffer getRequestURL() {
		StringBuffer url = super.getRequestURL();
		String urlString = new String(url);
		if(getHeader(RestConstants.HEADER_X_FORWARDED_HOST) != null && !urlString.startsWith(RestConstants.HTTPS_SCHEME) ) {
			urlString= urlString.replace(RestConstants.HTTP_SCHEME, RestConstants.HTTPS_SCHEME);
		}
		return new StringBuffer(urlString);
	}
}

class AuditFilterWrapperInputStream extends ServletInputStream {
	protected InputStream stream;

	@Override
	public int read() throws IOException {
		return stream.read();
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReadListener(ReadListener arg0) {
		// TODO Auto-generated method stub
		
	}
}
