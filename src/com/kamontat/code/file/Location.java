package com.kamontat.code.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamontat.code.constant.Type;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.LoadingPage;

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
	private static Type type;


	/**
	 * run this first to assign province and amphur <br>
	 * <b>May Slow</b>, (In my test it's run 300 ms)
	 */
	public static boolean read() {
		// label in launcher
		LoadingPage.statusMessage = "Start loading Province and District";

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

	/**
	 * get Province and District by id
	 *
	 * @param id
	 * 		idNumber
	 * @return first is The Province, second is District <br>
	 */
	public static String[] getProvinceAndDistrict(IDNumber id) {
		String provinceID = id.getIDProvince();
		String districtID = id.getIDAddress();

		String provinceText = provinces.get(provinceID);
		if (provinceText == null) {
			type = Type.NO_EVERYTHING;
			return new String[]{"Unknown Province (" + provinceID + ")", "Unknown District (" + id.getIDDistrict() + ")"};
		}

		String districtText = districts.get(districtID);
		if (districtText == null) {
			type = Type.NO_DISTRICT;
			return new String[]{provinceText + "(" + provinceID + ")", "Unknown District (" + id.getIDDistrict() + ")"};
		} else {
			type = Type.OK;
			return new String[]{provinceText + "(" + provinceID + ")", districtText + "(" + id.getIDDistrict() + ")"};
		}
	}

	public static Type getType() {
		return type;
	}

	public static int getSizeProvince() {
		String val = provinces.get("size");
		if (val != null) {
			return Integer.parseInt(val);
		}
		return 0;
	}

	public static int getSizeDistrict() {
		String val = districts.get("size");
		if (val != null) {
			return Integer.parseInt(val);
		}
		return 0;
	}
}
