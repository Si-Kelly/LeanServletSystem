package com.lss.db.jooq;

import java.sql.Connection;

import javax.servlet.ServletRequest;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.lss.db.DB;
import com.lss.db.DBConnectionException;

public final class JOOQ {
	private JOOQ() {
		// prevent accidental instantiation
	}

	public static DSLContext dsl(ServletRequest request) throws DBConnectionException {
		return dsl(DB.getConnection(request));
	}

	public static DSLContext dsl(Connection connection) {
		return DSL.using(connection, SQLDialect.MYSQL);
	}
}
