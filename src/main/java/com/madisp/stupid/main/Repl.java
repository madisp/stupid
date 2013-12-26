package com.madisp.stupid.main;

import com.madisp.stupid.Converter;
import com.madisp.stupid.DefaultConverter;
import com.madisp.stupid.ExpressionFactory;
import com.madisp.stupid.ReflectionScope;
import com.madisp.stupid.StackedExecContext;
import com.madisp.stupid.Value;
import com.madisp.stupid.VarScope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Repl {
	private final StackedExecContext ctx;
	private final ExpressionFactory factory;
	private final BufferedReader reader;
	private boolean quit = false;

	public Repl(String encoding) throws UnsupportedEncodingException {
		ctx = new StackedExecContext();
		factory = new ExpressionFactory();
		// access to quit method
		ctx.pushExecContext(new ReflectionScope(this));
		// create a var scope
		ctx.pushExecContext(new VarScope(VarScope.Type.CREATE_ON_SET_OR_GET));
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
