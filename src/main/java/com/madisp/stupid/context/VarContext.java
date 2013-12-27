package com.madisp.stupid.context;

import com.madisp.stupid.Value;

import java.util.HashMap;
import java.util.Map;

public class VarContext extends BaseContext {

	public static enum Type { NO_CREATE, CREATE_ON_SET, CREATE_ON_SET_OR_GET }
	private final Type type;
	private final Var base;

	public VarContext(Map<String, Object> vars) {
		this(Type.NO_CREATE, vars);
	}

	public VarContext(Type type) {
		this(type, null);
	}

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
