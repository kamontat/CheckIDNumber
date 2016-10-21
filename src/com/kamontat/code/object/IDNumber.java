package com.kamontat.code.object;

import com.kamontat.code.constant.Status;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.kamontat.code.constant.Status.*;

/**
 * object that deal with <code>id-number</code> of this program <br>
 * <p>
 * keep id-number by String, <br>
 * keep update time in form of <code>LocalDateTime</code>, <br>
 * and status of this id-number by using id rule of <b>Thailand</b> <br>
 *
 * @author kamontat
 * @version 2.2
 * @since 19/8/59 - 20:41
 */
public class IDNumber {
	private char[] splitID;
	private String id;
	private LocalDateTime time;
	private Status status;
	private Location location = new Location();

	/**
	 * <b>init</b> id-number with nothing
	 */
	public IDNumber() {
		this.id = null;
		splitID = null;

		status = NOT_CREATE;

		time = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));

	}

	/**
	 * <b>init</b> id-number by using parameter <code>id</code>
	 *
	 * @param id
	 * 		some id-number
	 */
	public IDNumber(String id) {
		this.id = id;
		splitID = id.toCharArray();
		time = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));

		if (checkLength() && isIDCorrect()) {
			status = OK;
			location = new Location(this);
		}
	}

	/**
	 * <b>init</b> id-number by using parameter <code>id</code> and <code>time</code> <br>
	 * it's method using for loading id from file (server)
	 *
	 * @param id
	 * 		some id-number
	 * @param time
	 * 		time that update this id-number
	 */
	public IDNumber(String id, LocalDateTime time) {
		this.id = id;
		splitID = id.toCharArray();
		this.time = time;


		if (checkLength() && isIDCorrect()) {
			status = OK;
			location = new Location(this);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		splitID = id.toCharArray();

		if (checkLength() && isIDCorrect()) {
			status = OK;
			location = new Location(this);

			time = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));
		}
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public LocalDateTime getTime() {
		return time;
	}

	/**
	 * get <code>location</code> of this id <br>
	 *
	 * @return first is The Province, second is District <br>
	 * @see Location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * convert first digit in id-number to genre in form of String by using id rule of <b>Thailand</b>
	 *
	 * @return string of genre
	 */
	public String getIDGenre() {
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
			return "No Genre";
		}
	}

	/**
	 * get address in id-number by using id rule of <b>Thailand</b><br>
	 * <b>address</b> is mean province + district
	 *
	 * @return address in form of number
	 */
	public String getIDAddress() {
		return String.copyValueOf(splitID, 1, 4);
	}

	/**
	 * get province in id-number by using id rule of <b>Thailand</b>
	 *
	 * @return province in form of number
	 */
	public String getIDProvince() {
		return String.copyValueOf(splitID, 1, 2);
	}

	/**
	 * get district in id-number by using id rule of <b>Thailand</b>
	 *
	 * @return district in form of number
	 */
	public String getIDDistrict() {
		return String.copyValueOf(splitID, 3, 2);
	}

	/**
	 * get ID of <b>Birth Certificate</b> in id-number by using id rule of <b>Thailand</b>
	 *
	 * @return Birth Certificate ID in form of number
	 */
	public String getIDBC() {
		return String.copyValueOf(splitID, 5, 5);
	}

	/**
	 * get order in <b>Birth Certificate</b> by using id rule of <b>Thailand</b>
	 *
	 * @return order of that in form of number
	 */
	public String getIDOrder() {
		return String.copyValueOf(splitID, 10, 2);
	}

	/**
	 * check length of id
	 *
	 * @return if length isn't 13 change status and return false, otherwise return true
	 */
	private boolean checkLength() {
		if (id.length() != 13) status = OUT_LENGTH;
		return id.length() == 13;
	}

	/**
	 * check id is passing id rule of <b>Thailand</b> or not
	 *
	 * @return if pass return true, otherwise return false
	 */
	private boolean isIDCorrect() {
		if (splitID[0] == '9') {
			status = NOT_NINE;
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

		status = UNCORRECTED;
		return false;
	}

	/**
	 * For check duplicate id
	 *
	 * @param id
	 * 		some id
	 * @return if <code>some id</code> is same with <code>this id</code> return true, otherwise return false
	 */
	public boolean isSame(IDNumber id) {
		return getId().equals(id.getId());
	}

	@Override
	public String toString() {
		//		String text = "id: " + id + "\n";
		//		text += "status: " + status + "\n";
		//		text += "type: " + getLocation().getType() + "\n";
		//		text += "\n\n";
		return id;
	}

	/**
	 * use for saving id into file (server)
	 *
	 * @return format to save in file
	 */
	public String saveFormat() {
		return id + " " + time.toLocalDate() + " " + time.toLocalTime() + " Asia/Bangkok";
	}

	public static boolean validID(String id) {
		IDNumber idNumber = new IDNumber(id);
		return idNumber.isIDCorrect() && idNumber.checkLength();
	}
}
