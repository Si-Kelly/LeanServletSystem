package org.leanservlet.bind;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.leanservlet.MessageUtil;


public class DecimalParam extends BaseParamHelper<BigDecimal> implements ParamHelper<BigDecimal> {

	private final boolean zeroForNull;
	private final HttpServletRequest request;
	private final String fieldName;

	public DecimalParam(String fieldName, HttpServletRequest request) {
		this(fieldName, request, false);
	}

	private DecimalParam(String fieldName, HttpServletRequest request, boolean zeroForNull) {
		super(fieldName, request);
		this.zeroForNull = zeroForNull;
		this.request = request;
		this.fieldName = fieldName;
	}

	@Override
	public String format(BigDecimal value) {
		return value.toString();
	}

	@Override
	public BigDecimal parse(String val) throws ValidationException {
		BigDecimal value = null;
		if (val != null && val.length() > 0) {
			try {
				value = new BigDecimal(val);
			} catch (NumberFormatException nfe) {
				throw new ValidationException(new String[] { getParameterName(),
						MessageUtil.niceFieldName(getParameterName()) + " doesn't seem to be a valid number" });
			}
		} else {
			value = null;
		}
		return value;
	}

	public DecimalParam zeroForNull() throws ValidationException {
		return new DecimalParam(fieldName, request, true);
	}

	@Override
	public BigDecimal value() throws ValidationException {
		BigDecimal val = super.value();
		if (zeroForNull && val == null)
			val = BigDecimal.ZERO;
		return val;
	}

}
