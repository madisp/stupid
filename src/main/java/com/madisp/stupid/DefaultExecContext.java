package com.madisp.stupid;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class DefaultExecContext implements ExecContext {
	private final Deque<Scope> stack = new LinkedList<Scope>();

	@Override
	public boolean toBool(Object value) {
		value = dereference(value);
		if (value == null) {
			return false;
		} else if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		} else if (value instanceof CharSequence) {
			return ((CharSequence)value).length() > 0;
		} else if (value instanceof Integer) {
			return ((Integer)value).intValue() != 0;
		}
		return false;
	}

	@Override
	public int toInt(Object value) {
		value = dereference(value);
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}
		return 0;
	}

	@Override
	public double toDouble(Object value) {
		value = dereference(value);
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		} else if (value instanceof Integer) {
			return ((Integer)value).doubleValue();
		}
		return 0.0d;
	}

	@Override
	public String toString(Object value) {
		value = dereference(value);
		return value == null ? "null" : value.toString();
	}

	@Override
	public Object deref(Value value) {
		return dereference((Object)value);
	}

	private Object dereference(Object value) {
		while (value != null && value instanceof Value) {
			value = ((Value)value).value(this);
		}
		return value;
	}

	@Override
	public void pushScope(Scope scope) {
		stack.push(scope);
	}

	@Override
	public void popScope() {
		stack.pop();
	}

	@Override
	public Object getFieldValue(Object root, String identifier) {
		if (root == null && "$ctx".equals(identifier)) {
			return this; // is this a bad idea?
		}
		Iterator<Scope> iter = stack.iterator();
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

	@Override
	public Object setFieldValue(Object root, String identifier, Object value) {
		Iterator<Scope> iter = stack.descendingIterator();
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

	@Override
	public Object callMethod(Object root, String identifier, Object... args) {
		Iterator<Scope> iter = stack.descendingIterator();
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
		Iterator<Scope> iter = stack.descendingIterator();
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
}
