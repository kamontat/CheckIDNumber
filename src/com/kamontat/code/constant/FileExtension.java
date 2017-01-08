package com.kamontat.code.constant;

import java.io.File;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/8/2017 AD - 4:47 PM
 */
public enum FileExtension {
	TEXT(".txt", "text file"), EXCEL(".xls", "old excel format from 97-2003"), EXCEL_NEW(".xlsx", "start using office 2007");
	
	public String extension;
	public String extensionWithoutDot;
	public String description;
	
	private FileExtension(String extension, String description) {
		this.extension = extension;
		this.extensionWithoutDot = extension.substring(1);
		this.description = description;
	}
	
	private static FileExtension which(String extension) {
		for (FileExtension e : FileExtension.values()) {
			if (e.extension.equals(extension) || e.extensionWithoutDot.equals(extension)) return e;
		}
		return null;
	}
	
	/*
	 * Get the extension of a file. without "."
     */
	public static FileExtension getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return which(ext);
	}
}
