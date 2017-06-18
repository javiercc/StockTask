package com.stockmanagement.domain.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "order")
@XmlType(propOrder = { "idproduct", "units", "state" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@XmlElement(name = "idproduct")
	private Long idProduct;
	private BigDecimal units;
	private Integer state;

	public Order() {
		super();
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
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
	 * @return the units
	 */
	public BigDecimal getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return new StringBuilder("{idProdut: ").append(idProduct).append(", units: ").append(units).append(", state: ")
				.append(state).append("}").toString();
	}
}
