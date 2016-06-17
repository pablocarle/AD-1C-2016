package org.uade.ad.trucoserver;

import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.RankingDTO;
import org.uade.ad.trucorepo.exceptions.RankingException;
import org.uade.ad.trucorepo.interfaces.RankingService;

public class RankingServiceImpl extends Context implements RankingService {

	protected RankingServiceImpl() throws RemoteException {
		super();
	}
	
	public RankingServiceImpl(int port) throws RemoteException {
		super(port);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public RankingDTO getRankingGeneral() throws RemoteException, RankingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RankingDTO getRankingGrupo(int idGrupo, JugadorDTO jugador) throws RemoteException, RankingException {
		// TODO Auto-generated method stub
		return null;
	}

}
