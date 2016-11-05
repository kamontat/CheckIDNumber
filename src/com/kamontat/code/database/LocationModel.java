package com.kamontat.code.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamontat.code.object.Location;
import com.kamontat.code.watch.StopWatch;
import com.kamontat.gui.LoadingPopup;

import javax.swing.*;
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
	public static void read() {
		LoadingPopup loading = new LoadingPopup();
		StopWatch watch = new StopWatch();
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				super.run();
				ObjectMapper mapper = new ObjectMapper();
				try {
					watch.start();
					loading.setProgressLabel("Start loading Province and District");
					loading.setProgressValue(0);
					
					InputStream streamProvince = Location.class.getResourceAsStream("/resources/json_location/provinces.json");
					provinces = mapper.readValue(streamProvince, provinces.getClass());
					
					loading.setProgressValue(50);
					
					InputStream streamDistrict = Location.class.getResourceAsStream("/resources/json_location/districts.json");
					districts = mapper.readValue(streamDistrict, districts.getClass());
					
					watch.stop();
					loading.setProgressValue(100);
					loading.setDoneLabel("Finish loaded Province and District" + watch);
					
					readable = true;
				} catch (Exception e) {
					e.printStackTrace();
					readable = false;
					JOptionPane.showMessageDialog(null, "Can't read json_location file \nplease contact to developer.\nif you want information feature.", "Error Loading file", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		loading.startLoading(thread);
	}
}
