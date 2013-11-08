package com.madisp.stupid;

import com.madisp.stupid.expr.StatementListExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Block {
	private final StatementListExpression body;
	private final String[] varNames;

	public Block(String[] varNames, StatementListExpression body) {
		this.varNames = Arrays.copyOf(varNames, varNames.length);
		this.body = body;
	}

	public Object yield(ExecContext ctx, Object... args) {
		if (varNames.length != args.length) {
			throw new IllegalArgumentException();
		}
		Map<String, Object> argMap = new HashMap<String, Object>();
		for (int i = 0; i < varNames.length; i++) {
			argMap.put(varNames[i], args[i]);
		}
		ctx.pushScope(new VarScope(Collections.unmodifiableMap(argMap)));
		Object ret = ctx.deref(body);
		ctx.popScope();
		return ret;
	};
}
