package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Envite;

public class EnviteDaoImpl extends GenericDaoImpl<Envite, Integer>  implements EnviteDao {

	private static EnviteDao instancia = null;
	
	public static EnviteDao getDAO(){
		if (instancia == null)
			instancia = new EnviteDaoImpl();
		return instancia;
	}
	
	private EnviteDaoImpl() {
		super();
	}
}
