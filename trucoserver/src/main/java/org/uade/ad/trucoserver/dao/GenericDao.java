package org.uade.ad.trucoserver.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public interface GenericDao<T, ID extends Serializable> {
	
	public Session getSession();
	
	public T getPorId(ID id);
	
	public List<T> getTodos();
	
	public List<T> getMuchos(Query criteria);
	
	public T getUnico(Query criteria);
	
	public void guardar(T entity);

	public void actualizar(T entity);
	
}