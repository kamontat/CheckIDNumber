package com.kamontat.code.file;

import com.kamontat.code.object.IDNumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	public static boolean read() {
		ArrayList<String[]> allData = new ArrayList<>();

		Consumer<String> consumer = s -> {
			String[] array = s.split(" ");
			allData.add(array);
		};

		try {
			File file = new File(dir.getPath() + "/src/resources/province/allProvinces.txt");
			if (!file.isFile()) {
				file = new File(dir.getPath() + "/checkIDNumber/resources/province/allProvinces.txt");
			}
			BufferedReader br = new BufferedReader(new FileReader(file));

			br.lines().forEach(consumer); // add data into arrays
			br.close();

			Provinces.allData = allData.toArray(new String[allData.size()][]);
			return true;
		} catch (IOException e) {
			Provinces.allData = null;
		}
		return false;
	}

	/**
	 * get Province and District
	 *
	 * @param id
	 * 		idNumber
	 * @return At element 0 is The Province <br>
	 * At element 1 is The District
	 */
	public static String[] getProvinceAndDistrict(IDNumber id) {
		String tempProvince = "", province = "";
		String[] info = new String[2];
		info[0] = "";
		info[1] = "";

		for (String[] data : allData) {
			for (int j = 0; j < allData[0].length; j++) {
				// update new province
				if (data[0].length() != 4) {
					// clear new province
					tempProvince = "";
					for (int i = 1; i < data.length; i++) {
						tempProvince += data[i] + " ";
					}
				}

				// District
				if (data[0].length() == 4) {
					// check province
					if (String.copyValueOf(data[0].toCharArray(), 0, 2).equals(id.getIDProvince())) {
						province = tempProvince + " (" + id.getIDProvince() + ")";
					}

					if (id.getIDAddress().equals(data[0])) {
						info[0] = tempProvince;
						info[0] += " (" + id.getIDProvince() + ")";
						for (int i = 1; i < data.length; i++) {
							info[1] += data[i] + " ";
						}
						info[1] += " (" + id.getIDDistrict() + ")";
						return info;
					}
				}
			}
		}
		return new String[]{province.equals("") ? "Unknown Province (" + id.getIDProvince() + ")": province, "Unknown District (" + id.getIDDistrict() + ")"};
	}

	public static boolean hasFile() {
		return allData != null;
	}
}
