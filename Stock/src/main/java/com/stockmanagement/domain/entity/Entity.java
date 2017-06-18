package com.stockmanagement.domain.entity;

/**
 * 
 * @author Samsung
 *
 * @param <T>
 */
public abstract class Entity<T> {

	T id;

	/**
	 * 
	 * @param id
	 */
	public Entity(T id) {
		this.id = id;

	}
	
	public Entity() {
	

	}

	/**
	 *
	 * @return
	 */
	public T getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 */
	public void setId(T id) {
		this.id = id;
	}

}
