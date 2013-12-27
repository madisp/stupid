package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

/**
 * The arithmetic plus operator. If any of the arguments is a double then
 * both of the arguments are converted to double and the return value is also
 * a double. Otherwise an Integer is implied.
 *
 * Usage in stupid: {@code expr + expr}
 */
public class PlusExpression implements Expression {
	private final Expression left, right;

	public PlusExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object l = ctx.dereference(left);
		Object r = ctx.dereference(right);
		if (l instanceof String || r instanceof String) {
			return ctx.getConverter().toString(l) + ctx.getConverter().toString(r);
		} else if (l instanceof Double || r instanceof Double) {
			return ctx.getConverter().toDouble(l) + ctx.getConverter().toDouble(r);
		}
		return ctx.getConverter().toInt(l) + ctx.getConverter().toInt(r);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
