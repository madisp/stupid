package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

/**
 * The equals expression.
 * The implementation uses the {@link Object#equals(Object)} method under the hood, so
 * comparing strings with the == operator is valid, for instance.
 * In stupid: {@code expr == expr}
 */
public class EqualsExpression implements Expression<Boolean> {
	private Expression left, right;

	public EqualsExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}

	@Override
	public Boolean value(ExecContext ctx) {
		Object leftValue = ctx.dereference(left);
		Object rightValue = ctx.dereference(right);
		return leftValue == null ? rightValue == null : leftValue.equals(rightValue);
	}
}
