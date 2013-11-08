package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class AssignExpression implements Value {
	private final Value base;
	private final String identifier;
	private final Value value;

	public AssignExpression(Value base, String identifier, Value value) {
		this.base = base;
		this.identifier = identifier;
		this.value = value;
	}

	@Override
	public Object value(ExecContext ctx) {
		Object root = base == null ? null : base.value(ctx);
		if (base != null && root == null) {
			return null; // null.something always yields null
		}
		return ctx.setFieldValue(root, identifier, ctx.deref(value));
	}
}
