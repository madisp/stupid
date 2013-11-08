package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class OrExpression implements Value {
	private final Value left, right;

	public OrExpression(Value left, Value right) {
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
}
