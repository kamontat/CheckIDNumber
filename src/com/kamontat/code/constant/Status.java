package com.kamontat.code.constant;

import java.awt.*;

/**
 * This enum use for check status of id-number <br>
 * This enum have <br>
 *     1) OK          -> id is correct <br>
 *     2) OUT_LENGTH  -> length of id isn't 13 <br>
 *     3) NOT_NINE    -> first digit is 9 <br>
 *     4) DUPLICATE   -> this id have duplicate with other in program <br>
 *     5) UNCORRECTED -> id don't match with id rule <br>
 *     6) NOT_CREATE  -> id never be assign <br>
 *
 *
 * @author kamontat
 * @version 1.0
 * @since 16/9/59 - 23:05
 */
public enum Status {
	OK("OK (Good ID)", new Color(0, 200, 0)),
	OUT_LENGTH("Warning (MUST be 13 Digit)", new Color(255, 213, 0)),
	NOT_NINE("Warning (First digit CANNOT be 9)", new Color(255, 119, 0)),
	DUPLICATE("Error (ID duplicate)", new Color(255, 0, 0)),
	UNCORRECTED("Error (ID NOT match with id rule)", new Color(195, 0, 255)),
	NOT_CREATE("Error (NEVER assign ID)", new Color(255, 0, 0));

	private String message;
	private Color color;

	Status(String message, Color color) {
		this.message = message;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return message;
	}
}
