package com.stockmanagement.domain.repository;

import java.util.Collection;

public interface GenericDAO<E, T> {

	/**
	 * 
	 * @param id
	 * @return
	 */
    boolean contains(T id);

    /**
     *
     * @param id
     * @return
     */
    E get(T id);

    /**
     *
     * @return
     */
    Collection<E> getAll();
	
    /**
    *
    * @param entity
    */
   void add(E entity);

   /**
    *
    * @param id
    */
   void remove(T id);

   /**
    *
    * @param entity
    */
   void update(E entity);
	
}
