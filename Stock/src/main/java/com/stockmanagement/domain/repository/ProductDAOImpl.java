package com.stockmanagement.domain.repository;

import java.util.Collection;
import java.util.Map;

import com.stockmanagement.domain.entity.Product;
import com.stockmanagement.domain.entity.Stock;

public class ProductDAOImpl implements ProductDAO {

	private static Map<Long, Product> productsMap = DataBase.getData();

	public ProductDAOImpl() {
		super();
	}

	@Override
	public Product get(Long id) {
		return productsMap.get(id);
	}

	@Override
	public Collection<Product> getAll() {

		for (Product product : productsMap.values()) {
			isProductAvailable(product);
		}

		return productsMap.values();
	}

	private void isProductAvailable(Product product) {

		Stock stock = product.getStock();

		if (stock != null && stock.getAvailableU().intValue() > 0) {
			product.setAvailable(true);
		}

	}

	@Override
	public void update(Product entity) {
		if (productsMap.containsKey(entity.getId())) {
			productsMap.put(entity.getId(), entity);
		}

	}

	@Override
	public boolean contains(Long id) {
		return false;
	}

	@Override
	public void add(Product entity) {
		throw new UnsupportedOperationException("ProductDAOImpl.add");

	}

	@Override
	public void remove(Long id) {
		throw new UnsupportedOperationException("ProductDAOImpl.remove");

	}

}
