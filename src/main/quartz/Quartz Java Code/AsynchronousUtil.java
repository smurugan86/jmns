package com.ghx.cc.commitment.webservices.rest.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.ghx.cc.commitment.util.Util;
import com.ghx.cc.commitment.webservices.rest.RestConstants;

/**
 * A collection of utility methods for asynchronous requests
 *
 * @author dgruber
 */
public final class AsynchronousUtil {
	private static final Logger LOG = Logger.getLogger(AsynchronousUtil.class);

	/**
	 * Private constructor
	 */
	private AsynchronousUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns {@code true} if the request is to be processed asynchronously,
	 * otherwise returns {@code false}
	 *
	 * @param headerMap the map of headers
	 * @return true if the request is to be processed asynchronous, otherwise returns false
	 */
	public static boolean processAsynchronously(MultivaluedMap<String, String> headerMap) {
		if(evaluateAsyncPreferred(headerMap)) {
			if(evaluateAsyncCallbackUrl(headerMap)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns {@code true} if the
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HEADER_ASYNC_PREFERRED}
	 * request header value is "<i>true</i>"
	 *
	 * <p>
	 * A "<i>true</i>"
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HEADER_ASYNC_PREFERRED}
	 * value must match one (1) of the following (not case sensitive):
	 * <ol>
	 * <li>{@code YES}</li>
	 * <li>{@code TRUE}
	 * <li>{@code 1}</li>
	 * </ol>
	 * </p>
	 *
	 * @param headerMap the map of headers
	 * @return true if the request header value is "<i>true</i>"
	 */
	private static boolean evaluateAsyncPreferred(MultivaluedMap<String, String> headerMap) {
		if(headerMap.containsKey(RestConstants.HEADER_ASYNC_PREFERRED.toLowerCase())) {

			String asyncPreferredValue = Util.getHeaderString(headerMap, RestConstants.HEADER_ASYNC_PREFERRED.toLowerCase());

			for(String trueValue : RestConstants.HEADER_TRUE_VALUES) {
				if(asyncPreferredValue.equalsIgnoreCase(trueValue)) {
					LOG.debug("Request prefers asynchronous processing");

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns {@code true} if the
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HEADER_ASYNC_CALLBACK_URL}
	 * request header value is valid <b>or</b> if it is not included, otherwise
	 * returns {@code false}
	 *
	 * <p>
	 * A
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HEADER_ASYNC_CALLBACK_URL}
	 * is valid if:
	 * <ol>
	 * <li>includes the
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HEADER_ASYNC_CALLBACK_URL}
	 * key</li>
	 * <li>the
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HEADER_ASYNC_CALLBACK_URL}
	 * key value uses the
	 * {@value com.ghx.cc.commitment.webservices.rest.RestConstants#HTTPS_SCHEME}
	 * protocol</li>
	 * </ol>
	 * </p>
	 *
	 * @param headerMap the map of headers
	 * @return true if the request header is valid or if it is not included, otherwise false
	 * @throws WebApplicationException if there an error evaluating the callback URL
	 */
	private static boolean evaluateAsyncCallbackUrl(MultivaluedMap<String, String> headerMap) throws WebApplicationException {
		if(!(headerMap.containsKey(RestConstants.HEADER_ASYNC_CALLBACK_URL))) {
			LOG.debug("Request did not provide a callback URL in the header, continuing without a callback");

			return true;
		}

		String asyncCallbackUrlValue = Util.getHeaderString(headerMap, RestConstants.HEADER_ASYNC_CALLBACK_URL);

		try {
			URL url = new URL(asyncCallbackUrlValue);
			String protocol = url.getProtocol();

			if(protocol.equalsIgnoreCase(RestConstants.HTTPS_SCHEME)) {
				LOG.debug("Request provided a valid callback URL for asynchronous processing");

				return true;
			} else {
				LOG.error(String.format("The provided callback URL does NOT use the secure HTTP protocol, request will NOT be processed asynchronously (provided callback URL: %s)", asyncCallbackUrlValue));
			}
		} catch(MalformedURLException e) {
			String message = String.format("There was a problem while trying to evaluate the provided callback URL (provided callback URL: %s)", asyncCallbackUrlValue);
			LOG.error(message, e);

			throw new WebApplicationException(message, Status.BAD_REQUEST);
		}

		return false;
	}
}