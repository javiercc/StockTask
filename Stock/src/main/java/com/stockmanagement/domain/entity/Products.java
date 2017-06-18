package com.stockmanagement.domain.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {

	@XmlElement(name = "product")
	List<Product> productsList;

	/**
	 * @return the productsList
	 */
	public List<Product> getProducts() {
		return productsList;
	}

	/**
	 * @param productsList
	 */
	public void setProducts(List<Product> productsList) {
		this.productsList = productsList;
	}

}
