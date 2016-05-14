package org.uade.ad.trucorepo.exceptions;

public class JuegoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JuegoException() {
		super();
	}

	public JuegoException(String message) {
		super(message);
	}

	public JuegoException(Throwable cause) {
		super(cause);
	}

	public JuegoException(String message, Throwable cause) {
		super(message, cause);
	}

	public JuegoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
