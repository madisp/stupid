package com.madisp.stupid.context;

import com.madisp.stupid.ExecContext;
import com.madisp.stupid.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link ExecContext} backed by a {@link Map} of variables. Enables the script
 * to create new variables. One may also provide a set of constants by providing a
 * {@link Map}<String, Object> when constructing.
 */
public class VarContext extends BaseContext {

	/**
	 * The type of the VarContext.
	 */
	public static enum Type {
		/**
		 * No variables can be created, the variable set is immutable.
		 */
		NO_CREATE,
		/**
		 * Variables can (and will be created) with assignment
		 */
		CREATE_ON_SET,
		/**
		 * Variables will be created when getting a value even if they don't
		 * exist yet. A bit weird but useful later on.
		 */
		CREATE_ON_SET_OR_GET
	}
	private final Type type;
	private final Var base;

	/**
	 * Create a new immutable context with a set of variables.
	 * @param vars The String->Object (identifier->value) map of variables
	 */
	public VarContext(Map<String, Object> vars) {
		this(Type.NO_CREATE, vars);
	}

	/**
	 * Create a new context with the given type and no starting variables.
	 * @param type The type of the context
	 */
	public VarContext(Type type) {
		this(type, null);
	}

	/**
	 * Create a new context with the given type and a set of variables
	 * @param type The type of the context
	 * @param vars The String->Object (identifier->value) map of variables
	 */
	public VarContext(Type type, Map<String, Object> vars) {
		this.type = type;
		this.base = new Var(null);
		if (vars != null) {
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				base.put(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public Object getFieldValue(Object root, String identifier) throws NoSuchFieldException {
		Var obj = base;
		if (root != null) {
			if (root instanceof Var) {
				obj = (Var)root;
			} else {
				throw new NoSuchFieldException();
			}
		}

		if (!obj.has(identifier)) {
			if (type != Type.CREATE_ON_SET_OR_GET) {
				throw new NoSuchFieldException();
			}
			obj.put(identifier, null);
		}
		return obj.get(identifier);
	}

	@Override
	public Object setFieldValue(Object root, String identifier, Object value) throws NoSuchFieldException {
		Var obj = base;
		if (root != null) {
			if (root instanceof Var) {
				obj = (Var)root;
			} else {
				throw new NoSuchFieldException();
			}
		}

		if (!obj.has(identifier)) {
			if (type == Type.NO_CREATE) {
				throw new NoSuchFieldException();
			}
		}
		return obj.put(identifier, new Var(value));
	}

	private static class Var implements Value {
		private Object value = null;
		private final Map<String, Var> vars;

		private Var(Object value) {
			this.value = value;
			this.vars = new HashMap<String, Var>();
		}

		private boolean has(String identifier) {
			return vars.containsKey(identifier);
		}

		private Object get(String identifier) {
			return vars.get(identifier);
		}

		private Object put(String identifier, Object value) {
			vars.put(identifier, new Var(value));
			return value;
		}

		@Override
		public Object value(ExecContext ctx) {
			return value;
		}
	}
}
