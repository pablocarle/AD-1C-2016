package org.uade.ad.trucoserver.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public interface GenericDao<T, ID extends Serializable> {
	
	public Session getSession();
	
	public T getPorId(Class<T> clasz, ID id);
	
	public List<T> getTodos(Class<T> clasz);
	
	public List<T> getMuchos(Query criteria);
	
	public T getUnico(Query criteria);
	
	public void guardar(T entity);

	public void actualizar(T entity);
	
	/**
	 * Por default eliminacion fisica
	 * 
	 * @param entity La entidad a eliminar
	 */
	public void eliminar(T entity);
	
}