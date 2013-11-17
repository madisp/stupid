package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class OrExpression implements Expression {
	private final Expression left, right;

	public OrExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object leftVal = ctx.deref(left);
		Object rightVal = ctx.deref(right);
		if (ctx.toBool(leftVal)) {
			return leftVal;
		} else {
			return rightVal;
		}
	}

	@Override
	public Expression[] children() {
		return new Expression[] { left, right };
	}
}
