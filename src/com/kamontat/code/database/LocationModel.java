package com.kamontat.code.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamontat.code.object.Location;
import com.kamontat.gui.popup.LoadingPopup;

import javax.swing.*;
import java.io.InputStream;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/30/2016 AD - 1:47 AM
 */
public class LocationModel extends Observable {
	public static boolean readable = false;
	public static TreeMap<String, String> provinces = new TreeMap<>();
	public static TreeMap<String, String> districts = new TreeMap<>();
	
	private LoadingPopup popup = new LoadingPopup();
	private static LocationModel model;
	
	public static LocationModel getInstance() {
		if (model == null) model = new LocationModel();
		return model;
	}
	
	private LocationModel() {
		addObserver(popup);
	}
	
	/**
	 * run this first to assign province and amphur <br>
	 * <b>May Slow</b>, (In my test it's run 300 ms)
	 */
	public void read() {
		popup.showPage(2);
		ObjectMapper mapper = new ObjectMapper();
		try {
			setChanged();
			notifyObservers("Start loading Province and District");
			
			InputStream streamProvince = Location.class.getResourceAsStream("/resources/json_location/provinces.json");
			provinces = mapper.readValue(streamProvince, provinces.getClass());
			
			setChanged();
			notifyObservers(1);
			
			InputStream streamDistrict = Location.class.getResourceAsStream("/resources/json_location/districts.json");
			districts = mapper.readValue(streamDistrict, districts.getClass());
			
			setChanged();
			notifyObservers(2);
			readable = true;
			popup.hidePage(false);
		} catch (Exception e) {
			e.printStackTrace();
			readable = false;
			JOptionPane.showMessageDialog(null, "Can't read json_location file \nplease contact to developer.\nif you want information feature.", "Error Loading file", JOptionPane.ERROR_MESSAGE);
		}
		
		setChanged();
		notifyObservers("Have some Error");
		popup.hidePage(true);
	}
}
