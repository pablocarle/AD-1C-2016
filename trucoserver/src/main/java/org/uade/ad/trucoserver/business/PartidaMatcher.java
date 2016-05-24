package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Pareja;

public abstract class PartidaMatcher {

	public PartidaMatcher() {
		super();
	}

	public abstract Pareja[] match();


}
