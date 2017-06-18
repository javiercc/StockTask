package com.stockmanagement.domain.entity;

import java.math.BigDecimal;

/**
 * 
 * @author Samsung
 *
 */
public class Stock extends Entity<Long> {

	private Long idStock;

	private Long idProduct;
	private BigDecimal availableU;
	private BigDecimal bookedU;

	public Stock(Long idStock) {
		super(idStock);
		this.idStock = idStock;

	}

	public Stock() {
		super();
	}

	/**
	 * @return the idStock
	 */
	public Long getIdStock() {
		return idStock;
	}

	/**
	 * @param idStock
	 *            the idStock to set
	 */
	public void setIdStock(Long idStock) {
		this.idStock = idStock;
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
	 * @return the availableU
	 */
	public BigDecimal getAvailableU() {
		return availableU;
	}

	/**
	 * @param availableU
	 *            the availableU to set
	 */
	public void setAvailableU(BigDecimal availableU) {
		this.availableU = availableU;
	}

	/**
	 * @return the bookedU
	 */
	public BigDecimal getBookedU() {
		return bookedU;
	}

	/**
	 * @param bookedU
	 *            the bookedU to set
	 */
	public void setBookedU(BigDecimal bookedU) {
		this.bookedU = bookedU;
	}

	/**
	 * String presentation of the Object
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return new StringBuilder("{Stock id: ").append(idStock).append(", product id: ").append(idProduct)
				.append(", available units: ").append(availableU).append(", booked units: ").append(bookedU).append("}")
				.toString();
	}

}
