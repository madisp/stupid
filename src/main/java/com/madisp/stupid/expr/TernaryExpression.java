package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class TernaryExpression implements Expression {
	private final Expression expression, trueValue, falseValue;

	public TernaryExpression(Expression expression, Expression trueValue, Expression falseValue) {
		this.expression = expression;
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.toBool(expression) ? trueValue.value(ctx) : falseValue.value(ctx);
	}

	@Override
	public Expression[] children() {
		return new Expression[] { expression, trueValue, falseValue };
	}
}
