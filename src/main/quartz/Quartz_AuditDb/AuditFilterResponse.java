package com.ghx.cc.commitment.webservices.rest.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.ghx.cc.commitment.resource.Constants;

public class AuditFilterResponse extends HttpServletResponseWrapper {

	private AuditResponseWriter writer;
	private AuditResponseOutputStream outputStream;
	private HashMap<String, String> headerMap = new HashMap<>();
	private AuditFilterRequest auditRequest;
	private int statusCode;

	public AuditFilterResponse(HttpServletResponse response, AuditFilterRequest auditRequest)
			throws IOException {
		super(response);
		this.auditRequest = auditRequest;
	}

	public AuditFilterRequest getAuditRequest() {
		return auditRequest;
	}

	public void setAuditRequest(AuditFilterRequest auditRequest) {
		this.auditRequest = auditRequest;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		writer = new AuditResponseWriter(super.getWriter());
		return writer;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		outputStream = new AuditResponseOutputStream(super.getOutputStream());
		return outputStream;
	}

	@Override
	public final void addDateHeader(String name, long date) {
		super.addDateHeader(name, date);
		headerMap.put(name, String.valueOf(date));
	}

	@Override
	public final void addHeader(String name, String value) {
		super.addHeader(name, value);
		headerMap.put(name, value);
	}

	@Override
	public final void addIntHeader(String name, int value) {
		super.addIntHeader(name, value);
		headerMap.put(name, String.valueOf(value));
	}

	@Override
	public final void setDateHeader(String name, long date) {
		super.setDateHeader(name, date);
		headerMap.put(name, String.valueOf(date));
	}

	@Override
	public final void setHeader(String name, String value) {
		super.setHeader(name, value);
		headerMap.put(name, value);
	}

	@Override
	public final void setIntHeader(String name, int value) {
		super.setIntHeader(name, value);
		headerMap.put(name, String.valueOf(value));
	}

	@Override
	public final void setStatus(int sc) {
		super.setStatus(sc);
		this.setStatusCode(sc);
	}

	@Override
	public final void setStatus(int sc, java.lang.String sm) {
		super.setStatus(sc, sm);
		this.setStatusCode(sc);
	}

	@Override
	public void sendError(int sc) throws IOException {
		super.sendError(sc);
		this.setStatusCode(sc);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		super.sendError(sc, msg);
		this.setStatusCode(sc);
	}

	public HashMap<String, String> getHeaderMap() {
		return headerMap;
	}

	public String getContent() {
		if (writer != null) {
			return writer.getContent();
		} else if(outputStream != null) {
			return outputStream.getContent();
		} else {
			return Constants.EMPTY_STRING;
		}
	}
}

class AuditResponseWriter extends PrintWriter {

	private StringBuilder content = new StringBuilder();

	public AuditResponseWriter(Writer writer) {
		super(writer);
	}

	@Override
	public void write(int c) {
		content.append((char) c);
		super.write(c);
	}

	@Override
	public void write(char[] chars, int offset, int length) {
		content.append(chars, offset, length);
		super.write(chars, offset, length);
	}

	@Override
	public void write(String string, int offset, int length) {
		content.append(string, offset, length);
		super.write(string, offset, length);
	}

	public String getContent() {
		return content.toString();
	}

}

class AuditResponseOutputStream extends ServletOutputStream {
	public ServletOutputStream sos;
	private ByteArrayOutputStream outStream;

	public AuditResponseOutputStream(ServletOutputStream sos) {
		super();
		this.sos = sos;
		outStream = new ByteArrayOutputStream();
	}
	
	public void write(byte[] b, int off, int len) throws IOException {
		sos.write(b, off, len);
		outStream.write(b, off, len);
	}

	public void write(byte[] b) throws IOException {
		sos.write(b);
		outStream.write(b);
	}

	public void write(int b) throws IOException {
		sos.write(b);
		outStream.write(b);
	}

	public void flush() throws IOException {
		sos.flush();
	}

	public void close() throws IOException {
		sos.close();
	}

	public String getContent() {
		return outStream.toString();
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}

}