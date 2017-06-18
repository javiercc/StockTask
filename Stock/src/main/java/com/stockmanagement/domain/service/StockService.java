package com.stockmanagement.domain.service;

import java.util.List;

import com.stockmanagement.domain.entity.Order;

public interface StockService {

	public List<Order> bookStock(List<Order> orders) throws Exception;

	public List<Order> buyStock(List<Order> orders) throws Exception;

	public List<Order> cancelStock(List<Order> orders) throws Exception;

}