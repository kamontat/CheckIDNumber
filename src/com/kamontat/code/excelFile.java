package com.kamontat.code;

import com.kamontat.main.Main;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

import static com.kamontat.main.Main.dir;

/**
 * @author kamontat
 * @since 17/8/59 - 21:49
 */
public class excelFile {
	private static String name = "output";
	private static String path = dir.getPath() + "/folderList/";
	private static File excelFile = new File(path + name + ".xls");

	public static void createExcelFile() {
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("ID Data");
		int rowNum = 0;
		System.out.println(Main.idList.size());

		for (String id : Main.idList) {
			sheet.createRow(rowNum++).createCell(0).setCellValue(id);
		}

		try {

			int i = 0;
			while (!excelFile.createNewFile()) {
				name = name + "(" + (i++) + ")";
				excelFile = new File(path + name + ".xls");
			}

			FileOutputStream out = new FileOutputStream(excelFile);
			workbook.write(out);
			out.close();

			System.out.println("file written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
