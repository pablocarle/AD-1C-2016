package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;

public interface JuegoManager extends Remote {
	
	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas() throws RemoteException;
	
}