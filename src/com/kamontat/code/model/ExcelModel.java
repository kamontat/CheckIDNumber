package com.kamontat.code.model;

import com.kamontat.code.constant.FileExtension;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.popup.LoadingPopup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.*;

import static com.kamontat.code.model.DatabaseAPI.idList;

/**
 * This class use to create new excel file in <code>folderList</code> location <br>
 * It's have only one method call <b><code>createExcelFile</code></b> to create Excel by using <code>idList</code>
 *
 * @author kamontat
 * @version 1.0
 * @since 17/8/59 - 21:49
 */
public class ExcelModel extends Observable {
	/**
	 * get current dir
	 */
	private static File dir = Paths.get("").toAbsolutePath().toFile();
	
	private static String name = "output";
	private static String path = dir.getPath() + "/folderList/";
	
	private LoadingPopup popup;
	private Window parent;
	
	public ExcelModel(Window parent) {
		this.parent = parent;
		popup = new LoadingPopup(null);
		addObserver(popup);
	}
	
	/**
	 * create excel file by <code>idList</code>
	 *
	 * @param t
	 * 		type of file extension with `.` Example ".xlsx", ".xls"
	 */
	public void createExcelFile(FileExtension t) {
		if (t == FileExtension.TEXT) return;
		
		popup.setVisible(true);
		popup.showPage(DatabaseAPI.getDatabase(parent).getLocalSize() + 1);
		
		setChanged();
		notifyObservers("Start writing excel file");
		
		File excelFile = new File(path + name + t.extension);
		
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("ID Data");
		int row = 0, column = 0;
		for (IDNumber id : idList) {
			sheet.createRow(row).createCell(column).setCellValue(id.getId());
			sheet.autoSizeColumn(column);
			
			setChanged();
			notifyObservers(row);
			column++;
			if (column > 10000) {
				row++;
				column = 0;
			}
		}
		
		try {
			int i = 1;
			while (!excelFile.createNewFile()) {
				excelFile = new File(path + name + "(" + (i++) + ")" + t.extension);
			}
			
			FileOutputStream out = new FileOutputStream(excelFile);
			workbook.write(out);
			out.close();
			
			setChanged();
			notifyObservers(row);
			popup.hidePage(false);
			popup.setVisible(false);
			JOptionPane.showMessageDialog(null, "create file in \"" + (path + name + (--i == 0 ? "": ("(" + (i) + ")")) + t.extension + "\"") + "\n" + "total ID is " + idList.size() + " id.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Cannot export.", "Error", JOptionPane.ERROR_MESSAGE);
			popup.hidePage(true);
		}
		
		deleteObserver(popup);
	}
}