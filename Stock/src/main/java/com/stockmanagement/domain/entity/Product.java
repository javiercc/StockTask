package com.stockmanagement.domain.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product extends Entity<Long> {

	private Long idProduct;
	private String name;
	private Stock stock;
	private boolean isAvailable;

	public Product(Long idProduct) {
		super(idProduct);
		this.idProduct = idProduct;
	}

	public Product() {
		super();
	}

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct
	 *            the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable
	 *            the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stock
	 */
	// @XmlTransient
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock
	 *            the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * String presentation of the Object
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return new StringBuilder("{Product id: ").append(idProduct).append(", name: ").append(name)
				.append(", available: ").append(isAvailable).append("}").toString();
	}

}
