package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

/**
 * The arithmetic minus expression. If any of the arguments is a double then
 * both of the arguments are converted to double. Otherwise an Integer is implied.
 * Note that this class is used for both less than and larger than operations (as the
 * only difference is the order of operands).
 *
 * Usage in stupid: {@code expr < expr}
 */
public class ComparisonExpression implements Expression<Boolean> {
	private Expression left, right;

	public ComparisonExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Boolean value(ExecContext ctx) {
		Object leftValue = ctx.dereference(left);
		Object rightValue = ctx.dereference(right);
		if (leftValue instanceof Double || rightValue instanceof Double) {
			return ctx.getConverter().toDouble(leftValue) < ctx.getConverter().toDouble(rightValue);
		} else {
			return ctx.getConverter().toInt(leftValue) < ctx.getConverter().toInt(rightValue);
		}
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
