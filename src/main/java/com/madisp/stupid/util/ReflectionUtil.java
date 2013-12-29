package com.madisp.stupid.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A set of methods that are mainly consumed by the {@link com.madisp.stupid.context.ReflectionContext}.
 */
public class ReflectionUtil {
	/**
	 * Finds the first method that matches the given signature in a class
	 * @param clz class to search
	 * @param isStatic if the method is static
	 * @param method name of the method
	 * @param args arguments
	 * @return method or null if not found
	 */
	public static Method getMethodBySignature(Class clz, boolean isStatic, String method, Object... args) {
		Class[] argTypes = new Class[args.length];
		for (int i = 0; i < argTypes.length; i++) {
			argTypes[i] = args[i] == null ? null : args[i].getClass();
		}
		for (Method m : clz.getMethods()) {
			if (Modifier.isStatic(m.getModifiers()) != isStatic) {
				continue;
			}
			if (m.getName().equals(method) && isCallable(m, argTypes)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * Check if method is callable with a set of types
	 * @param m method to check
	 * @param argTypes argument types, ordered
	 * @return true if the method is callable with given types
	 */
	public static boolean isCallable(Method m, Class[] argTypes) {
		Class<?>[] paramTypes = m.getParameterTypes();
		return (isCollapsible(paramTypes, argTypes) || areAssignableFrom(paramTypes, argTypes));
	}

	/**
	 * Check if srcTypes can be collapsed into dstTypes (e.g., if the last type in dstTypes
	 * is an array, can we assume that this is a varargs call?)
	 * @param dstTypes
	 * @param srcTypes
	 * @return
	 */
	public static boolean isCollapsible(Class[] dstTypes, Class[] srcTypes) {
		if (dstTypes.length > srcTypes.length+1 || srcTypes.length == 0 || dstTypes.length == 0) {
			return false;
		}
		for (int i = 0; i < dstTypes.length - 1; i++) {
			if (!isAssignableFrom(dstTypes[i], srcTypes[i])) {
				return false;
			}
		}
		// is the last type an array type? (e.g., varargs?)
		Class last = dstTypes[dstTypes.length - 1];
		if (!last.isArray()) {
			return false;
		}
		for (int i = dstTypes.length; i < srcTypes.length; i++) {
			if (!isAssignableFrom(last.getComponentType(), srcTypes[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Collapse src array so it's class signature matches dest.
	 * @param dst
	 * @param src
	 * @return
	 */
	public static Object[] collapse(Class[] dst, Object[] src) {
		if (dst.length == src.length) {
			if (dst.length == 0 || !dst[dst.length-1].isArray()) {
				return src;
			} else if (isAssignableFrom(dst[dst.length-1], src[src.length-1].getClass())) {
				return src;
			}
		}
		Object[] ret = new Object[dst.length];
		Object[] lastArg = (Object[]) Array.newInstance(dst[dst.length - 1].getComponentType(), src.length - (dst.length - 1));
		for (int i = 0; i < lastArg.length; i++) {
			lastArg[i] = src[dst.length - 1 + i];
		}
		for (int i = 0; i < ret.length - 1; i++) {
			ret[i] = src[i];
		}
		ret[ret.length - 1] = lastArg;
		return ret;
	}

	/**
	 * Can we assign to an list with signature dstTypes from a list with signature srcTypes?
	 * e.g. is dstTypes[i] assignable with something from srcTypes[i] ?
	 * @param dstTypes
	 * @param srcTypes
	 * @return
	 */
	public static boolean areAssignableFrom(Class[] dstTypes, Class[] srcTypes) {
		if (dstTypes == null || srcTypes == null || dstTypes.length != srcTypes.length) {
			return false;
		}
		for (int i = 0; i < dstTypes.length; i++) {
			if (!isAssignableFrom(dstTypes[i], srcTypes[i])) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean isAssignableFrom(Class dstType, Class srcType) {
		// same class
		if (dstType.equals(srcType)) {
			return true;
		}
		// autoboxing
		if (dstType.isPrimitive()) {
			if (srcType == null) {
				return false; // null can't be assigned to primitive
			}

			final Class[] primitiveNums = { byte.class, short.class, int.class, long.class, float.class, double.class };
			final Class[] boxedNums = { Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class };

			final int primitiveIdx = indexOf(primitiveNums, dstType);
			final int boxedIdx = indexOf(boxedNums, srcType);

			if (boxedIdx >= 0 && boxedIdx <= primitiveIdx) {
				return true;
			}

			// boolean
			if (dstType.equals(boolean.class) && srcType.equals(Boolean.class)) {
				return true;
			}
			// char
			if (dstType.equals(char.class) && srcType.equals(Character.class)) {
				return true;
			}
		}
		if (srcType == null) {
			// null can be assigned to anything
			return true;
		}
		// other, inheritance, etc
		return dstType.isAssignableFrom(srcType);
	}

	private static int indexOf(Object[] haystack, Object needle) {
		for (int i = 0; i < haystack.length; i++) {
			if (needle == null && haystack[i] == null || needle != null && needle.equals(haystack[i])) {
				return i;
			}
		}
		return -1;
	}
}
