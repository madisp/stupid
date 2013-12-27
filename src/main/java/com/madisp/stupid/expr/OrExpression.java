package com.madisp.stupid.expr;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.Expression;

public class OrExpression implements Expression {
	private final Expression left, right;

	public OrExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object leftVal = ctx.dereference(left);
		Object rightVal = ctx.dereference(right);
		if (ctx.getConverter().toBool(leftVal)) {
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
