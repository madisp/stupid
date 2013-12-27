package com.madisp.stupid;

import com.madisp.stupid.context.ExecContext;
import com.madisp.stupid.context.StackContext;
import com.madisp.stupid.context.VarContext;
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
		// wrap our block arguments over the underlying context
		StackContext withArgs = new StackContext();
		withArgs.pushExecContext(ctx); // the underlying context
		withArgs.pushExecContext(new VarContext(Collections.unmodifiableMap(argMap))); // args
		return body.value(withArgs);
	};
}
