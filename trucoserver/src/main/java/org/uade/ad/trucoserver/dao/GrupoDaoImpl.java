package org.uade.ad.trucoserver.dao;

import org.hibernate.Query;
import org.uade.ad.trucoserver.entities.Grupo;

public class GrupoDaoImpl extends GenericDaoImpl<Grupo, Integer> implements GrupoDao {

	private static GrupoDao instancia = null;
	
	public static GrupoDao getDAO() {
		if (instancia == null) {
			instancia = new GrupoDaoImpl();
		}
		return instancia;
	}
	
	private GrupoDaoImpl() {
		super();
	}

	@Override
	public Grupo getPorNombreGrupo(String nombreGrupo) {
		Query q = getSession().createQuery("from Grupo where nombre = :nombre");
		q.setParameter("nombre", nombreGrupo);
		return getUnico(q);
	}
}
