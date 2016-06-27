package org.uade.ad.trucoserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.RankingDTO;
import org.uade.ad.trucorepo.dtos.RankingItemDTO;
import org.uade.ad.trucorepo.exceptions.RankingException;
import org.uade.ad.trucoserver.business.RankingItem;
import org.uade.ad.trucoserver.business.RankingService;

public class RankingServiceImpl extends Context implements org.uade.ad.trucorepo.interfaces.RankingService {

	protected RankingServiceImpl() throws RemoteException {
		super();
	}
	
	public RankingServiceImpl(int port) throws RemoteException {
		super(port);
	}

	private static final long serialVersionUID = 1L;


	public RankingDTO getRankingGeneral(JugadorDTO jugador) throws RemoteException, RankingException {
		try {
			int i=1;
			RankingService rs = RankingService.getService();
			SortedSet<RankingItem> resSet = rs.rankingItems;
			RankingDTO rankdto = new RankingDTO();
			List<RankingItemDTO> rankitemdto = new ArrayList<RankingItemDTO>();
			for (RankingItem ri: resSet){
//				if(ri.getJugador().getApodo()==jugador.getApodo()){
					RankingItemDTO item = new RankingItemDTO();
					item.setJugador(ri.getJugador().getDTO());
					item.setPartidasGanadas(ri.getPartidasGanadas());
					item.setPartidasJugadas(ri.getPartidasJugadas());
					item.setPromedioGanadas(ri.getPuntos()/ri.getPartidasJugadas());
					item.setPuntos(ri.getPuntos());
					item.setPosicion(i);
					rankitemdto.add(item);
					
//				}
				i++;
			}
			rankdto.setItems(rankitemdto);
			return rankdto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public RankingDTO getRankingGrupo(int idGrupo, JugadorDTO jugador) throws RemoteException, RankingException {
		try {
			int i=1;
			RankingService rs = RankingService.getService();
			SortedSet<RankingItem> resSet = rs.rankingItems;
			RankingDTO rankdto = new RankingDTO();
			List<RankingItemDTO> rankitemdto = new ArrayList<RankingItemDTO>();
			for (RankingItem ri: resSet){
					RankingItemDTO item = new RankingItemDTO();
					item.setJugador(jugador);
					item.setPartidasGanadas(ri.getPartidasGanadas());
					item.setPartidasJugadas(ri.getPartidasJugadas());
					item.setPromedioGanadas(ri.getPromedioGanadas());
					item.setPuntos(ri.getPuntos());
					item.setPosicion(i);
					rankitemdto.add(item);
					i++;
			}
			rankdto.setItems(rankitemdto);
			return rankdto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




}
