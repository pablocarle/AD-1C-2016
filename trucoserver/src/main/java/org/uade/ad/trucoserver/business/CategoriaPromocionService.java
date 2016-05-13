package org.uade.ad.trucoserver.business;

import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;

public class CategoriaPromocionService implements PartidaTerminadaObserver {

	private static CategoriaPromocionService instancia = null;
	
	public static CategoriaPromocionService getService() {
		if (instancia == null)
			instancia = new CategoriaPromocionService();
		return instancia;
	}
	
	private CategoriaPromocionService() {
		super();
	}

	@Override
	public void finPartida(Partida partida) throws Exception {
		Pareja parejaGanadora = partida.getParejaGanadora();
		Jugador j1 = parejaGanadora.getJugador1();
		Jugador j2 = parejaGanadora.getJugador2();
		RankingItem j1Ranking = RankingService.getService().getItem(j1.getIdJugador());
		RankingItem j2Ranking = RankingService.getService().getItem(j2.getIdJugador());
		
		//XXX Para evitar lio de que haya que garantizar que corra primero RankingService
		if (j1Ranking == null)
			j1Ranking = new RankingItem(j1, partida.getPuntosObtenidos(j1), 1, 1);
		if (j2Ranking == null)
			j2Ranking = new RankingItem(j2, partida.getPuntosObtenidos(j2), 1, 1);
		
		//TODO Cargar las categorias en la instancia y verificar con los nuevos parametros
	}

}
