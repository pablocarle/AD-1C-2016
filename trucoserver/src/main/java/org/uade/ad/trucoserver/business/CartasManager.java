package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Transaction;
import org.uade.ad.trucoserver.dao.CartaDao;
import org.uade.ad.trucoserver.dao.CartaDaoImpl;
import org.uade.ad.trucoserver.entities.Carta;

public class CartasManager {

	public static final int HAND_SIZE = 3;
	
	private static CartasManager instancia = null;
	
	public static CartasManager getManager() {
		if (instancia == null) 
			instancia = new CartasManager();
		return instancia;
	}
	
	private CartaDao dao = CartaDaoImpl.getDAO();
	
	//Lista para facilitar el "reparto" de cartas
	private List<Carta> cartas;
	{
		Transaction tr = dao.getSession().beginTransaction();
		cartas = dao.getTodos(Carta.class);
		tr.commit();
	}
	
	private CartasManager() {
		super();
	}
	
	/**
	 * Obtener numSets de cartas tomadas de a HAND_SIZE
	 * 
	 * @param numSets Cantidad de sets de cartas deseadas
	 * @return Mapa K: Numero de set V: Set de cartas random
	 */
	public Map<Integer, Set<Carta>> getRandomSets(int numSets) {
		Map<Integer, Set<Carta>> cartas = new HashMap<>(numSets);
		Set<Carta> cartasSet = null;
		List<Carta> cartasAux = new ArrayList<>(this.cartas);
		Collections.shuffle(cartasAux);
		
		int cartaIdx = 0;
		for (int i = 0; i < numSets; i++) {
			cartasSet = new HashSet<>(HAND_SIZE);
			cartas.put(i, cartasSet);
			for (int handSizeIdx = 0; cartaIdx < cartasAux.size() && handSizeIdx < HAND_SIZE; cartaIdx ++,handSizeIdx++) {
				cartasSet.add(cartasAux.get(cartaIdx));
			}
		}
		return cartas;
	}
	
}
