package com.madisp.stupid;

public interface ExecContext {
	boolean toBool(Object value);
	int toInt(Object value);
	double toDouble(Object value);
	String toString(Object value);
	Object deref(Value value);

	void pushScope(Scope scope);
	void popScope();

	Object getFieldValue(Object root, String identifier);
	Object setFieldValue(Object root, String identifier, Object value);
	Object callMethod(Object root, String identifier, Object... args);

	Object apply(Object base, Object[] args);
}
