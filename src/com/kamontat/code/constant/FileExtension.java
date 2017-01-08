package com.kamontat.code.constant;

import java.io.File;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/8/2017 AD - 4:47 PM
 */
public enum FileExtension {
	TEXT("txt", "Text File (.txt)"), EXCEL("xls", "Excel 97-2003 (.xls)"), EXCEL_NEW("xlsx", "Excel 2007 (.xlsx)"), OTHER("", "Other extension"), NONE(null, "Don't have extension");
	
	public String extension;
	public String extensionLowerCase;
	public String extensionDot;
	public String description;
	
	private FileExtension(String extension, String description) {
		this.extension = extension;
		if (extension != null) {
			this.extensionLowerCase = extension.toLowerCase(Locale.ENGLISH);
			this.extensionDot = "." + extension;
		}
		this.description = description;
	}
	
	private static FileExtension which(String extension) {
		extension = extension.toLowerCase(Locale.ENGLISH);
		for (FileExtension e : FileExtension.values()) {
			if (e != OTHER && e != NONE)
				if (e.extensionLowerCase.equals(extension) || e.extensionDot.equals(extension)) return e;
		}
		return OTHER;
	}
	
	public static boolean isHaveExt(File f) {
		return f != null && f.getName().lastIndexOf('.') != -1;
	}
	
	public static boolean isValid(File f) {
		return getExtension(f) != FileExtension.NONE && getExtension(f) != FileExtension.OTHER;
	}
	
	/*
	 * Get the extension of a file. without "."
     */
	public static FileExtension getExtension(File f) {
		if (!isHaveExt(f)) return NONE;
		
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return which(ext);
	}
	
	@Override
	public String toString() {
		return "FileExtension{" + "extension='" + extension + '\'' + ", extensionLowerCase='" + extensionLowerCase + '\'' + ", extensionDot='" + extensionDot + '\'' + ", description='" + description + '\'' + '}';
	}
}
