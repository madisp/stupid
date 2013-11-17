package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class ApplyExpression implements Expression {
	private final Expression value;
	private final Expression[] args;

	public ApplyExpression(Expression value, Expression... args) {
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

	@Override
	public Expression[] children() {
		Expression[] ret = new Expression[args.length + 1];
		ret[0] = value;
		System.arraycopy(args, 0, ret, 1, args.length);
		return ret;
	}
}
