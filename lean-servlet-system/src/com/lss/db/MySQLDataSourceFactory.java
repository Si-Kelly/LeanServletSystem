package com.lss.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

public class MySQLDataSourceFactory implements DataSourceFactory {

	@Override
	public DataSource create(final DBAccount account) throws DBConnectionException {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new DBConnectionException(account.getURL(), e);
		}
		return new DataSource() {

			@Override
			public PrintWriter getLogWriter() throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

			@Override
			public int getLoginTimeout() throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setLogWriter(PrintWriter arg0) throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setLoginTimeout(int arg0) throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

			@Override
			public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

			@Override
			public Connection getConnection() throws java.sql.SQLException {
				return DriverManager.getConnection(account.getURL(), account.getProperties());
			}

			@Override
			public Connection getConnection(String username, String password) throws java.sql.SQLException {
				throw new UnsupportedOperationException();
			}

		};
	}

}
