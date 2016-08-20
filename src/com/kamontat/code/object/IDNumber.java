package com.kamontat.code.object;

/**
 * @author kamontat
 * @since 19/8/59 - 20:41
 */
public class IDNumber {
	public static int OK = 0;
	public static int OUT_LENGTH = 1;
	public static int UNCORRECTED = 2;

	private char[] splitID;
	private String id;
	private int statusMessage;


	public IDNumber(String id) {
		this.id = id;
		splitID = id.toCharArray();

		if (checkLength() && isIDCorrect()) {
			statusMessage = OK;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(int statusMessage) {
		this.statusMessage = statusMessage;
	}

	public boolean isSame(IDNumber id) {
		return getId().equals(id.getId());
	}

	public String getType() {
		if (splitID[0] == 1) {
			return "Thai nationality and Birth on time";
		} else if (splitID[0] == 2) {
			return "Thai nationality and Birth late";
		} else if (splitID[0] == 3) {
			return "Thai nationality or other that have name on Household Registration before 31/05/2527";
		} else if (splitID[0] == 4) {
			return "Thai nationality or other that move into Thailand but don't have ID number";
		} else if (splitID[0] == 5) {
			return "Thai nationality that allow to Add Name Later with some reason, like multiple nationality";
		} else if (splitID[0] == 6) {
			return "People who came to thailand Illegal or Legal Temporary";
		} else if (splitID[0] == 7) {
			return "type 6's son, who was born in Thailand.";
		} else if (splitID[0] == 8) {
			return "Other nationality that have Thai nationality legal";
		} else if (splitID[0] == 0) {
			return "People who don't have Thai nationality but can live in Thailand Temporary";
		} else {
			return "No type";
		}
	}

	public String getAddress() {
		String[] allProvince = new String[77];

		return null;
	}

	public boolean checkLength() {
		if (id.length() == 13) {
			return true;
		}
		statusMessage = OUT_LENGTH;
		return false;
	}

	public boolean isIDCorrect() {
		if (splitID[0] == '9') {
			statusMessage = UNCORRECTED;
			return false;
		}

		int total = 0;
		for (int i = 1; i <= 12; i++) {
			int digit = Character.getNumericValue(splitID[i]);
			total += (14 - i) * digit;
		}
		total = (total % 11);

		int lastDigit = Character.getNumericValue(splitID[splitID.length - 1]);

		if (total <= 1) {
			if (lastDigit == 1 - total) {
				return true;
			}
		} else if (total > 1) {
			if (lastDigit == 11 - total) {
				return true;
			}
		}
		statusMessage = UNCORRECTED;
		return false;
	}

	@Override
	public String toString() {
		return id;
	}
}
