package com.stockmanagement.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stockmanagement.domain.entity.Orders;
import com.stockmanagement.domain.service.StockService;
import com.stockmanagement.domain.service.StockServiceImpl;

@Path("/stock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderServiceController {

	// inject
	private StockService stockService = new StockServiceImpl();

	public OrderServiceController() {
		super();
	}

	@PUT
	@Path("/book")
	public Orders bookStock(Orders orders) {

		try {
			stockService.bookStock(orders.getOrders());
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
		return orders;

	}

	@PUT
	@Path("/buy")
	public Orders buyStock(Orders orders) {
		try {
			stockService.buyStock(orders.getOrders());
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
		return orders;

	}

	@PUT
	@Path("/cancel")
	public Orders cancelStock(Orders orders) {

		try {
			stockService.cancelStock(orders.getOrders());
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
		return orders;

	}

}
