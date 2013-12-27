package com.madisp.stupid.expr;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.Expression;

public class AndExpression implements Expression {
	private final Expression left, right;

	public AndExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.getConverter().toBool(left.value(ctx)) && ctx.getConverter().toBool(right.value(ctx));
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
