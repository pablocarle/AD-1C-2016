package org.uade.ad.trucoserver.dao;

import org.uade.ad.trucoserver.entities.Categoria;

public class CategoriaDaoImpl extends GenericDaoImpl<Categoria, Integer> implements CategoriaDao {

	private static CategoriaDao instancia = null;
	
	public static CategoriaDao getDAO() {
		if (instancia == null) {
			instancia = new CategoriaDaoImpl();
		}
		return instancia;
	}
	
	private CategoriaDaoImpl() {
		super();
	}

}
