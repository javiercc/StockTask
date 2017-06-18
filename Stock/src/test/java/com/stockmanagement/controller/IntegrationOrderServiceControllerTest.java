package com.stockmanagement.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.stockmanagement.domain.entity.Order;
import com.stockmanagement.domain.entity.Orders;
import com.stockmanagement.domain.entity.Product;
import com.stockmanagement.domain.entity.Products;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationOrderServiceControllerTest {

	private static final int PORT = 4234;
	private static final String bindAddress = "localhost";
	private static final int EXPECTED_SIZE = 5;

	private static TJWSEmbeddedJaxrsServer server;
	private static Client client;

	@BeforeClass
	public static void setUpServer() throws Exception {
		server = new TJWSEmbeddedJaxrsServer();
		server.setPort(PORT);
		server.setBindAddress(bindAddress);
		ProductServiceController productController = new ProductServiceController();
		OrderServiceController orderController = new OrderServiceController();
		server.getDeployment().getResources().add(productController);
		server.getDeployment().getResources().add(orderController);
		server.start();
	}

	@BeforeClass
	public static void createClient() throws Exception {
		client = ClientBuilder.newClient();
	}

	@AfterClass
	public static void closeClient() {
		client.close();
	}

	@AfterClass
	public static void stop() {
		server.stop();
	}

	private String baseUri() {
		return "http://" + bindAddress + ":" + PORT;
	}

	@Test
	public void test01GetProduct() throws Exception {

		Response response = client.target(baseUri() + "/product/1").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		Product product = response.readEntity(Product.class);
		response.close();

		assertEquals(product.getIdProduct(), new Long(1));
		assertEquals(product.getStock().getAvailableU(), new BigDecimal(100));
		assertEquals(product.getStock().getBookedU(), new BigDecimal(0));

	}

	@Test
	public void test02GetAllProduct() throws Exception {

		Response response = client.target(baseUri() + "/product").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		Products products = response.readEntity(Products.class);
		assertEquals(EXPECTED_SIZE, products.getProducts().size());

	}

	@Test
	public void test03CheckInitialStock() throws Exception {
		Products products;

		Response response = client.target(baseUri() + "/product").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		products = response.readEntity(Products.class);
		response.close();

		assertEquals(EXPECTED_SIZE, products.getProducts().size());

		assertEquals(products.getProducts().get(0).getIdProduct(), new Long(1));
		assertEquals(products.getProducts().get(0).getStock().getAvailableU(), new BigDecimal(100));
		assertEquals(products.getProducts().get(0).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(1).getIdProduct(), new Long(2));
		assertEquals(products.getProducts().get(1).getStock().getAvailableU(), new BigDecimal(50));
		assertEquals(products.getProducts().get(1).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(2).getIdProduct(), new Long(3));
		assertNull(products.getProducts().get(2).getStock());
		assertNull(products.getProducts().get(2).getStock());
		assertEquals(products.getProducts().get(3).getIdProduct(), new Long(4));
		assertEquals(products.getProducts().get(3).getStock().getAvailableU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(3).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(4).getIdProduct(), new Long(5));
		assertEquals(products.getProducts().get(4).getStock().getAvailableU(), new BigDecimal(-77));
		assertEquals(products.getProducts().get(4).getStock().getBookedU(), new BigDecimal(0));

	}

	@Test
	public void test04BookProductAndCheckStates() throws Exception {
		// Booked
		Order order1 = new Order();
		order1.setIdProduct(1L);
		order1.setUnits(new BigDecimal(5));

		Order order2 = new Order();
		order2.setIdProduct(2L);
		order2.setUnits(new BigDecimal(25));

		Order order3 = new Order();
		order3.setIdProduct(3L);
		order3.setUnits(new BigDecimal(4));

		Order order4 = new Order();
		order4.setIdProduct(4L);
		order4.setUnits(new BigDecimal(50));

		Order order5 = new Order();
		order5.setIdProduct(5L);
		order5.setUnits(new BigDecimal(25));

		Order order6 = new Order();
		order6.setIdProduct(6L);
		order6.setUnits(new BigDecimal(80));

		List<Order> orderList = new ArrayList<>();
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order3);
		orderList.add(order4);
		orderList.add(order5);
		orderList.add(order6);

		Orders orders = new Orders();
		orders.setOrders(orderList);

		Response response = client.target(baseUri() + "/stock/book").request(MediaType.APPLICATION_JSON)
				.put(Entity.json(orders));

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		Orders ordersR = response.readEntity(Orders.class);
		response.close();

		assertEquals(ordersR.getOrders().get(0).getIdProduct(), new Long(1));
		assertEquals(ordersR.getOrders().get(0).getUnits(), new BigDecimal(5));
		assertEquals(ordersR.getOrders().get(0).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(1).getIdProduct(), new Long(2));
		assertEquals(ordersR.getOrders().get(1).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(1).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(2).getIdProduct(), new Long(3));
		assertEquals(ordersR.getOrders().get(2).getUnits(), new BigDecimal(4));
		assertEquals(ordersR.getOrders().get(2).getState(), new Integer(2));
		assertEquals(ordersR.getOrders().get(3).getIdProduct(), new Long(4));
		assertEquals(ordersR.getOrders().get(3).getUnits(), new BigDecimal(50));
		assertEquals(ordersR.getOrders().get(3).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(4).getIdProduct(), new Long(5));
		assertEquals(ordersR.getOrders().get(4).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(4).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(5).getIdProduct(), new Long(6));
		assertEquals(ordersR.getOrders().get(5).getUnits(), new BigDecimal(80));
		assertEquals(ordersR.getOrders().get(5).getState(), new Integer(2));

	}

	@Test
	public void test05CheckStockAfterBook() throws Exception {

		Products products;
		Response response = client.target(baseUri() + "/product").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		products = response.readEntity(Products.class);

		response.close();

		assertEquals(products.getProducts().get(0).getIdProduct(), new Long(1));
		assertEquals(products.getProducts().get(0).getStock().getAvailableU(), new BigDecimal(95));
		assertEquals(products.getProducts().get(0).getStock().getBookedU(), new BigDecimal(5));
		assertEquals(products.getProducts().get(1).getIdProduct(), new Long(2));
		assertEquals(products.getProducts().get(1).getStock().getAvailableU(), new BigDecimal(25));
		assertEquals(products.getProducts().get(1).getStock().getBookedU(), new BigDecimal(25));
		assertEquals(products.getProducts().get(2).getIdProduct(), new Long(3));
		assertNull(products.getProducts().get(2).getStock());
		assertNull(products.getProducts().get(2).getStock());
		assertEquals(products.getProducts().get(3).getIdProduct(), new Long(4));
		assertEquals(products.getProducts().get(3).getStock().getAvailableU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(3).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(4).getIdProduct(), new Long(5));
		assertEquals(products.getProducts().get(4).getStock().getAvailableU(), new BigDecimal(-77));
		assertEquals(products.getProducts().get(4).getStock().getBookedU(), new BigDecimal(0));

	}

	@Test
	public void test06CancelProductAndCheckStates() throws Exception {
		// Booked
		Order order1 = new Order();
		order1.setIdProduct(1L);
		order1.setUnits(new BigDecimal(5));

		Order order2 = new Order();
		order2.setIdProduct(2L);
		order2.setUnits(new BigDecimal(25));

		Order order3 = new Order();
		order3.setIdProduct(3L);
		order3.setUnits(new BigDecimal(4));

		Order order4 = new Order();
		order4.setIdProduct(4L);
		order4.setUnits(new BigDecimal(50));

		Order order5 = new Order();
		order5.setIdProduct(5L);
		order5.setUnits(new BigDecimal(25));

		Order order6 = new Order();
		order6.setIdProduct(6L);
		order6.setUnits(new BigDecimal(80));

		List<Order> orderList = new ArrayList<>();
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order3);
		orderList.add(order4);
		orderList.add(order5);
		orderList.add(order6);

		Orders orders = new Orders();
		orders.setOrders(orderList);

		Response response = client.target(baseUri() + "/stock/cancel").request(MediaType.APPLICATION_JSON)
				.put(Entity.json(orders));

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		Orders ordersR = response.readEntity(Orders.class);
		response.close();

		assertEquals(ordersR.getOrders().get(0).getIdProduct(), new Long(1));
		assertEquals(ordersR.getOrders().get(0).getUnits(), new BigDecimal(5));
		assertEquals(ordersR.getOrders().get(0).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(1).getIdProduct(), new Long(2));
		assertEquals(ordersR.getOrders().get(1).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(1).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(2).getIdProduct(), new Long(3));
		assertEquals(ordersR.getOrders().get(2).getUnits(), new BigDecimal(4));
		assertEquals(ordersR.getOrders().get(2).getState(), new Integer(2));
		assertEquals(ordersR.getOrders().get(3).getIdProduct(), new Long(4));
		assertEquals(ordersR.getOrders().get(3).getUnits(), new BigDecimal(50));
		assertEquals(ordersR.getOrders().get(3).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(4).getIdProduct(), new Long(5));
		assertEquals(ordersR.getOrders().get(4).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(4).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(5).getIdProduct(), new Long(6));
		assertEquals(ordersR.getOrders().get(5).getUnits(), new BigDecimal(80));
		assertEquals(ordersR.getOrders().get(5).getState(), new Integer(2));

	}

	@Test
	public void test07CheckStockAfterCancel() throws Exception {

		Products products;
		Response response = client.target(baseUri() + "/product").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		products = response.readEntity(Products.class);

		response.close();

		assertEquals(products.getProducts().get(0).getIdProduct(), new Long(1));
		assertEquals(products.getProducts().get(0).getStock().getAvailableU(), new BigDecimal(100));
		assertEquals(products.getProducts().get(0).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(1).getIdProduct(), new Long(2));
		assertEquals(products.getProducts().get(1).getStock().getAvailableU(), new BigDecimal(50));
		assertEquals(products.getProducts().get(1).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(2).getIdProduct(), new Long(3));
		assertNull(products.getProducts().get(2).getStock());
		assertNull(products.getProducts().get(2).getStock());
		assertEquals(products.getProducts().get(3).getIdProduct(), new Long(4));
		assertEquals(products.getProducts().get(3).getStock().getAvailableU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(3).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(4).getIdProduct(), new Long(5));
		assertEquals(products.getProducts().get(4).getStock().getAvailableU(), new BigDecimal(-77));
		assertEquals(products.getProducts().get(4).getStock().getBookedU(), new BigDecimal(0));

	}

	@Test
	public void test08BookProductAndCheckStates() throws Exception {
		// Booked
		Order order1 = new Order();
		order1.setIdProduct(1L);
		order1.setUnits(new BigDecimal(5));

		Order order2 = new Order();
		order2.setIdProduct(2L);
		order2.setUnits(new BigDecimal(25));

		Order order3 = new Order();
		order3.setIdProduct(3L);
		order3.setUnits(new BigDecimal(4));

		Order order4 = new Order();
		order4.setIdProduct(4L);
		order4.setUnits(new BigDecimal(50));

		Order order5 = new Order();
		order5.setIdProduct(5L);
		order5.setUnits(new BigDecimal(25));

		Order order6 = new Order();
		order6.setIdProduct(6L);
		order6.setUnits(new BigDecimal(80));

		List<Order> orderList = new ArrayList<>();
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order3);
		orderList.add(order4);
		orderList.add(order5);
		orderList.add(order6);

		Orders orders = new Orders();
		orders.setOrders(orderList);

		Response response = client.target(baseUri() + "/stock/book").request(MediaType.APPLICATION_JSON)
				.put(Entity.json(orders));

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		Orders ordersR = response.readEntity(Orders.class);
		response.close();

		assertEquals(ordersR.getOrders().get(0).getIdProduct(), new Long(1));
		assertEquals(ordersR.getOrders().get(0).getUnits(), new BigDecimal(5));
		assertEquals(ordersR.getOrders().get(0).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(1).getIdProduct(), new Long(2));
		assertEquals(ordersR.getOrders().get(1).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(1).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(2).getIdProduct(), new Long(3));
		assertEquals(ordersR.getOrders().get(2).getUnits(), new BigDecimal(4));
		assertEquals(ordersR.getOrders().get(2).getState(), new Integer(2));
		assertEquals(ordersR.getOrders().get(3).getIdProduct(), new Long(4));
		assertEquals(ordersR.getOrders().get(3).getUnits(), new BigDecimal(50));
		assertEquals(ordersR.getOrders().get(3).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(4).getIdProduct(), new Long(5));
		assertEquals(ordersR.getOrders().get(4).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(4).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(5).getIdProduct(), new Long(6));
		assertEquals(ordersR.getOrders().get(5).getUnits(), new BigDecimal(80));
		assertEquals(ordersR.getOrders().get(5).getState(), new Integer(2));

	}

	@Test
	public void test09CheckStockAfterBook() throws Exception {

		Products products;
		Response response = client.target(baseUri() + "/product").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		products = response.readEntity(Products.class);

		response.close();

		assertEquals(products.getProducts().get(0).getIdProduct(), new Long(1));
		assertEquals(products.getProducts().get(0).getStock().getAvailableU(), new BigDecimal(95));
		assertEquals(products.getProducts().get(0).getStock().getBookedU(), new BigDecimal(5));
		assertEquals(products.getProducts().get(1).getIdProduct(), new Long(2));
		assertEquals(products.getProducts().get(1).getStock().getAvailableU(), new BigDecimal(25));
		assertEquals(products.getProducts().get(1).getStock().getBookedU(), new BigDecimal(25));
		assertEquals(products.getProducts().get(2).getIdProduct(), new Long(3));
		assertNull(products.getProducts().get(2).getStock());
		assertNull(products.getProducts().get(2).getStock());
		assertEquals(products.getProducts().get(3).getIdProduct(), new Long(4));
		assertEquals(products.getProducts().get(3).getStock().getAvailableU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(3).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(4).getIdProduct(), new Long(5));
		assertEquals(products.getProducts().get(4).getStock().getAvailableU(), new BigDecimal(-77));
		assertEquals(products.getProducts().get(4).getStock().getBookedU(), new BigDecimal(0));

	}

	@Test
	public void test10BuyProductAndCheckStates() throws Exception {
		// Booked
		Order order1 = new Order();
		order1.setIdProduct(1L);
		order1.setUnits(new BigDecimal(5));

		Order order2 = new Order();
		order2.setIdProduct(2L);
		order2.setUnits(new BigDecimal(25));

		Order order3 = new Order();
		order3.setIdProduct(3L);
		order3.setUnits(new BigDecimal(4));

		Order order4 = new Order();
		order4.setIdProduct(4L);
		order4.setUnits(new BigDecimal(50));

		Order order5 = new Order();
		order5.setIdProduct(5L);
		order5.setUnits(new BigDecimal(25));

		Order order6 = new Order();
		order6.setIdProduct(6L);
		order6.setUnits(new BigDecimal(80));

		List<Order> orderList = new ArrayList<>();
		orderList.add(order1);
		orderList.add(order2);
		orderList.add(order3);
		orderList.add(order4);
		orderList.add(order5);
		orderList.add(order6);

		Orders orders = new Orders();
		orders.setOrders(orderList);

		Response response = client.target(baseUri() + "/stock/buy").request(MediaType.APPLICATION_JSON)
				.put(Entity.json(orders));

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		Orders ordersR = response.readEntity(Orders.class);
		response.close();

		assertEquals(ordersR.getOrders().get(0).getIdProduct(), new Long(1));
		assertEquals(ordersR.getOrders().get(0).getUnits(), new BigDecimal(5));
		assertEquals(ordersR.getOrders().get(0).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(1).getIdProduct(), new Long(2));
		assertEquals(ordersR.getOrders().get(1).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(1).getState(), new Integer(0));
		assertEquals(ordersR.getOrders().get(2).getIdProduct(), new Long(3));
		assertEquals(ordersR.getOrders().get(2).getUnits(), new BigDecimal(4));
		assertEquals(ordersR.getOrders().get(2).getState(), new Integer(2));
		assertEquals(ordersR.getOrders().get(3).getIdProduct(), new Long(4));
		assertEquals(ordersR.getOrders().get(3).getUnits(), new BigDecimal(50));
		assertEquals(ordersR.getOrders().get(3).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(4).getIdProduct(), new Long(5));
		assertEquals(ordersR.getOrders().get(4).getUnits(), new BigDecimal(25));
		assertEquals(ordersR.getOrders().get(4).getState(), new Integer(1));
		assertEquals(ordersR.getOrders().get(5).getIdProduct(), new Long(6));
		assertEquals(ordersR.getOrders().get(5).getUnits(), new BigDecimal(80));
		assertEquals(ordersR.getOrders().get(5).getState(), new Integer(2));

	}

	@Test
	public void test11CheckStockAfterBuy() throws Exception {

		Products products;
		Response response = client.target(baseUri() + "/product").request(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

		products = response.readEntity(Products.class);

		response.close();

		assertEquals(products.getProducts().get(0).getIdProduct(), new Long(1));
		assertEquals(products.getProducts().get(0).getStock().getAvailableU(), new BigDecimal(95));
		assertEquals(products.getProducts().get(0).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(1).getIdProduct(), new Long(2));
		assertEquals(products.getProducts().get(1).getStock().getAvailableU(), new BigDecimal(25));
		assertEquals(products.getProducts().get(1).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(2).getIdProduct(), new Long(3));
		assertNull(products.getProducts().get(2).getStock());
		assertNull(products.getProducts().get(2).getStock());
		assertEquals(products.getProducts().get(3).getIdProduct(), new Long(4));
		assertEquals(products.getProducts().get(3).getStock().getAvailableU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(3).getStock().getBookedU(), new BigDecimal(0));
		assertEquals(products.getProducts().get(4).getIdProduct(), new Long(5));
		assertEquals(products.getProducts().get(4).getStock().getAvailableU(), new BigDecimal(-77));
		assertEquals(products.getProducts().get(4).getStock().getBookedU(), new BigDecimal(0));

	}
}