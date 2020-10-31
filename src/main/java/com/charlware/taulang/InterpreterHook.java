package com.charlware.taulang;

import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.Value;

public interface InterpreterHook {
	boolean aboutToEval(Token token);
	boolean evaluated(Token token, Value result);
}
