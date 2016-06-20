package org.uade.ad.trucoserver.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;
import org.uade.ad.trucoserver.dao.EnviteDao;
import org.uade.ad.trucoserver.dao.EnviteDaoImpl;
import org.uade.ad.trucoserver.entities.EnvidoEnvite;
import org.uade.ad.trucoserver.entities.Envite;

public class EnviteManager {
	
	private static EnviteManager instancia = null;
	
	public static EnviteManager getManager() {
		if (instancia == null) {
			instancia = new EnviteManager();
		}
		return instancia;
	}
	
	private EnviteDao enviteDao = EnviteDaoImpl.getDAO();
	private List<Envite> envites;
	{
		Transaction tr = enviteDao.getSession().beginTransaction();
		envites = enviteDao.getTodos(Envite.class);
		tr.commit();
	}

	private EnviteManager() {
		super();
	}

	/**
	 * Obtener envidos (primer nivel)
	 * 
	 * @return
	 */
	public List<Envite> getEnvidos() {
		List<Envite> retList = new ArrayList<>();
		if (envites == null || envites.isEmpty()) {
			throw new RuntimeException("No hay envites configurados!!");
		}
		for (Envite e : envites) {
			if (!e.tieneEnviteAnterior() && e instanceof EnvidoEnvite) {
				retList.add(e);
			}
		}
		return retList;
	}

	/**
	 * Obtener trucos (primer nivel)
	 * 
	 * @return
	 */
	public List<Envite> getTrucos() {
		// TODO Auto-generated method stub
		return null;
	}
}
