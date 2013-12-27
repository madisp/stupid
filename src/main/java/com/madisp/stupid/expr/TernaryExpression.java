package com.madisp.stupid.expr;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.Expression;

public class TernaryExpression implements Expression {
	private final Expression expression, trueValue, falseValue;

	public TernaryExpression(Expression expression, Expression trueValue, Expression falseValue) {
		this.expression = expression;
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.getConverter().toBool(ctx.dereference(expression))
				? ctx.dereference(trueValue) : ctx.dereference(falseValue);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { expression, trueValue, falseValue };
	}
}
