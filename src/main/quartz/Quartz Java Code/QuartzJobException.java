package com.ghx.cc.commitment.exception;

/**
 * @author doug
 */
public class QuartzJobException extends Exception {
	private static final long serialVersionUID = 1305855921382823719L;

	/**
	 * @param message
	 */
	public QuartzJobException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public QuartzJobException(String message, Throwable throwable) {
		super(message, throwable);
	}
}