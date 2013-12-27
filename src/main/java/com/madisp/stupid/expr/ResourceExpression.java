package com.madisp.stupid.expr;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Expression;

/**
 * The resource expression. This is currently unsupported but can be used on
 * android. The value of this is typically an android resource id which is an
 * int.
 *
 * Stupid usage: {@code @pckg:type/name}
 */
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
