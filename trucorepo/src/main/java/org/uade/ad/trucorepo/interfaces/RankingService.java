package org.uade.ad.trucorepo.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.RankingDTO;
import org.uade.ad.trucorepo.exceptions.RankingException;

public interface RankingService extends Remote {
	
	public static final String SERVICENAME = "rankingService";

	RankingDTO getRankingGeneral() throws RemoteException, RankingException;

	RankingDTO getRankingGrupo(int idGrupo, JugadorDTO jugador) throws RemoteException, RankingException;

}
