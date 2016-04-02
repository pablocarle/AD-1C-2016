package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import org.uade.ad.trucorepo.dtos.CartaDTO;
import org.uade.ad.trucorepo.dtos.EnviteDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.interfaces.JuegoService;
import org.uade.ad.trucoserver.business.CartasManager;

/**
 * Entry point servicios de juego
 * 
 * @author Grupo9
 *
 */
public class JuegoServiceImpl extends Context implements JuegoService {

	private CartasManager cartaManager = CartasManager.getManager();
	
	protected JuegoServiceImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	public Map<JugadorDTO, Set<CartaDTO>> repartirCartas(int idJuego) throws RemoteException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cantarEnvite(int idJuego, JugadorDTO jugador, EnviteDTO envite) throws RemoteException {
		
	}
}
