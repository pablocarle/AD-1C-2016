package org.uade.ad.trucoserver.entities;

import java.util.ArrayList;
import java.util.List;

public class Mano {
	
	private List<Baza> bazas;
	
	public Mano() {
		super();
	}
	
	public List<Baza> getBazas() {
		return new ArrayList<>(bazas);
	}
}
