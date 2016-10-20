package com.kamontat.code.constant;

/**
 * this enum use for check status of id-number
 *
 * @author kamontat
 * @version 1.0
 * @since 16/9/59 - 23:05
 */
public enum Status {
	OK("GOOD"),
	OUT_LENGTH("MUST be 13 Digit"),
	NOT_NINE("First digit CANNOT be 9"),
	UNCORRECTED("ID NOT match with id rule"),
	NOT_CREATE("NEVER assign ID");

	private String message;

	Status(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
