package org.leanservlet.db;


public interface DataSourceFactory {

	public abstract javax.sql.DataSource create(DBAccount account) ;

}