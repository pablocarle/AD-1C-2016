package org.uade.ad.trucoserver.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

	private SessionFactory factory;
	
	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getPorId(ID id) {
		Session session = getSession();
		return null;
	}

	@Override
	public List<T> getTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getMuchos(Query criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getUnico(Query criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardar(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(T entity) {
		// TODO Auto-generated method stub
		
	}
}
