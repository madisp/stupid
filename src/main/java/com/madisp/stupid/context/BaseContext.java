package com.madisp.stupid.context;

import com.madisp.stupid.Value;

public class BaseContext implements ExecContext {
	private static final Converter DEFAULT_CONVERTER = new DefaultConverter();

	@Override
	public Object getFieldValue(Object root, String identifier) throws NoSuchFieldException {
		throw new NoSuchFieldException();
	}

	@Override
	public Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException {
		throw new NoSuchFieldException();
	}

	@Override
	public Object callMethod(Object root, String identifier, Object... args) throws NoSuchMethodException {
		return null;
	}

	@Override
	public Object apply(Object base, Object[] args) throws NoSuchMethodException {
		return null;
	}

	@Override
	public Object getResource(String pckg, String type, String name) throws NoSuchFieldException {
		throw new NoSuchFieldException();
	}

	@Override
	public Object dereference(Object object) {
		while (object != null && object instanceof Value) {
			object = ((Value)object).value(this);
		}
		return object;
	}

	@Override
	public Converter getConverter() {
		return DEFAULT_CONVERTER;
	}
}
