package org.leanservlet.db;

import java.util.Properties;

public interface DBAccount {

	String getUserName();

	String getURL();

	String getPassword();

	Properties getProperties();

}
