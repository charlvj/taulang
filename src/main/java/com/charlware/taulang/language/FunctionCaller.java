package com.charlware.taulang.language;

import java.util.Iterator;

import com.charlware.taulang.Memory;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.functions.ValueFunction;
import com.charlware.taulang.values.Value;

public class FunctionCaller {
	private final com.charlware.taulang.Runtime runtime;
	
	public FunctionCaller(com.charlware.taulang.Runtime runtime) {
		this.runtime = runtime;
	}
	
	public Value call(Iterator<Token> code, String[] paramNames, Value[] params) throws Exception {
		Memory memory = runtime.getMemory();
		MemoryScope scope = memory.pushScope();
		try {
            for(int i = 0; i < paramNames.length; i++) {
                scope.put(new ValueFunction(paramNames[i], params[i]));
            }
            Value result = runtime.getInterpreter().eval(code);
            return result;
        } finally {
        	memory.popScope();
        }
	}
	
	public Value call(Iterator<Token> code, Value[] params) throws Exception {
		String[] paramNames = new String[params.length];
		for(int i = 0; i < params.length; i++) {
			paramNames[i] = "$" + i;
		}
		return call(code, paramNames, params);
	}
	
	public Value call(Iterator<Token> code, Value param) throws Exception {
		return call(code, new String[] {"$0"}, new Value[] {param});
	}
	
	public Value call(Iterator<Token> code, String paramName, Value param) throws Exception {
		return call(code, new String[] {paramName}, new Value[] {param});
	}
	
	public Value call(Iterator<Token> code) throws Exception {
		return call(code, new String[] {}, new Value[] {});
	}
	
	public Value call(Function function, Value[] params) throws Exception {
		Memory memory = runtime.getMemory();
		MemoryScope scope = memory.pushScope();
		try {
            Value result = function.execute(params);
            return result;
        } finally {
        	memory.popScope();
        }
	}
}
