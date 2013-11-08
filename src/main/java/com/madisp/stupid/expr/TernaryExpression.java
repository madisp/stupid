package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class TernaryExpression implements Value {
	private final Value expression, trueValue, falseValue;

	public TernaryExpression(Value expression, Value trueValue, Value falseValue) {
		this.expression = expression;
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.toBool(expression) ? trueValue.value(ctx) : falseValue.value(ctx);
	}
}
