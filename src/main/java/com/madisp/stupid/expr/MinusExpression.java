package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class MinusExpression implements Expression {
	private final Expression left, right;

	public MinusExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object l = ctx.deref(left);
		Object r = ctx.deref(right);
		if (l instanceof Double || r instanceof Double) {
			return ctx.toDouble(l) - ctx.toDouble(r);
		}
		return ctx.toInt(l) - ctx.toInt(r);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
