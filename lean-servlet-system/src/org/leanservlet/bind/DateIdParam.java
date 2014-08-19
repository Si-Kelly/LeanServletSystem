package org.leanservlet.bind;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateIdParam extends BaseParamHelper<DateTime> {
	DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

	public DateIdParam(String fieldName, HttpServletRequest request) {
		super(fieldName, request);
	}

	@Override
	public DateTime parse(String val) throws ValidationException {
		if (val == null || val.isEmpty())
			return null;
		try {
			return format.parseDateTime(val);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Can't parse date:" + val);
		}
	}

	@Override
	public String format(DateTime value) {
		return format.print(value);
	}

}
