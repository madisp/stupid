package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class NotExpression implements Value {
	private final Value expr;

	public NotExpression(Value expr) {
		this.expr = expr;
	}

	@Override
	public Object value(ExecContext ctx) {
		return !ctx.toBool(expr);
	}
}
