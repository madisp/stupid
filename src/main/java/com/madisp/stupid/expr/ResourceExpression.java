package com.madisp.stupid.expr;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.Expression;

public class ResourceExpression implements Expression {
	private final String pckg, type, name;

	public ResourceExpression(String pckg, String type, String name) {
		this.pckg = pckg;
		this.type = type;
		this.name = name;
	}

	@Override
	public Object value(ExecContext ctx) {
		try {
			return ctx.getResource(pckg, type, name);
		} catch (NoSuchFieldException _) {
			return null;
		}
	}

	@Override
	public Expression[] children() {
		return new Expression[0];
	}
}
