package org.leanservlet.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A wrapper for DataSource and Connection that caches the DataSource on the
 * ServletContext. Open connections are stored on the request.
 * 
 * @author Si
 * 
 */
public class DB {

	private static final Log log = LogFactory.getLog(DB.class);
	private static final String KEY = "org.leanservlet.openconnection";
	private static final String DATA_SOURCE_KEY = "org.leanservlet.datasource";

	/**
	 * Returns an instance of DBConnection
	 * 
	 * @param req
	 * @
	 */
	private DB() {
		// prevent accidental instantiation
	}

	public static void setDataSource(ServletContext servletContext, DataSource ds) {
		servletContext.setAttribute(DATA_SOURCE_KEY, ds);
	}

	private static Connection getOpenConnection(ServletRequest req) {
		return (Connection) req.getAttribute(KEY);
	}

	public static Connection getConnection(ServletRequest req) {
		Connection cached = getOpenConnection(req);
		if (cached == null) {
			cached = getConnection(req.getServletContext());
			req.setAttribute(KEY, cached);
		}
		return cached;
	}

	public static Connection getConnection(ServletContext ctx) {
		Connection connection = null;
		try {
			DataSource datasource = ((DataSource) ctx.getAttribute(DATA_SOURCE_KEY));

			if (datasource == null)
				throw new DBConnectionException(
						"No DataSource exists. You must call DBConnection.setDataSource() before calling getConnection()");

			connection = datasource.getConnection();
		} catch (SQLException e) {
			throw new DBConnectionException("Unable to connect to Database", e);
		}

		return connection;
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

}
