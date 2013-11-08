package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class AndExpression implements Value {
	private final Value left, right;

	public AndExpression(Value left, Value right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.toBool(left) && ctx.toBool(right);
	}
}
