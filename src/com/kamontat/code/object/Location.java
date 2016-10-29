package com.kamontat.code.object;

import com.kamontat.code.constant.Type;

import static com.kamontat.code.database.LocationModel.districts;
import static com.kamontat.code.database.LocationModel.provinces;

/**
 * @author kamontat
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
	
	public Location(IDNumber id) {
		String addressID = "(" + id.getIDAddress() + ")";
		String provinceID = "(" + id.getIDProvince() + ")";
		String districtID = "(" + id.getIDDistrict() + ")";
		
		province = provinces.get(id.getIDProvince());
		district = districts.get(id.getIDDistrict());
		
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
