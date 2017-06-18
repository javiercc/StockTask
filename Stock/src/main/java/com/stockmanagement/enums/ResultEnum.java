package com.stockmanagement.enums;

public enum ResultEnum {

	OK(0, "OK"), INSUFFICIENT(1, "INSUFFICIENT STOCK"),KO(2, "KO");

	private Integer value;
	private String description;

	ResultEnum(final Integer value, final String description) {
		this.value = value;
		this.description = description;

	}

	public Integer getValue() {
		return this.value;
	}

	public String getDescription() {
		return description;
	}

}
