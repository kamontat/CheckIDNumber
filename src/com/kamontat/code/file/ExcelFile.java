package com.kamontat.code.file;

import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.popup.LoadingPopup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import static com.kamontat.code.database.DatabaseAPI.dir;
import static com.kamontat.code.database.DatabaseAPI.getLocalSize;
import static com.kamontat.code.database.DatabaseAPI.idList;

/**
 * This class use to create new excel file in <code>folderList</code> location <br>
 * It's have only one method call <b><code>createExcelFile</code></b> to create Excel by using <code>idList</code>
 *
 * @author kamontat
 * @version 1.0
 * @since 17/8/59 - 21:49
 */
public class ExcelFile extends Observable {
	private static String name = "output";
	private static String path = dir.getPath() + "/folderList/";
	
	private static ExcelFile file;
	
	public static ExcelFile getFile() {
		if (file == null) file = new ExcelFile();
		return file;
	}
	
	/**
	 * create excel file by <code>idList</code>
	 *
	 * @param extension
	 * 		extension of file with `.` Example ".xlsx", ".xls"
	 */
	public void createExcelFile(String extension) {
		LoadingPopup popup = LoadingPopup.getInstance();
		popup.showPage(getLocalSize() + 1);
		addObserver(popup);
		setChanged();
		notifyObservers("Start writing excel file");
		
		File excelFile = new File(path + name + extension);
		
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("ID Data");
		int rowNum = 0;
		for (IDNumber id : idList) {
			for (int i = 0; i < 100; i++) {
				sheet.createRow(rowNum).createCell(i).setCellValue(id.getId());
				setChanged();
				notifyObservers((rowNum * 100) + i);
			}
			rowNum++;
		}
		
		try {
			int i = 1;
			while (!excelFile.createNewFile()) {
				excelFile = new File(path + name + "(" + (i++) + ")" + extension);
			}
			
			FileOutputStream out = new FileOutputStream(excelFile);
			workbook.write(out);
			out.close();
			
			setChanged();
			notifyObservers(rowNum);
			popup.hidePage(false);
			
			JOptionPane.showMessageDialog(null, "create file in \"" + (path + name + (--i == 0 ? "": ("(" + (i) + ")")) + extension + "\"") + "\n" + "total ID is " + idList.size() + " id.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot export.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}