package com.madisp.stupid;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class DefaultConverter implements Converter {
	@Override
	public boolean toBool(Object value) {
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
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}
		return 0;
	}

	@Override
	public double toDouble(Object value) {
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		} else if (value instanceof Integer) {
			return ((Integer)value).doubleValue();
		}
		return 0.0d;
	}

	@Override
	public String toString(Object value) {
		return value == null ? "null" : value.toString();
	}
}
