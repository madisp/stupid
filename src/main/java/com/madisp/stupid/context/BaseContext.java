package com.madisp.stupid.context;

import com.madisp.stupid.Converter;
import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

/**
 * Base class for all of the {@link ExecContext} implementations in stupid.
 * All of the methods that can throw NoSuch* exceptions will throw them.
 * The dereference() method runs instanceof {@link Value} checks in a loop and
 * evaluates the Value until it obtains a POJO instance or null.
 * The getConverter() method returns a {@link DefaultConverter} but this may
 * be overridden by calling setConverter().
 */
public class BaseContext implements ExecContext {
	private Converter converter = new DefaultConverter();

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
		throw new NoSuchMethodException();
	}

	@Override
	public Object apply(Object base, Object[] args) throws NoSuchMethodException {
		throw new NoSuchMethodException();
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
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}
}
