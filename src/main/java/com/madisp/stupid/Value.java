package com.madisp.stupid;

import com.madisp.stupid.context.ExecContext;

public interface Value {
	Object value(ExecContext ctx);
}
