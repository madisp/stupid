package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class NotExpression implements Expression {
	private final Expression expr;

	public NotExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public Object value(ExecContext ctx) {
		return !ctx.toBool(expr);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { expr };
	}
}
