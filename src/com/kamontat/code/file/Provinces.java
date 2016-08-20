package com.kamontat.code.file;

import com.kamontat.code.object.IDNumber;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

import static com.kamontat.code.window.Display.dir;

/**
 * @author kamontat
 * @since 20/8/59 - 23:15
 */
public class Provinces {
	private static String[][] allData;

	/**
	 * run this first to assign allData array
	 */
	public static void read() {
		ArrayList<String[]> allData = new ArrayList<>();

		Consumer<String> consumer = s -> {
			String[] array = s.split(" ");
			allData.add(array);
		};

		try {
			File file = new File(dir.getPath() + "/src/resources/province/allProvinces.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			br.lines().forEach(consumer); // add data into arrays
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Provinces.allData = allData.toArray(new String[allData.size()][]);
	}

	/**
	 * get Province and District
	 * @param id
	 * 		idNumber
	 * @return At element 0 is The Province <br>
	 * At element 1 is The District
	 */
	public static String[] getProvince(IDNumber id) {
		String province = "";
		String[] info = new String[2];
		info[0] = "";
		info[1] = "";

		for (String[] data : allData) {
			for (int j = 0; j < allData[0].length; j++) {
				// Province
				if (data[0].length() != 4) {
					// clear new province
					province = "";
					for (int i = 1; i < data.length; i++) {
						province += data[i] + " ";
					}
				}

				// District
				if (data[0].length() == 4) {
					if (id.getIDAddress().equals(data[0])) {
						info[0] = province;
						for (int i = 1; i < data.length; i++) {
							info[1] += data[i] + " ";
						}
						return info;
					}
				}
			}
		}
		return new String[]{"Unknown Province", "Unknown District"};
	}
}
