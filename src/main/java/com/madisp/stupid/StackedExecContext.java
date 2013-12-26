package com.madisp.stupid;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class StackedExecContext extends BaseExecContext {
	private final Deque<ExecContext> stack = new LinkedList<ExecContext>();

	public void pushExecContext(ExecContext scope) {
		stack.push(scope);
	}

	public void popExecContext() {
		stack.pop();
	}

	public Object getFieldValue(Object root, String identifier) {
		Iterator<ExecContext> iter = stack.iterator();
		while (iter.hasNext()) {
			try {
				return iter.next().getFieldValue(root, identifier);
			} catch (NoSuchFieldException e) {
				// ignore, jump to next scope
				if (false) { e.printStackTrace(); }
			}
		}
		return null;
	}

	public Object setFieldValue(Object root, String identifier, Object value) {
		Iterator<ExecContext> iter = stack.descendingIterator();
		while (iter.hasNext()) {
			try {
				return iter.next().setFieldValue(root, identifier, value);
			} catch (NoSuchFieldException e) {
				// ignore, jump to next scope
				if (false) { e.printStackTrace(); }
			}
		}
		return null;
	}

	public Object callMethod(Object root, String identifier, Object... args) {
		Iterator<ExecContext> iter = stack.descendingIterator();
		while (iter.hasNext()) {
			try {
				return iter.next().callMethod(dereference(root), identifier, args);
			} catch (NoSuchMethodException e) {
				// ignore, jump to next scope
				if (false) { e.printStackTrace(); }
			}
		}
		return null;
	}

	@Override
	public Object apply(Object base, Object[] args) {
		if (base instanceof Block) {
			return ((Block) base).yield(this, args);
		}
		Iterator<ExecContext> iter = stack.descendingIterator();
		while (iter.hasNext()) {
			try {
				return iter.next().apply(base, args);
			} catch (NoSuchMethodException e) {
				// ignore, jump to next scope
				if (false) { e.printStackTrace(); }
			}
		}
		return null;
	}

	public Object getResource(String pckg, String type, String name) {
		Iterator<ExecContext> iter = stack.iterator();
		while (iter.hasNext()) {
			try {
				return iter.next().getResource(pckg, type, name);
			} catch (NoSuchFieldException e) {
				// ignore, jump to next scope
				if (false) { e.printStackTrace(); }
			}
		}
		return null;
	}
}
