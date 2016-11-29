package com.kamontat.code.file;

import com.kamontat.code.object.IDNumber;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

import static com.kamontat.code.database.Database.dir;
import static com.kamontat.code.database.Database.idList;

/**
 * This class use to create new excel file in <code>folderList</code> location <br>
 * It's have only one method call <b><code>createExcelFile</code></b> to create Excel by using <code>idList</code>
 *
 * @author kamontat
 * @version 1.0
 * @since 17/8/59 - 21:49
 */
public class ExcelFile {
	private static String name = "output";
	private static String path = dir.getPath() + "/folderList/";
	
	/**
	 * create excel file by <code>idList</code>
	 *
	 * @param extension
	 * 		extension of file with `.` Example ".xlsx", ".xls"
	 */
	public static void createExcelFile(String extension) {
		File excelFile = new File(path + name + extension);
		
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("ID Data");
		int rowNum = 0;
		for (IDNumber id : idList) {
			sheet.createRow(rowNum++).createCell(0).setCellValue(id.getId());
		}
		try {
			int i = 1;
			while (!excelFile.createNewFile()) {
				excelFile = new File(path + name + "(" + (i++) + ")" + extension);
			}
			
			FileOutputStream out = new FileOutputStream(excelFile);
			workbook.write(out);
			out.close();
			
			JOptionPane.showMessageDialog(null, "create file in \"" + (path + name + (--i == 0 ? "": ("(" + (i) + ")")) + extension + "\"") + "\n" + "total ID is " + idList.size() + " id.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot export.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}