package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class NegateExpression implements Expression {
	private final Expression expr;

	public NegateExpression(Expression expr) {
		this.expr = expr;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object value = ctx.deref(expr);
		if (value instanceof Integer) {
			return 0-((Integer)value);
		} else if (value instanceof Double) {
			return 0-((Double)value);
		}
		return 0;
	}

	@Override
	public Expression[] children() {
		return new Expression[] { expr };
	}
}
