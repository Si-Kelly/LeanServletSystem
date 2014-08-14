package com.lss.bind;

import javax.servlet.http.HttpServletRequest;

public class IntegerParam extends BaseNumberParam<Integer> implements ParamHelper<Integer> {

	public IntegerParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	public IntegerParam range(int min, int max, String msg) throws ValidationException {
		if (value() < min || value() > max) {
			throw new ValidationException(new String[] { getParameterName(), msg });
		}
		return this;
	}

	@Override
	public Integer parseNumber(String val) {
		return Integer.valueOf(val);
	}
}
