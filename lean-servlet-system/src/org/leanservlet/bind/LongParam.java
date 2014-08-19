package org.leanservlet.bind;

import javax.servlet.http.HttpServletRequest;

public class LongParam extends BaseNumberParam<Long> {

	public LongParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	@Override
	public Long parseNumber(String val) {
		return Long.valueOf(val);
	}

}
