/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.Memory;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.language.TailCallValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class DefinedFunction extends Function {

    private ListToken code;

    public DefinedFunction(String funcName, String[] funcParamArr, ListToken funcTokens) {
        name = funcName;
        params = funcParamArr;
        code = funcTokens;
    }

    public ListToken getCode() {
        return code;
    }

    @Override
    public Value execute(Value[] paramValues) throws Exception {
    	Memory memory = runtime.getMemory();
    	MemoryScope oldScope = memory.getCurrentScope();
  		memory.setCurrentScope(this.memory);
    	try {
    		return runtime.functionCaller.call(code.iterator(), params, paramValues);
    	}
    	finally {
   			memory.setCurrentScope(oldScope);
    	}
    }
}
