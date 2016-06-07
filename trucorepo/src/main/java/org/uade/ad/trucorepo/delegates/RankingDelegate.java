package org.uade.ad.trucorepo.delegates;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.RankingDTO;
import org.uade.ad.trucorepo.exceptions.RankingException;
import org.uade.ad.trucorepo.interfaces.RankingService;

public class RankingDelegate extends BusinessDelegate {
	
	private RankingService rankingService;
	
	public RankingDelegate() throws RankingException {
		super();
		try {
			rankingService = (RankingService) Naming.lookup("//" + webServerProperties.getProperty("server.url") + "/" + RankingService.SERVICENAME);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new RankingException(e);
		} 

	}

	public RankingDTO getRankingGeneral() throws RankingException {
		try {
			return rankingService.getRankingGeneral();
		} catch (RemoteException e) {
			throw new RankingException(e);
		}
	}

	public RankingDTO getRankingGrupo(int idGrupo, JugadorDTO jugador) throws RankingException {
		try {
			return rankingService.getRankingGrupo(idGrupo, jugador);
		} catch (RemoteException e) {
			throw new RankingException(e);
		}
	}
}
