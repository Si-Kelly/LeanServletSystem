package com.lss.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBAccountProperties implements DBAccount {
	private Properties properties;

	public DBAccountProperties(String filename) {
		properties = new Properties();
		InputStream is = getClass().getResourceAsStream(filename);
		if (is == null)
			throw new RuntimeException("Could not load properties file with name " + filename);
		try {
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	@Override
	public String getUserName() {
		return properties.getProperty("user");
	}

	@Override
	public String getURL() {
		return properties.getProperty("url");
	}

	@Override
	public String getPassword() {
		return properties.getProperty("password");
	}

	@Override
	public Properties getProperties() {
		return properties;
	}
}
