package com.kamontat.code.file;

import com.kamontat.main.Main;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

import static com.kamontat.code.window.Display.dir;
import static com.kamontat.main.Main.idList;

/**
 * @author kamontat
 * @since 17/8/59 - 21:49
 */
public class ExcelFile {
	private static String name = "output";
	private static String path = dir.getPath() + "/folderList/";
	private static File excelFile = new File(path + name + ".xls");

	public static void createExcelFile() {
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("ID Data");
		int rowNum = 0;

		for (String id : Main.idList) {
			sheet.createRow(rowNum++).createCell(0).setCellValue(id);
		}

		try {

			int i = 1;
			while (!excelFile.createNewFile()) {
				excelFile = new File(path + name + "(" + (i++) + ")" + ".xls");
			}

			FileOutputStream out = new FileOutputStream(excelFile);
			workbook.write(out);
			out.close();

			JOptionPane.showMessageDialog(null, "create file in \"" + (path + name + (--i == 0 ? "": ("(" + (i) + ")")) + ".xls\"") + "\n" + "total ID is " + idList.size() + " id.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
