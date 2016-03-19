package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.interfaces.JuegoManager;

/**
 * Entry point servicios de juego
 * 
 * @author Grupo9
 *
 */
public class JuegoManagerImpl extends RemoteObject implements JuegoManager {

	private static final long serialVersionUID = 1L;

	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
