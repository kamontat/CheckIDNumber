package com.kamontat.code.object;

import com.kamontat.code.constant.Status;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.kamontat.code.constant.Status.*;
import static com.kamontat.code.database.Database.idList;
import static com.kamontat.config.Config.canDuplicate;

/**
 * object that deal with <code>id-number</code> of this program <br>
 * <p>
 * keep id-number by String, <br>
 * keep update createAt in form of <code>LocalDateTime</code>, <br>
 * and status of this id-number by using id rule of <b>Thailand</b> <br>
 *
 * @author kamontat
 * @version 2.4
 * @since 19/8/59 - 20:41
 */
public class IDNumber {
	private char[] splitID;
	private String id;
	private LocalDateTime createAt;
	private Status status;
	private Location location = new Location();
	
	/**
	 * <b>init</b> id-number by using parameter <code>id</code>
	 *
	 * @param id
	 * 		some id-number
	 */
	public IDNumber(String id) {
		this.id = id;
		splitID = id.toCharArray();
		createAt = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));
		
		if (isIDCorrect()) {
			status = isDuplicate() ? DUPLICATE: OK;
			location = new Location(this);
		}
	}
	
	/**
	 * <b>init</b> id-number by using parameter <code>id</code> and <code>createAt</code> <br>
	 * it's method using for loading id from file (server)
	 *
	 * @param id
	 * 		some id-number
	 * @param createAt
	 * 		createAt that update this id-number
	 */
	public IDNumber(String id, LocalDateTime createAt) {
		this.id = id;
		splitID = id.toCharArray();
		this.createAt = createAt;
		
		if (isIDCorrect()) {
			status = isDuplicate() ? DUPLICATE: OK;
			location = new Location(this);
		}
	}
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
		splitID = id.toCharArray();
		
		if (isIDCorrect()) {
			status = isDuplicate() ? DUPLICATE: OK;
			location = new Location(this);
		}
	}
	
	/**
	 * get current status <br>
	 *
	 * @return status
	 * @see Status
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * set new create time (debug only)
	 *
	 * @param createAt
	 * 		new create time
	 */
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	
	/**
	 * get create time
	 *
	 * @return LocalDataTime that create this id
	 */
	public LocalDateTime getCreateAt() {
		return createAt;
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
		String print = "<html>";
		if (splitID[0] == '1') {
			print += "สัญชาติไทย และ แจ้งเกิดทันเวลา ";
		} else if (splitID[0] == '2') {
			print += "สัญชาติไทย และ แจ้งเกิดไม่ทันเวลา ";
		} else if (splitID[0] == '3') {
			print += "คนไทยหรือคนต่างด้าวถูกกฏหมาย <br>ที่มีชื่ออยู่ในทะเบียนบ้านก่อนวันที่ 31 พฤษภาคม พ.ศ. 2527 ";
		} else if (splitID[0] == '4') {
			print += "คนไทยหรือคนต่างด้าวถูกกฏหมายที่ไม่มีเลขประจำตัวประชาชน <br>หรือไม่ทันได้เลขประจำตัวก็ขอย้ายบ้านก่อน ";
		} else if (splitID[0] == '5') {
			print += "คนไทยที่ได้รับอนุมัติให้เพิ่มชื่อในกรณีตกสำรวจหรือคนที่ถือ 2 สัญชาติ ";
		} else if (splitID[0] == '6') {
			print += "ผู้ที่เข้าเมืองโดยไม่ชอบด้วยกฎหมาย <br>หรือ ผู้ที่เข้าเมืองโดยชอบด้วยกฎหมายแต่อยู่ในลักษณะชั่วคราว ";
		} else if (splitID[0] == '7') {
			print += "บุตรของบุคคลประเภทที่ 6 ซึ่งเกิดในประเทศไทย ";
		} else if (splitID[0] == '8') {
			print += "คนต่างด้าวถูกกฎหมาย <br>ที่ได้รับการให้สัญชาติไทยตั้งแต่หลังวันที่ 31 พฤษภาคม พ.ศ. 2527 ";
		} else if (splitID[0] == '0') {
			print += "บุคคลที่ไม่มีสถานะทางทะเบียนราษฎร <br>หรือไม่มีสัญชาติ ";
		} else {
			print += "No Genre  ";
		}
		print += "</html>";
		return print;
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
	 * check first digit can't be 9
	 *
	 * @return if first digit is 9 return false, otherwise return true
	 */
	private boolean checkFirstDigit() {
		if (splitID[0] == '9') status = NOT_NINE;
		return splitID[0] != '9';
	}
	
	/**
	 * all id number must be integer
	 *
	 * @return if some part of id <b>isn't</b> number return false, otherwise return true
	 */
	private boolean checkAlphabet() {
		for (char id : splitID) {
			if (!Character.isDigit(id)) {
				status = NOT_ALPHABET;
				return false;
			}
		}
		return true;
	}
	
	/**
	 * check id is passing id rule of <b>Thailand</b> or not
	 *
	 * @return if pass return true, otherwise return false
	 */
	private boolean isIDCorrect() {
		// check alphabet
		if (!checkAlphabet()) return false;
		
		// check fist digit
		if (!checkFirstDigit()) return false;
		
		// check length
		if (!checkLength()) return false;
		
		// check rule
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
		status = NOT_CORRECT;
		return false;
	}
	
	/**
	 * For check duplicate id in the list
	 *
	 * @return if <code>some id</code> is same with <code>this id</code> return true, otherwise return false
	 */
	public boolean isDuplicate() {
		if (canDuplicate) return false;
		for (IDNumber id : idList) {
			if (id.getId().equals(getId())) return true;
		}
		return false;
	}
	
	public boolean isContain(String number) {
		return id.startsWith(number);
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
		return id + " " + createAt.toLocalDate() + " " + createAt.toLocalTime() + " Asia/Bangkok";
	}
	
	public static boolean validID(String id) {
		IDNumber idNumber = new IDNumber(id);
		return idNumber.isIDCorrect();
	}
}
