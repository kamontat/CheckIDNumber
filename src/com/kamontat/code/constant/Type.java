package com.kamontat.code.constant;

/**
 * this enum use for check type of address in id-number
 *
 * @author kamontat
 * @version 1.0
 * @since 16/9/59 - 23:07
 */
public enum Type {
	NO_DISTRICT("NO district"),
	NO_EVERYTHING("NO province and district"),
	OK("perfect ID");

	private String message;

	Type(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
