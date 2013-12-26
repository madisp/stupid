package com.madisp.stupid;

/**
 * A ExecContext facilitates access to the external environment.
 * Implementations may only provide a subset of the functionality (if stubbed, then a
 * NoSuch* exception should be thrown).
 */
public interface ExecContext {
	Object getFieldValue(Object root, String identifier) throws NoSuchFieldException;
	Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException;
	Object callMethod(Object root, String identifier, Object... args) throws NoSuchMethodException;
	Object apply(Object base, Object[] args) throws NoSuchMethodException;
	Object getResource(String pckg, String type, String name) throws NoSuchFieldException;
	Object dereference(Object object);
	Converter getConverter();
}
