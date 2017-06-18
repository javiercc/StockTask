package com.stockmanagement.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stockmanagement.domain.entity.Product;
import com.stockmanagement.domain.entity.Products;
import com.stockmanagement.domain.service.ProductService;
import com.stockmanagement.domain.service.ProductServiceImpl;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductServiceController {

	// inject
	private ProductService productService = new ProductServiceImpl();

	public ProductServiceController() {
		super();
	}

	@GET
	@Path("{id}")
	public Product getProduct(@PathParam("id") Long id) {
		try {
			return productService.findById(id);
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}

	}

	@GET
	public Products getAllProduct() {
		try {
			Products products = new Products();
			products.setProducts(productService.getAll());
			return products;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}

	}

}
