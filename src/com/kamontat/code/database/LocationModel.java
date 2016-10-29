package com.kamontat.code.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamontat.code.object.Location;

import java.io.InputStream;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/30/2016 AD - 1:47 AM
 */
public class LocationModel {
	public static boolean readable = false;
	public static TreeMap<String, String> provinces = new TreeMap<>();
	public static TreeMap<String, String> districts = new TreeMap<>();
	
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
