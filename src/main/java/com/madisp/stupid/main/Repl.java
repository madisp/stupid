package com.madisp.stupid.main;

import com.madisp.stupid.ExpressionFactory;
import com.madisp.stupid.context.ReflectionContext;
import com.madisp.stupid.context.StackContext;
import com.madisp.stupid.Value;
import com.madisp.stupid.context.VarContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * A Read-Eval-Print-Loop main class that serves as a simple example of integrating
 * stupid with some Java code.
 */
public class Repl {
	private final StackContext ctx;
	private final ExpressionFactory factory;
	private final BufferedReader reader;
	private boolean quit = false;

	public Repl(String encoding) throws UnsupportedEncodingException {
		ctx = new StackContext();
		factory = new ExpressionFactory();
		// access to quit method
		ctx.pushExecContext(new ReflectionContext(this));
		// create a var scope
		ctx.pushExecContext(new VarContext(VarContext.Type.CREATE_ON_SET_OR_GET));
		reader = new BufferedReader(new InputStreamReader(System.in, encoding));
	}

	private void loop() {
		try {
			while (!quit) {
				String line = reader.readLine();
				Value v = factory.parseExpression(line);
				System.out.println(ctx.dereference(v));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String quit() {
		quit = true;
		return "Quitting REPL";
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		Repl repl = new Repl(Charset.defaultCharset().name());
		repl.loop();
	}
}
