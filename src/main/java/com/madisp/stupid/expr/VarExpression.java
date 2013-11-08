package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

public class VarExpression implements Value {
	private final Value base;
	private final String identifier;

	public VarExpression(Value base, String identifier) {
		this.base = base;
		this.identifier = identifier;
	}


	@Override
	public Object value(ExecContext ctx) {
		Object root = base == null ? null : base.value(ctx);
		if (base != null && root == null) {
			return null; // null.something always yields null
		}
		return ctx.getFieldValue(root, identifier);
	}
}
