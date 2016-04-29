package org.uade.ad.trucorepo.exceptions;

import java.rmi.RemoteException;

public class JugadorException extends RemoteException {

	private static final long serialVersionUID = 1L;

	public JugadorException() {
		super();
	}

	public JugadorException(String s) {
		super(s);
	}

	public JugadorException(String s, Throwable cause) {
		super(s, cause);
	}
}
