package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class ApplyExpression implements Value {
	private final Value value;
	private final Value[] args;

	public ApplyExpression(Value value, Value... args) {
		this.value = value;
		this.args = args;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object base = ctx.deref(value);
		Object[] argValues = new Object[args.length];
		for (int i = 0; i < argValues.length; i++) {
			argValues[i] = ctx.deref(args[i]);
		}
		return ctx.apply(base, argValues);
	}
}
