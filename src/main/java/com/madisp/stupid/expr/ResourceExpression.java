package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;
import com.madisp.stupid.Value;

public class ResourceExpression implements Expression {
	private final String pckg, type, name;

	public ResourceExpression(String pckg, String type, String name) {
		this.pckg = pckg;
		this.type = type;
		this.name = name;
	}

	@Override
	public Object value(ExecContext ctx) {
		return ctx.getResource(pckg, type, name);
	}

	@Override
	public Expression[] children() {
		return new Expression[0];
	}
}
