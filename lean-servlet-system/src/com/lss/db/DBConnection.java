package com.lss.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * A wrapper for DataSource and Connection that caches the DataSource on the
 * ServletContext. Open connections are stored on the request.
 * 
 * @author Si
 * 
 */
public class DBConnection {
	private static final Log log = LogFactory.getLog(DBConnection.class);
	private static final String KEY = "com.lss.openconnection";
	private static final String DATA_SOURCE_KEY = "com.lss.datasource";

	private Connection connection;

	private static Connection getOpenConnection(ServletRequest req) {
		return (Connection) req.getAttribute(KEY);
	}

	private static Connection getConnection(ServletRequest req) throws DBConnectionException {
		Connection cached = getOpenConnection(req);
		if (cached == null) {
			try {
				DataSource datasource = ((DataSource) req.getServletContext().getAttribute(DATA_SOURCE_KEY));

				if (datasource == null)
					throw new DBConnectionException(
							"No DataSource exists. You must call DBConnection.setDataSource() before calling getConnection()");

				cached = datasource.getConnection();
			} catch (SQLException e) {
				throw new DBConnectionException("Unable to connect to Database", e);
			}
			req.setAttribute(KEY, cached);
		}
		return cached;
	}

	/**
	 * Returns an instance of DBConnection
	 * 
	 * @param req
	 * @throws DBConnectionException
	 */
	public DBConnection(ServletRequest req) throws DBConnectionException {
		this.connection = getConnection(req);
	}

	public DSLContext dsl() {
		log.debug("creating DSL context");
		return DSL.using(connection, SQLDialect.MYSQL);
	}

	public void done(ServletRequest request) {
		if (connection != null) {
			try {
				log.debug("[manual close connection]");
				connection.close();
				request.removeAttribute(KEY);
			} catch (SQLException e) {
				log.error("Error closing connection ", e);
			}
		}
	}

	public static void closeOpenConnection(ServletRequest request) {
		Connection connection = getOpenConnection(request);
		if (connection != null) {
			try {
				connection.close();
				request.removeAttribute(KEY);
				log.debug("[closed open connection]");
			} catch (SQLException e) {
				log.error("Error closing connection ", e);
			}
		}
	}

	public static void setDataSource(ServletContext servletContext, DataSource ds) throws DBConnectionException {
		servletContext.setAttribute(DATA_SOURCE_KEY, ds);
	}

}
