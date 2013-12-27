package com.madisp.stupid.context;

import com.madisp.stupid.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An {@link com.madisp.stupid.ExecContext} that uses reflection to bind the
 * value of an expression to a Java object. This allows you to interface with
 * Java methods and objects from stupid.
 *
 * There is a special identifier, "this" that returns the bound object itself.
 */
public class ReflectionContext extends BaseContext {
	final Object base;

	/**
	 * Create a ReflectionContext bound to the object base
	 * @param base the bound object for the context
	 */
	public ReflectionContext(Object base) {
		this.base = base;
	}

	@Override
	public Object getFieldValue(Object root, String identifier) throws NoSuchFieldException {
		if (root == null) {
			root = base;
		}
		// "this", special case
		if ("this".equals(identifier)) {
			return root;
		}
		return getFieldValueImpl(root, identifier);
	}

	@Override
	public Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException {
		return setFieldValueImpl(root == null ? base : root, identifier, value);
	}

	@Override
	public Object callMethod(Object root, String identifier, Object... args) throws NoSuchMethodException {
		return callMethodImpl(root == null ? base : root, identifier, args);
	}

	@Override
	public Object apply(Object base, Object[] args) throws NoSuchMethodException {
		Class[] argClasses = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			argClasses[i] = args[i].getClass();
		}
		Method[] methods = base.getClass().getMethods();
		Method m = null;
		if (methods.length == 1) {
			if (methods[0].isAccessible() && ReflectionUtil.isCallable(methods[0], argClasses)) {
				m = methods[0];
			}
		}
		// try interfaces
		if (m == null) {
			for (Class clz : base.getClass().getInterfaces()) {
				int numMethods = clz.getMethods().length;
				boolean callable = ReflectionUtil.isCallable(clz.getMethods()[0], argClasses);
				if (numMethods == 1 && callable) {
					m = clz.getMethods()[0];
					break;
				}
			}
		}
		if (m != null) {
			try {
				return m.invoke(base, ReflectionUtil.collapse(methods[0].getParameterTypes(), args));
			} catch (IllegalAccessException e) {
				throw new NoSuchMethodException();
			} catch (InvocationTargetException e) {
				throw new NoSuchMethodException();
			}
		}
		throw new NoSuchMethodException();
	}

	private Object getFieldValueImpl(Object instance, String identifier) throws NoSuchFieldException {
		try {
			for (Field f : instance.getClass().getFields()) {
				if (f.getName().equals(identifier)) {
					return f.get(instance);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		throw new NoSuchFieldException();
	}

	private Object callMethodImpl(Object instance, String method, Object... args) throws NoSuchMethodException {
		Method m = ReflectionUtil.getMethodBySignature(instance.getClass(), false, method, args);
		if (m != null) {
			try {
				return m.invoke(instance, ReflectionUtil.collapse(m.getParameterTypes(), args));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		throw new NoSuchMethodException();
	}

	private Object setFieldValueImpl(Object instance, String identifier, Object value) throws NoSuchFieldException {
		try {
			for (Field f : instance.getClass().getFields()) {
				if (f.getName().equals(identifier)) {
					f.set(instance, value);
					return value;
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		throw new NoSuchFieldException();
	}

}
