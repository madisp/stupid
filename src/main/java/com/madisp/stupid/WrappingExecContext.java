package com.madisp.stupid;

public class WrappingExecContext implements ExecContext {
	private final ExecContext fallback;
	private final ExecContext wrapper;

	public WrappingExecContext(ExecContext wrapper, ExecContext fallback) {
		this.fallback = fallback;
		this.wrapper = wrapper;
	}

	@Override
	public Object getFieldValue(Object root, String identifier) throws NoSuchFieldException {
		try {
			return wrapper.getFieldValue(root, identifier);
		} catch (NoSuchFieldException _) {
			return fallback.getFieldValue(root, identifier);
		}
	}

	@Override
	public Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException {
		try {
			return wrapper.setFieldValue(root, identifier, value);
		} catch (NoSuchFieldException nsfe) {
			return fallback.setFieldValue(root, identifier, value);
		}
	}

	@Override
	public Object callMethod(Object root, String identifier, Object... args) throws NoSuchMethodException {
		try {
			return wrapper.callMethod(root, identifier, args);
		} catch (NoSuchMethodException _) {
			return fallback.callMethod(root, identifier, args);
		}
	}

	@Override
	public Object apply(Object base, Object[] args) throws NoSuchMethodException {
		try {
			return wrapper.apply(base, args);
		} catch (NoSuchMethodException _) {
			return fallback.apply(base, args);
		}
	}

	@Override
	public Object getResource(String pckg, String type, String name) throws NoSuchFieldException {
		try {
			return wrapper.getResource(pckg, type, name);
		} catch (NoSuchFieldException _) {
			return fallback.getResource(pckg, type, name);
		}
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
		return wrapper.getConverter();
	}
}
