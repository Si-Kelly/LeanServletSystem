package com.lss.db;


public interface DataSourceFactory {

	public abstract javax.sql.DataSource create(DBAccount account) throws DBConnectionException;

}