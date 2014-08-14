package com.lss.bind;

import javax.servlet.http.HttpServletRequest;

import com.lss.MessageUtil;

public abstract class BaseNumberParam<T> extends BaseParamHelper<T> implements ParamHelper<T> {

	public BaseNumberParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	@Override
	public T parse(String val) throws ValidationException {
		T value = null;
		if (val != null && val.length() > 0) {
			try {
				return parseNumber(val);
			} catch (NumberFormatException nfe) {
				throw new ValidationException(new String[] { getParameterName(),
						MessageUtil.niceFieldName(getParameterName()) + " doesn't seem to be a valid number" });
			}
		} else {
			value = null;
		}
		return value;
	}

	public abstract T parseNumber(String val);

	@Override
	public String format(T value) {
		return value.toString();
	}

}
