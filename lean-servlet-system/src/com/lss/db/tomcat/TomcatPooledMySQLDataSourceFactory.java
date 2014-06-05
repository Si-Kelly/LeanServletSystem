package com.lss.db.tomcat;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.lss.db.DBAccount;
import com.lss.db.DBConnectionException;
import com.lss.db.DataSourceFactory;

public class TomcatPooledMySQLDataSourceFactory implements DataSourceFactory {

	@Override
	public DataSource create(DBAccount account) throws DBConnectionException {
		Object driver = null;
		try {
			driver = Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new DBConnectionException("Unable to instanciate Database Driver", e);
		}
		PoolProperties p = new PoolProperties();
		p.setUrl(account.getURL());
		p.setDriverClassName(driver.getClass().getName());
		p.setUsername(account.getUserName());
		p.setPassword(account.getPassword());
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setPoolProperties(p);
		return datasource;
	}
}
