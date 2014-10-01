package org.leanservlet.bind;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringParam extends BaseParamHelper<String> implements ParamHelper<String> {

	public StringParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	@Override
	public String format(String s) {
		return StringEscapeUtils.escapeHtml4(s);
	}

	public StringParam restrictLength(int len, String message) throws ValidationException {
		if (value() != null && value().length() > len)
			throw new ValidationException(new String[] { getParameterName(), message });
		return this;
	}

	@Override
	public String parse(String s) throws ValidationException {
		if (s == null || s.length() == 0)
			return null;
		return s;
	}
}
