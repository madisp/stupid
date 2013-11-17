package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class CallExpression implements Expression {
	private final Expression base;
	private final String identifier;
	private final Expression[] args;

	public CallExpression(Expression base, String identifier, Expression... args) {
		this.base = base;
		this.identifier = identifier;
		this.args = args;
	}


	@Override
	public Object value(ExecContext ctx) {
		Object[] argValues = new Object[args.length];
		for (int i = 0; i < argValues.length; i++) {
			argValues[i] = ctx.deref(args[i]);
		}
		Object root = base == null ? null : base.value(ctx);
		if (base != null && root == null) {
			return null; // null.something(...) always yields null
		}
		return ctx.callMethod(root, identifier, argValues);
	}

	@Override
	public Expression[] children() {
		Expression[] ret = new Expression[args.length + 1];
		ret[0] = base;
		System.arraycopy(args, 0, ret, 1, args.length);
		return ret;
	}
}
