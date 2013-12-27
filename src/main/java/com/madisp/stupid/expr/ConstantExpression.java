package com.madisp.stupid.expr;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.Expression;

public class ConstantExpression implements Expression {
	private final Object constant;

	public ConstantExpression(Object constant) {
		this.constant = constant;
	}

	@Override
	public Object value(ExecContext ctx) {
		return constant;
	}

	@Override
	public Expression[] children() {
		return new Expression[0];
	}
}
