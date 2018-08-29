package com.ghx.cc.commitment.exception;

/**
 * @author doug
 */
public class QuartzInitializerException extends Exception {
	private static final long serialVersionUID = 2895831603931457716L;

	/**
	 * @param message
	 */
	public QuartzInitializerException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public QuartzInitializerException(String message, Throwable throwable) {
		super(message, throwable);
	}
}