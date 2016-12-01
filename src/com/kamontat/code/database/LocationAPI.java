package com.kamontat.code.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamontat.code.object.Location;
import com.kamontat.gui.LoadingPopup;

import javax.swing.*;
import java.io.InputStream;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/30/2016 AD - 1:47 AM
 */
public class LocationAPI extends Observable {
	public static boolean readable = false;
	public static TreeMap<String, String> provinces = new TreeMap<>();
	public static TreeMap<String, String> districts = new TreeMap<>();
	
	/**
	 * run this first to assign province and amphur <br>
	 * <b>May Slow</b>, (In my test it's run 300 ms)
	 */
	public void read() {
		addObserver(LoadingPopup.getInstance());
		LoadingPopup.getInstance().showPage(2);
		ObjectMapper mapper = new ObjectMapper();
		try {
			setChanged();
			notifyObservers("Start loading Province and District");
			
			InputStream streamProvince = Location.class.getResourceAsStream("/resources/json_location/provinces.json");
			provinces = mapper.readValue(streamProvince, provinces.getClass());
			
			notifyObservers(1);
			
			InputStream streamDistrict = Location.class.getResourceAsStream("/resources/json_location/districts.json");
			districts = mapper.readValue(streamDistrict, districts.getClass());
			
			notifyObservers(2);
			readable = true;
			LoadingPopup.getInstance().hidePage(false);
		} catch (Exception e) {
			e.printStackTrace();
			readable = false;
			JOptionPane.showMessageDialog(null, "Can't read json_location file \nplease contact to developer.\nif you want information feature.", "Error Loading file", JOptionPane.ERROR_MESSAGE);
		}
		notifyObservers("Have some Error");
		LoadingPopup.getInstance().hidePage(true);
	}
}
