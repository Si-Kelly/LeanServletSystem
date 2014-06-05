package com.lss;

import javax.servlet.ServletException;

@SuppressWarnings("serial")
public abstract class BaseCheckedException extends ServletException {

	public BaseCheckedException(String string, Exception e) {
		super(string, e);
	}

	public BaseCheckedException(String string) {
		super(string);
	}

}
