package com.kamontat.code.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamontat.code.constant.Type;

import java.io.InputStream;
import java.util.*;

/**
 * @author kamontat
 * @since 20/8/59 - 23:15
 */
public class Location {
	public static boolean readable = false;
	private static TreeMap<String, String> provinces = new TreeMap<>();
	private static TreeMap<String, String> districts = new TreeMap<>();

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

	/**
	 * run this first to assign province and amphur <br>
	 * <b>May Slow</b>, (In my test it's run 300 ms)
	 */
	public static boolean read() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			InputStream streamProvince = Location.class.getResourceAsStream("/resources/json_location/provinces.json");

			provinces = mapper.readValue(streamProvince, provinces.getClass());

			InputStream streamDistrict = Location.class.getResourceAsStream("/resources/json_location/districts.json");
			districts = mapper.readValue(streamDistrict, districts.getClass());
			readable = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			readable = false;
		}
		return false;
	}
}
