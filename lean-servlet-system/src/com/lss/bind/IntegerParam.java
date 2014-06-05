package com.lss.bind;

import javax.servlet.http.HttpServletRequest;

import com.lss.MessageUtil;

public class IntegerParam extends BaseParamHelper<Integer> implements ParamHelper<Integer> {

	public IntegerParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	@Override
	public String format(Integer value) {
		return value.toString();
	}

	@Override
	public Integer parse(String val) throws ValidationException {
		Integer value = null;
		if (val != null && val.length() > 0) {
			try {
				value = Integer.valueOf(val);
			} catch (NumberFormatException nfe) {
				throw new ValidationException(new String[] { getParameterName(),
						MessageUtil.niceFieldName(getParameterName()) + " doesn't seem to be a valid number" });
			}
		} else {
			value = null;
		}
		return value;
	}

	public IntegerParam range(int min, int max, String msg) throws ValidationException {
		if (value() < min || value() > max) {
			throw new ValidationException(new String[] { getParameterName(), msg });
		}
		return this;
	}
}
