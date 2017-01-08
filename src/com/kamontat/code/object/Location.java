package com.kamontat.code.object;

import com.kamontat.code.constant.Type;

import static com.kamontat.code.model.LocationModel.districts;
import static com.kamontat.code.model.LocationModel.provinces;

/**
 * show location of id by assign id into constructor and save that location into IDNumber
 *
 * @author kamontat
 * @version 1.3
 * @since 20/8/59 - 23:15
 */
public class Location {
	private String province;
	private String district;
	private Type type;
	
	public Location() {
		province = "Unknown";
		district = "Unknown";
		type = Type.NO_EVERYTHING;
	}
	
	/**
	 * Assign id into it, and it will give 3 parameter in this class <br>
	 * First is province -> if the id has province, otherwise province will be <b>Unknown</b> <br>
	 * Second id district ->  if the id has district, otherwise district will be <b>Unknown</b> <br>
	 * And can easy to know id have location correctly or not by using getType()
	 *
	 * @param id
	 * 		some id
	 * @see Type
	 */
	public Location(IDNumber id) {
		String addressID = "(" + id.getIDAddress() + ")";
		String provinceID = "(" + id.getIDProvince() + ")";
		String districtID = "(" + id.getIDDistrict() + ")";
		
		province = provinces.get(id.getIDProvince());
		district = districts.get(id.getIDAddress());
		
		if (province == null) {
			province = "Unknown " + addressID;
			type = Type.NO_EVERYTHING;
		} else if (district == null) {
			district = "Unknown " + districtID;
			type = Type.NO_DISTRICT;
		} else {
			province += provinceID;
			district += districtID;
			type = Type.OK;
		}
	}
	
	public int getSizeProvince() {
		String val = provinces.get("size");
		if (val != null) {
			return Integer.parseInt(val);
		}
		return 0;
	}
	
	public String getProvince() {
		return province;
	}
	
	public int getSizeDistrict() {
		String val = districts.get("size");
		if (val != null) {
			return Integer.parseInt(val);
		}
		return 0;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public Type getType() {
		return type;
	}
}
