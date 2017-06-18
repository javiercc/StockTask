package com.stockmanagement.domain.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.stockmanagement.domain.entity.Product;
import com.stockmanagement.domain.entity.Stock;

public class DataBase {

	private static Map<Long, Product> productsMap;

	static {

		Product product1 = new Product();
		product1.setIdProduct(1L);
		product1.setName("Product 1");

		Stock stockPr1 = new Stock();
		stockPr1.setIdStock(1L);
		stockPr1.setIdProduct(product1.getIdProduct());
		stockPr1.setAvailableU(new BigDecimal(100));
		stockPr1.setBookedU(new BigDecimal(0));
		product1.setStock(stockPr1);

		Product product2 = new Product();
		product2.setIdProduct(2L);
		product2.setName("Product 2");

		Stock stockPr2 = new Stock();
		stockPr2.setIdStock(2L);
		stockPr2.setIdProduct(product2.getIdProduct());
		stockPr2.setAvailableU(new BigDecimal(50));
		stockPr2.setBookedU(new BigDecimal(0));
		product2.setStock(stockPr2);

		Product product3 = new Product();
		product3.setIdProduct(3L);
		product3.setName("Product 3");

		Stock stockPr3 = new Stock();
		stockPr3.setIdStock(3L);
		stockPr3.setIdProduct(product3.getIdProduct());
		stockPr3.setAvailableU(new BigDecimal(20));
		stockPr3.setBookedU(new BigDecimal(0));

		Product product4 = new Product();
		product4.setIdProduct(4L);
		product4.setName("Product 4");

		Stock stockPr4 = new Stock();
		stockPr4.setIdStock(4L);
		stockPr4.setIdProduct(product4.getIdProduct());
		stockPr4.setAvailableU(new BigDecimal(0));
		stockPr4.setBookedU(new BigDecimal(0));
		product4.setStock(stockPr4);

		Product product5 = new Product();
		product5.setIdProduct(5L);
		product5.setName("Product 5");

		Stock stockPr5 = new Stock();
		stockPr5.setIdStock(5L);
		stockPr5.setIdProduct(product5.getIdProduct());
		stockPr5.setAvailableU(new BigDecimal(-77));
		stockPr5.setBookedU(new BigDecimal(0));
		product5.setStock(stockPr5);

		productsMap = new HashMap<>();
		productsMap.put(product1.getIdProduct(), product1);
		productsMap.put(product2.getIdProduct(), product2);
		productsMap.put(product3.getIdProduct(), product3);
		productsMap.put(product4.getIdProduct(), product4);
		productsMap.put(product5.getIdProduct(), product5);

	}

	private DataBase() {
	}

	public static Map<Long, Product> getData() {
		return productsMap;
	}
}
