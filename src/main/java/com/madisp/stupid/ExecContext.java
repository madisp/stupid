package com.madisp.stupid;

/**
 * A ExecContext facilitates access to the external environment.
 * Implementations may only provide a subset of the functionality (if stubbed, then a
 * NoSuch* exception should be thrown).
 *
 * An Expression that is evaluated with an empty ExecContext will basically be a
 * constant expression - it won't have access to any fields or methods.
 *
 * A good example of a context is the {@link com.madisp.stupid.context.ReflectionContext}
 * which binds the context to a specific POJO instance.
 */
public interface ExecContext {
	/**
	 * Get a field value from the environment.
	 * @param root The object whose field value is being queried. May be null which semantically
	 *             means getting a value from the "scope" or ExecContext itself.
	 * @param identifier field identifier
	 * @return the Java value of the given field
	 * @throws NoSuchFieldException if field was not found / getting value is not supported
	 */
	Object getFieldValue(Object root, String identifier) throws NoSuchFieldException;

	/**
	 * Assigns a value to a field in the environment.
	 * @param root The object whose field value is being set. May be null which semantically
	 *             means getting a value from the "scope" or ExecContext itself.
	 * @param identifier field identifier
	 * @param value the new value for the field
	 * @return if successful, the value itself
	 * @throws NoSuchFieldException if the field was not found / setting values is not supported
	 */
	Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException;

	/**
	 * Calls a method in the environment.
	 * @param root The object whose method is being called. May be null.
	 * @param identifier The method name
	 * @param args List of arguments, the types of these will be matched against the methods
	 * @return The result of calling {root}.{identifier}({args})
	 * @throws NoSuchMethodException if no method with matching signature was found or calling is not supported
	 */
	Object callMethod(Object root, String identifier, Object... args) throws NoSuchMethodException;

	/**
	 * Yield a value with some arguments.
	 * Typically used for evaluating {@link Block} instances, but in some implementations
	 * may also be used to execute SAM (single-access-method) interface. For an example
	 * implementation see {@link com.madisp.stupid.context.ReflectionContext}
	 * @param base The object/value to yield
	 * @param args The arguments, if any, to apply
	 * @return The result of yielding the base object
	 * @throws NoSuchMethodException
	 */
	Object apply(Object base, Object[] args) throws NoSuchMethodException;

	/**
	 * Obtain a descriptor for a given resource.
	 * The only Android-specific part in stupid. This method was intended to be used
	 * for resolving android integer ID's from package-type-name tuplets. It may be
	 * reused for other purposes if it makes sense. All implementations in stupid itself
	 * currently throw NoSuchFieldException.
	 * @param pckg The package name of the resource
	 * @param type The type of the resource
	 * @param name The identifier for the resource
	 * @return An object describing the resource
	 * @throws NoSuchFieldException if the resource wasn't found
	 */
	Object getResource(String pckg, String type, String name) throws NoSuchFieldException;

	/**
	 * Dereference an object.
	 * Usually this means applying .value(this) onto a value until
	 * we have a POJO instance. See {@link com.madisp.stupid.context.BaseContext} for an
	 * implementation.
	 * @param object The object to dereference. May be null.
	 * @return The dereferenced object. May return the same object if it already was dereferenced.
	 */
	Object dereference(Object object);

	/**
	 * Obtain the type coercion rules.
	 * @return the Converter defining the type coercion rules for the environment
	 */
	Converter getConverter();
}
