package com.stockmanagement.domain.service;

import java.util.List;

import com.stockmanagement.domain.entity.Product;

/**
 * @author Samsung
 *
 */
public interface ProductService {

	/**
	 * @param product
	 * @throws Exception
	 */
	public void update(Product product) throws Exception;

	/**
	 *
	 * @param id
	 * @throws Exception
	 */
	public void delete(Long id) throws Exception;

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Product findById(Long id) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public List<Product> getAll() throws Exception;

	/**
	 * @param product
	 * @throws Exception
	 */
	public void add(Product product) throws Exception;

}
