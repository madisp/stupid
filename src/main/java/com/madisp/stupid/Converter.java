package com.madisp.stupid;

public interface Converter {
	boolean toBool(Object value);
	int toInt(Object value);
	double toDouble(Object value);
	String toString(Object value);
}
