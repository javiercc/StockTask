package com.stockmanagement.domain.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {

	@XmlElement(name = "order")
	List<Order> ordersList;

	/**
	 * @return the orders
	 */
	public List<Order> getOrders() {
		return ordersList;
	}

	/**
	 * @param orders
	 */
	public void setOrders(List<Order> ordersList) {
		this.ordersList = ordersList;
	}

}
