package org.leanservlet.db;


public class DBConnectionException extends RuntimeException {
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
