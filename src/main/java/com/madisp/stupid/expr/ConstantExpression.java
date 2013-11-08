package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class ConstantExpression implements Value {
	private final Object constant;

	public ConstantExpression(Object constant) {
		this.constant = constant;
	}

	@Override
	public Object value(ExecContext ctx) {
		return constant;
	}
}
