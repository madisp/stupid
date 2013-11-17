package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class AndExpression implements Expression {
	private final Expression left, right;

	public AndExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.toBool(left) && ctx.toBool(right);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
