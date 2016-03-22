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
		return factory.getCurrentSession();
	}

	@Override
	public T getPorId(Class<T> clasz, ID id) {
		Session session = getSession();
		return session.get(clasz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getTodos(Class<T> clasz) {
		Session session = getSession();
		return session.createQuery("from " + clasz.getName() + " where 1 = 1").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getMuchos(Query criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getUnico(Query criteria) {
		return (T) criteria.uniqueResult();
	}

	@Override
	public void guardar(T entity) {
		Session session = getSession();
		session.save(entity);
	}

	@Override
	public void actualizar(T entity) {
		Session session = getSession();
		session.update(entity);
	}

	@Override
	public void eliminar(T entity) {
		Session session = getSession();
		session.delete(entity);
	}
}
