package com.ghx.cc.commitment.webservices.rest.util;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

/**
 * Dummy UriInfo created to cater to asynchronous rest operations
 * needs refinement
 * @author svemula
 *
 */
public class AsynchronousUriInfo implements UriInfo {

	String path;
	
	public AsynchronousUriInfo(String path) {
		this.path = path;
	}
	
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getPath(boolean decode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PathSegment> getPathSegments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PathSegment> getPathSegments(boolean decode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getRequestUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UriBuilder getRequestUriBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getAbsolutePath() {
		return  getAbsolutePathBuilder().build();
	}

	@Override
	public UriBuilder getAbsolutePathBuilder() {
		return new JerseyUriBuilder().path(path);
	}

	@Override
	public URI getBaseUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UriBuilder getBaseUriBuilder() {
		String str = path;
		int index = str.indexOf("/v1");
	    str = str.substring(0,index);
		return new JerseyUriBuilder().path(str);
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters(boolean decode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMatchedURIs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMatchedURIs(boolean decode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getMatchedResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI resolve(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI relativize(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}

}
