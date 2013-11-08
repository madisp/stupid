package com.madisp.stupid;

public interface Scope {
	Object getFieldValue(Object root, String identifier) throws NoSuchFieldException;
	Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException;
	Object callMethod(Object root, String identifier, Object... args) throws NoSuchMethodException;
	Object apply(Object base, Object[] args) throws NoSuchMethodException;
}
