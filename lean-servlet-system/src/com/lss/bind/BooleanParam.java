package com.lss.bind;

import javax.servlet.http.HttpServletRequest;

public class BooleanParam extends BaseParamHelper<Boolean> {

	public BooleanParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	@Override
	public Boolean parse(String val) throws ValidationException {
		if (val == null || val.isEmpty())
			return null;
		Integer i = null;
		try {
			i = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			throw new ValidationException(new String[] { getParameterName(),
					"The field doesn't seem to be a valid value." });
		}
		if (i == 1)
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	@Override
	public String format(Boolean value) {
		if (value)
			return "1";
		return "";
	}

}
