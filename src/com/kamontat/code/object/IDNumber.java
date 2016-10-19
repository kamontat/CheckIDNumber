package com.kamontat.code.object;

import com.kamontat.code.constant.Status;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.kamontat.code.constant.Status.*;

/**
 * @author kamontat
 * @since 19/8/59 - 20:41
 */
public class IDNumber {
	private char[] splitID;
	private String id;
	private LocalDateTime time;
	private Status statusMessage;

	public IDNumber() {
		this.id = null;
		splitID = null;

		statusMessage = NOT_CREATE;

		time = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));

	}

	public IDNumber(String id) {
		this.id = id;
		splitID = id.toCharArray();

		if (checkLength() && isIDCorrect()) {
			statusMessage = OK;
		}

		time = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));
	}

	public IDNumber(String id, LocalDateTime time) {
		this.id = id;
		splitID = id.toCharArray();

		if (checkLength() && isIDCorrect()) {
			statusMessage = OK;
		}

		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		splitID = id.toCharArray();

		if (checkLength() && isIDCorrect()) {
			statusMessage = OK;

			time = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));
		}
	}

	public Status getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(Status statusMessage) {
		this.statusMessage = statusMessage;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String getType() {
		if (splitID[0] == '1') {
			return "สัญชาติไทย และ แจ้งเกิดทันเวลา";
		} else if (splitID[0] == '2') {
			return "สัญชาติไทย และ แจ้งเกิดไม่ทันเวลา";
		} else if (splitID[0] == '3') {
			return "คนไทยหรือคนต่างด้าวถูกกฏหมาย\nที่มีชื่ออยู่ในทะเบียนบ้านก่อนวันที่ 31 พฤษภาคม พ.ศ. 2527";
		} else if (splitID[0] == '4') {
			return "คนไทยหรือคนต่างด้าวถูกกฏหมายที่ไม่มีเลขประจำตัวประชาชน\nหรือไม่ทันได้เลขประจำตัวก็ขอย้ายบ้านก่อน";
		} else if (splitID[0] == '5') {
			return "คนไทยที่ได้รับอนุมัติให้เพิ่มชื่อในกรณีตกสำรวจหรือคนที่ถือ 2 สัญชาติ";
		} else if (splitID[0] == '6') {
			return "ผู้ที่เข้าเมืองโดยไม่ชอบด้วยกฎหมาย \nหรือ ผู้ที่เข้าเมืองโดยชอบด้วยกฎหมายแต่อยู่ในลักษณะชั่วคราว";
		} else if (splitID[0] == '7') {
			return "บุตรของบุคคลประเภทที่ 6 ซึ่งเกิดในประเทศไทย";
		} else if (splitID[0] == '8') {
			return "คนต่างด้าวถูกกฎหมาย ที่ได้รับการให้สัญชาติไทยตั้งแต่หลังวันที่ 31 พฤษภาคม พ.ศ. 2527";
		} else if (splitID[0] == '0') {
			return "บุคคลที่ไม่มีสถานะทางทะเบียนราษฎร ไม่มีสัญชาติ";
		} else {
			return "No type";
		}
	}

	public String getIDAddress() {
		return String.copyValueOf(splitID, 1, 4);
	}

	public String getIDProvince() {
		return String.copyValueOf(splitID, 1, 2);
	}

	public String getIDDistrict() {
		return String.copyValueOf(splitID, 3, 2);
	}

	public String getIDBC() {
		return String.copyValueOf(splitID, 5, 5);
	}

	public String getIDOrder() {
		return String.copyValueOf(splitID, 10, 2);
	}

	private boolean checkLength() {
		if (id.length() == 13) {
			return true;
		}
		statusMessage = OUT_LENGTH;
		return false;
	}

	private boolean isIDCorrect() {
		if (splitID[0] == '9') {
			statusMessage = UNCORRECTED;
			return false;
		}

		int total = 0;
		for (int i = 1; i <= 12; i++) {
			int digit = Character.getNumericValue(splitID[i - 1]);
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

	public boolean isSame(IDNumber id) {
		return getId().equals(id.getId());
	}

	@Override
	public String toString() {
		return id;
	}

	public String saveFormat() {
		return id + " " + time.toLocalDate() + " " + time.toLocalTime() + " Asia/Bangkok";
	}
}
