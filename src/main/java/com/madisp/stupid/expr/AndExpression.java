package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

/**
 * The boolean and operator.
 * Usage in stupid: {@code expr and expr}
 */
public class AndExpression implements Expression<Boolean> {
	private final Expression left, right;

	public AndExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Boolean value(ExecContext ctx) {
		return ctx.getConverter().toBool(left.value(ctx)) && ctx.getConverter().toBool(right.value(ctx));
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
