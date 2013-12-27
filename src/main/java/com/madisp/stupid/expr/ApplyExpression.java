package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

/**
 * An apply expression, usually evals to yielding a block.
 * Usage in stupid: {@code expr.(arg1, arg2, ..., argN)}
 */
public class ApplyExpression implements Expression {
	private final Expression value;
	private final Expression[] args;

	public ApplyExpression(Expression value, Expression... args) {
		this.value = value;
		this.args = args;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object base = ctx.dereference(value);
		Object[] argValues = new Object[args.length];
		for (int i = 0; i < argValues.length; i++) {
			argValues[i] = ctx.dereference(args[i]);
		}
		try {
			return ctx.apply(base, argValues);
		} catch (NoSuchMethodException nsme) {
			// TODO log it. Think of error handling in general.
			return null;
		}
	}

	@Override
	public Expression[] children() {
		Expression[] ret = new Expression[args.length + 1];
		ret[0] = value;
		System.arraycopy(args, 0, ret, 1, args.length);
		return ret;
	}
}
