package org.leanservlet.bind;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.leanservlet.MessageUtil;


public class DateParam extends BaseParamHelper<DateTime> implements ParamHelper<DateTime> {
	private final String pattern;
	private final String paramName;
	private final HttpServletRequest request;

	public DateParam(String paramName, HttpServletRequest request, String pattern) {
		super(paramName, request);
		this.pattern = pattern;
		this.request = request;
		this.paramName = paramName;
	}

	public DateParam withPattern(String pattern) {
		return new DateParam(paramName, request, pattern);
	}

	@Override
	public String format(DateTime date) {
		return DateTimeFormat.forPattern(pattern).print(date);
	}

	public DateParam inThePast(String message) throws ValidationException {
		if (value() != null && !value().isBeforeNow()) {
			throw new ValidationException(new String[] { paramName, message });
		}
		return this;
	}

	public DateParam inTheFuture(String message) throws ValidationException {
		if (value() != null && !value().isAfterNow()) {
			throw new ValidationException(new String[] { paramName, message });
		}
		return this;
	}

	@Override
	public DateTime parse(String val) throws ValidationException {
		DateTime date;
		if (val != null && val.length() > 0) {
			try {
				date = DateTime.parse(val, DateTimeFormat.forPattern(pattern));
			} catch (IllegalArgumentException e) {
				throw new ValidationException(new String[] { getParameterName(),
						MessageUtil.niceFieldName(getParameterName()) + " doesn't seem to be a valid date" });
			}
		} else {
			date = null;
		}
		return date;
	}

}
