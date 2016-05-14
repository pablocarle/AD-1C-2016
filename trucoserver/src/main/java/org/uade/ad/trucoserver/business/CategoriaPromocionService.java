package org.uade.ad.trucoserver.business;

import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucoserver.dao.JugadorDao;
import org.uade.ad.trucoserver.dao.JugadorDaoImpl;
import org.uade.ad.trucoserver.entities.Categoria;
import org.uade.ad.trucoserver.entities.Jugador;

public class CategoriaPromocionService implements PartidaTerminadaObserver {

	private static CategoriaPromocionService instancia = null;
	
	public static CategoriaPromocionService getService() {
		if (instancia == null)
			instancia = new CategoriaPromocionService();
		return instancia;
	}
	
	private JugadorDao jDao = JugadorDaoImpl.getDAO();
	private List<Categoria> categorias = JugadorManager.getManager().getCategoriasOrdenadas();
	
	private CategoriaPromocionService() {
		super();
	}

	@Override
	public void finPartida(PartidaTerminadaEvent partida) throws Exception {
		RankingItem rankingItem = null;
		Categoria categoriaCalculada = null;
		Transaction tr = jDao.getSession().beginTransaction();
		for (Jugador ganador : partida.getGanadores()) {
			rankingItem = RankingService.getService().getItem(ganador.getIdJugador());
			if (rankingItem == null)
				rankingItem = new RankingItem(ganador, partida.getPartida().getPuntosObtenidos(ganador), 1, 1);
			categoriaCalculada = calcularCategoria(rankingItem);
			if (!ganador.getCategoria().equals(categoriaCalculada)) {
				//Actualizamos categoria
				ganador.setCategoria(categoriaCalculada);
				jDao.actualizar(ganador);
			}
		}
		tr.commit();
	}

	private Categoria calcularCategoria(RankingItem rankingItem) {
		Categoria actual = null;
		for (int i = categorias.size() - 1; i >= 0; i--) {
			actual = categorias.get(i);
			if (rankingItem.getPartidasJugadas() >= actual.getPartidosMin()
					&& rankingItem.getPromedioGanadas() >= actual.getPromedioVictoriasMin()
					&& rankingItem.getPuntos() >= actual.getPuntajeMin()) {
				return actual;
			}
		}
		return null;
	}

}
