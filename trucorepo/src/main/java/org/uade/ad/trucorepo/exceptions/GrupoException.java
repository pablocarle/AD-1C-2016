package org.uade.ad.trucorepo.exceptions;

public class GrupoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrupoException() {
		super();
	}

	public GrupoException(String message) {
		super(message);
	}

	public GrupoException(Throwable cause) {
		super(cause);
	}

	public GrupoException(String message, Throwable cause) {
		super(message, cause);
	}

	public GrupoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
