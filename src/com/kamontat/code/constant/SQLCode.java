package com.kamontat.code.constant;

/**
 * @author kamontat
 * @version 1.0
 * @since 11/29/2016 AD - 10:42 PM
*/
public enum SQLCode {
	SQLITE_OK(0, "Successful result"),
	SQLITE_ERROR(1, "SQL error or missing database"),
	SQLITE_INTERNAL(2, "Internal logic error in SQLite"),
	SQLITE_PERM(3, "Access permission denied"),
	SQLITE_ABORT(4, "Callback routine requested an abort"),
	SQLITE_BUSY(5, "The database file is locked"),
	SQLITE_LOCKED(6, "A table in the database is locked"),
	SQLITE_NO_MEM(7, "A malloc() failed"),
	SQLITE_READONLY(8, "Attempt to write a readonly database"),
	SQLITE_INTERRUPT(9, "Operation terminated by sqlite3_interrupt()"),
	SQLITE_IO_ERROR(10, "Some kind of disk I/O error occurred"),
	SQLITE_CORRUPT(11, "The database disk image is malformed"),
	SQLITE_NOT_FOUND(12, "Unknown opcode in sqlite3_file_control()"),
	SQLITE_FULL(13, "Insertion failed because database is full"),
	SQLITE_CANT_OPEN(14, "Unable to open the database file"),
	SQLITE_PROTOCOL(15, "DatabaseModel lock protocol error"),
	SQLITE_EMPTY(16, "DatabaseModel is empty"),
	SQLITE_SCHEMA(17, "The database schema changed"),
	SQLITE_TOO_BIG(18, "String or BLOB exceeds size limit"),
	SQLITE_CONSTRAINT(19, "Abort due to constraint violation"),
	SQLITE_MISMATCH(20, "Data type mismatch"),
	SQLITE_MISUSE(21, "Library used incorrectly"),
	SQLITE_UNSUPPORTED_OS(22, "Uses OS features not supported on host"),
	SQLITE_AUTH(23, "Authorization denied"),
	SQLITE_FORMAT(24, "Auxiliary database format error"),
	SQLITE_RANGE(25, "2nd parameter to sqlite3_bind out of range"),
	SQLITE_NOT_A_DB(26, "File opened that is not a database file"),
	SQLITE_NOTICE(27, "Notifications from sqlite3_log()"),
	SQLITE_WARNING(28, "Warnings from sqlite3_log()"),
	SQLITE_ROW(100, "sqlite3_step() has another row ready"),
	SQLITE_DONE(101, "sqlite3_step() has finished executing");
	
	public int code;
	public String message;
	
	SQLCode(int num_code,  String message) {
		this.code = num_code;
		this.message = message;
	}
	
	public static String which(int i) {
		for (SQLCode code : values()) {
			if (code.code == i) return code.message;
		}
		return "";
	}
}
