package com.lss.db;

import com.lss.BaseCheckedException;

public class DBConnectionException extends BaseCheckedException {
	/**
     * 
     */
	private static final long serialVersionUID = 1574458859018354977L;

	public DBConnectionException(String connectionName, Exception e) {
		super("Unable to connect to " + connectionName, e);
	}

	public DBConnectionException(String string) {
		super(string);
	}
}
