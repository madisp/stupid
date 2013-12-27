package com.madisp.stupid.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtil {
	public static Method getMethodBySignature(Class clz, boolean isStatic, String method, Object... args) {
		Class[] paramClasses = new Class[args.length];
		for (int i = 0; i < paramClasses.length; i++) {
			paramClasses[i] = args[i] == null ? null : args[i].getClass();
		}
		for (Method m : clz.getMethods()) {
			if (Modifier.isStatic(m.getModifiers()) != isStatic) {
				continue;
			}
			if (m.getName().equals(method) && isCallable(m, paramClasses)) {
				return m;
			}
		}
		return null;
	}

	public static boolean isCallable(Method m, Class[] paramClasses) {
		Class<?>[] paramTypes = m.getParameterTypes();
		return (isCollapsible(paramTypes, paramClasses) || areAssignableFrom(paramTypes, paramClasses));
	}

	public static boolean isCollapsible(Class[] a, Class[] b) {
		if (a.length > b.length+1 || b.length == 0) {
			return false;
		}
		for (int i = 0; i < a.length - 1; i++) {
			if (!isAssignableFrom(a[i], b[i])) {
				return false;
			}
		}
		// is the last type an array type? (e.g., varargs?)
		Class last = a[a.length - 1];
		if (!last.isArray()) {
			return false;
		}
		for (int i = a.length; i < b.length; i++) {
			if (!isAssignableFrom(last.getComponentType(), b[i])) {
				return false;
			}
		}
		return true;
	}

	public static Object[] collapse(Class[] dest, Object[] src) {
		if (dest.length == src.length) {
			if (dest.length == 0 || !dest[dest.length-1].isArray()) {
				return src;
			} else if (isAssignableFrom(dest[dest.length-1], src[src.length-1].getClass())) {
				return src;
			}
		}
		Object[] ret = new Object[dest.length];
		Object[] lastArg = (Object[]) Array.newInstance(dest[dest.length - 1].getComponentType(), src.length - (dest.length - 1));
		for (int i = 0; i < lastArg.length; i++) {
			lastArg[i] = src[dest.length - 1 + i];
		}
		for (int i = 0; i < ret.length - 1; i++) {
			ret[i] = src[i];
		}
		ret[ret.length - 1] = lastArg;
		return ret;
	}

	public static boolean areAssignableFrom(Class[] a, Class[] b) {
		if (a == null || b == null || a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (!isAssignableFrom(a[i], b[i])) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean isAssignableFrom(Class a, Class b) {
		// same class
		if (a.equals(b)) {
			return true;
		}
		// byte
		if (a.equals(byte.class) && b.equals(Byte.class)) {
			return true;
		}
		if (b.equals(byte.class) && a.equals(Byte.class)) {
			return true;
		}
		// short
		if (a.equals(short.class) && b.equals(Short.class)) {
			return true;
		}
		if (b.equals(short.class) && a.equals(Short.class)) {
			return true;
		}
		// int
		if (a.equals(int.class) && b.equals(Integer.class)) {
			return true;
		}
		if (b.equals(int.class) && a.equals(Integer.class)) {
			return true;
		}
		// long
		if (a.equals(long.class) && b.equals(Long.class)) {
			return true;
		}
		if (b.equals(long.class) && a.equals(Long.class)) {
			return true;
		}
		// float
		if (a.equals(float.class) && b.equals(Float.class)) {
			return true;
		}
		if (b.equals(float.class) && a.equals(Float.class)) {
			return true;
		}
		// double
		if (a.equals(double.class) && b.equals(Double.class)) {
			return true;
		}
		if (b.equals(double.class) && a.equals(Double.class)) {
			return true;
		}
		// boolean
		if (a.equals(boolean.class) && b.equals(Boolean.class)) {
			return true;
		}
		if (b.equals(boolean.class) && a.equals(Boolean.class)) {
			return true;
		}
		// char
		if (a.equals(char.class) && b.equals(Character.class)) {
			return true;
		}
		if (b.equals(char.class) && a.equals(Character.class)) {
			return true;
		}
		// other, inheritance, etc
		return a.isAssignableFrom(b);
	}
}
