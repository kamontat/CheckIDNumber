package com.kamontat.code.constant;

/**
 * This enum use for check type of address in id-number <br>
 * This enum have <br>
 * 1) NO_EVERYTHING -> if id don't have both province and district
 * 2) NO_DISTRICT   -> if id don't district
 * 3) OK            -> when id has all location
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
