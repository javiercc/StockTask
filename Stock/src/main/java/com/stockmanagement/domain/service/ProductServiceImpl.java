package com.stockmanagement.domain.service;

import java.util.ArrayList;
import java.util.List;

import com.stockmanagement.domain.entity.Product;
import com.stockmanagement.domain.repository.ProductDAO;
import com.stockmanagement.domain.repository.ProductDAOImpl;

public class ProductServiceImpl extends GenericServiceImpl implements ProductService {
	// inject
	ProductDAO prDAO = new ProductDAOImpl();

	public ProductServiceImpl() {
		super();
	}

	@Override
	public void update(Product product) throws Exception {
		throw new UnsupportedOperationException("ProductServiceImpl.update");

	}

	@Override
	public void delete(Long id) throws Exception {
		throw new UnsupportedOperationException("ProductServiceImpl.delete");

	}

	@Override
	public Product findById(Long id) throws Exception {
		
			return prDAO.get(id);

		
	}

	@Override
	public List<Product> getAll() throws Exception {

		List<Product> productList = new ArrayList<>();

		
			productList.addAll(prDAO.getAll());

	
		return productList;

	}

	@Override
	public void add(Product product) throws Exception {
		throw new UnsupportedOperationException("ProductServiceImpl.add");

	}

}
