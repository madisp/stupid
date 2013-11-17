package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class VarExpression implements Expression {
	private final Expression base;
	private final String identifier;

	public VarExpression(Expression base, String identifier) {
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

	@Override
	public Expression[] children() {
		return new Expression[] { base };
	}
}
