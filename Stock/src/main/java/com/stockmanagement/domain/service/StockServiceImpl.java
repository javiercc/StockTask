package com.stockmanagement.domain.service;

import java.util.List;

import com.stockmanagement.domain.entity.Order;
import com.stockmanagement.domain.entity.Product;
import com.stockmanagement.domain.entity.Stock;
import com.stockmanagement.domain.repository.ProductDAO;
import com.stockmanagement.domain.repository.ProductDAOImpl;
import com.stockmanagement.enums.ResultEnum;

public class StockServiceImpl extends GenericServiceImpl implements StockService {

	// inject
	ProductDAO prDAO = new ProductDAOImpl();

	/*
	 * sustract units available add units booked
	 */
	@Override
	public List<Order> bookStock(List<Order> orders) throws Exception {

	
			for (Order order : orders) {

				Product product = null;
				Stock stock = null;
				order.setState(ResultEnum.KO.getValue()); 
				
				if (order.getIdProduct() != null) {
					product = prDAO.get(order.getIdProduct());
				}

				if (product != null) {

					stock = product.getStock();
				}

				if (stock != null && stock.getBookedU() != null && stock.getAvailableU() != null
						&& order.getUnits() != null) {

					// Check availables
					if (order.getUnits().compareTo(stock.getAvailableU()) > 0) {
						order.setState(ResultEnum.INSUFFICIENT.getValue());
					} else {
						stock.setAvailableU(stock.getAvailableU().subtract(order.getUnits()));
						stock.setBookedU(stock.getBookedU().add(order.getUnits()));
						order.setState(ResultEnum.OK.getValue());
						prDAO.update(product);
					}

				}
			}
		return orders;
	}
	
	
	

	/*
	 * sustract units booked
	 * 
	 */
	@Override
	public List<Order> buyStock(List<Order> orders) throws Exception {

		
			for (Order order : orders) {
				Product product = null;
				Stock stock = null;

				order.setState(ResultEnum.KO.getValue());
				if (order.getIdProduct() != null) {
					product = prDAO.get(order.getIdProduct());
				}

				if (product != null) {

					stock = product.getStock();
				}

				if (stock != null && stock.getBookedU() != null && stock.getAvailableU() != null
						&& order.getUnits() != null) {

					if (order.getUnits().compareTo(stock.getBookedU()) > 0) {
						order.setState(ResultEnum.INSUFFICIENT.getValue());
					} else {
						stock.setBookedU(stock.getBookedU().subtract(order.getUnits()));
						order.setState(ResultEnum.OK.getValue());
						prDAO.update(product);
					}
				}
			}
		
		return orders;

	}

	/*
	 * add units available sustract units booked
	 */
	@Override
	public List<Order> cancelStock(List<Order> orders) throws Exception {

		
			for (Order order : orders) {
				Product product = null;
				Stock stock = null;
				order.setState(ResultEnum.KO.getValue());

				if (order.getIdProduct() != null) {
					product = prDAO.get(order.getIdProduct());
				}

				if (product != null) {

					stock = product.getStock();
				}

				if (stock != null && stock.getBookedU() != null && stock.getAvailableU() != null
						&& order.getUnits() != null) {

					if (order.getUnits().compareTo(stock.getBookedU()) > 0) {
						order.setState(ResultEnum.INSUFFICIENT.getValue());
					} else {
						stock.setAvailableU(stock.getAvailableU().add(order.getUnits()));
						stock.setBookedU(stock.getBookedU().subtract(order.getUnits()));

						order.setState(ResultEnum.OK.getValue());
						prDAO.update(product);
					}
				}
			}
		

		return orders;
	}

}
