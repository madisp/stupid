package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class CallExpression implements Value {
	private final Value base;
	private final String identifier;
	private final Value[] args;

	public CallExpression(Value base, String identifier, Value... args) {
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
}
