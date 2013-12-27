package com.madisp.stupid.expr;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.Expression;

public class NotExpression implements Expression {
	private final Expression expr;

	public NotExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public Object value(ExecContext ctx) {
		return !ctx.getConverter().toBool(ctx.dereference(expr));
	}

	@Override
	public Expression[] children() {
		return new Expression[] { expr };
	}
}
