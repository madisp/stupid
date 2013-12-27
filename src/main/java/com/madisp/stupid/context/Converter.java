package com.madisp.stupid.context;

public interface Converter {
	boolean toBool(Object value);
	int toInt(Object value);
	double toDouble(Object value);
	String toString(Object value);
}
