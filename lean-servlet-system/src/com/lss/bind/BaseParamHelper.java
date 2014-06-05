package com.lss.bind;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class for implementations of ParamHelper Interface
 * 
 * @author Si
 * 
 * @param <T>
 */
public abstract class BaseParamHelper<T> implements ParamHelper<T> {
	private static final Log LOG = LogFactory.getLog(BaseParamHelper.class);

	private final String parameterName;
	private final HttpServletRequest request;
	private T value;
	private boolean fromURL = false;

	public BaseParamHelper(String fieldName, HttpServletRequest request) {

		this.request = request;
		if (fieldName.startsWith("/") && fieldName.endsWith("/")) {
			this.parameterName = fieldName.substring(1, fieldName.length() - 1);
			fromURL = true;
		} else {
			this.parameterName = fieldName;
		}
	}

	public String getParameterName() {
		return parameterName;
	}

	public ParamHelper<T> require() throws ValidationException {
		return require("The parameter was missing");
	}

	public ParamHelper<T> require(String message) throws ValidationException {
		if (value() == null)
			throw new ValidationException(new String[] { getParameterName(), message });
		return this;
	}

	public T value() throws ValidationException {
		if (value == null) {
			String s = getParameter();
			if (s != null) {
				if (fromURL) {
					try {
						value = parse(s);
					} catch (ValidationException ex) {
						// ignore valuation exceptions when parsing from
						// request URI
						value = null;
					}
				} else {
					value = parse(s);
				}
			}
		}
		return value;
	}

	private String getParameter() {
		String strValue = null;
		String url = request.getRequestURL().toString();
		if (fromURL) {
			String[] parts = url.split("/");
			String tempValue = null;
			for (int i = parts.length - 1; i >= 0; i--) {
				if (parts[i].equals(parameterName) && tempValue != null) {
					try {
						strValue = URLDecoder.decode(tempValue, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}
					break;
				}
				tempValue = parts[i];
			}
		} else {
			strValue = request.getParameter(parameterName);
		}
		return strValue;
	}

	public List<T> values() throws ValidationException {
		List<T> values = new ArrayList<T>();
		String[] strValues = request.getParameterValues(parameterName);
		if (strValues != null) {
			for (String str : strValues) {
				values.add(parse(str));
			}
		}
		return values;
	}

	public void set(T value) {
		this.value = value;
		if (value != null)
			request.setAttribute(parameterName, format(value));
		else
			request.setAttribute(parameterName, null);
	}

	public void set(List<T> values) {
		List<String> fVals = new ArrayList<String>();
		for (T val : values) {
			fVals.add(format(val));
		}
		request.setAttribute(parameterName, fVals);
	}

	public void push() throws ValidationException {
		set(value());
	}

	public abstract T parse(String val) throws ValidationException;

	public abstract String format(T value);

}
