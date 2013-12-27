package com.madisp.stupid;

import com.madisp.stupid.context.StackContext;
import com.madisp.stupid.context.VarContext;
import com.madisp.stupid.expr.StatementListExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A block is basically an expression with some arguments that can be
 * evaluated later. A block is not a closure however, e.g., it doesn't
 * have a local scope.
 */
public class Block {
	private final StatementListExpression body;
	private final String[] varNames;

	/**
	 * Create a new block with named arguments
	 * @param varNames list of argument names
	 * @param body expression that is to be evaluated
	 */
	public Block(String[] varNames, StatementListExpression body) {
		this.varNames = Arrays.copyOf(varNames, varNames.length);
		this.body = body;
	}

	/**
	 * Evaluate the body of a block with given arguments. The length
	 * of arguments must match the length of given names in the constructor.
	 * @param ctx The context wherein to evaluate the block
	 * @param args argument values (must match the same length and order as given
	 *             in the constructor)
	 * @return The evaluated value of the block
	 */
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
