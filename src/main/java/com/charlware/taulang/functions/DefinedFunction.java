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
        MemoryScope savedScope = memory.getCurrentScope();
        memory.setCurrentScope(getMemory());
        MemoryScope scope = memory.pushScope();
        scope.putAll(savedScope);
        try {
            for (int i = 0; i < params.length; i++) {
                scope.put(new ValueFunction(params[i], paramValues[i]));
            }
            Value result = runtime.getInterpreter().eval(code.iterator());
            if (runtime.getFlags().isTailCallOptimizationEnabled() && result instanceof TailCallValue) {
//                        memory.popScope();
//                        scope = memory.pushScope();
                for (int i = 0; i < params.length; i++) {
                    scope.put(new ValueFunction(params[i], paramValues[i]));
                }
                result = result.realize();
            }
            result.setMemoryScope(scope);
            return result;
        }
        finally {
            memory.popScope();
            memory.setCurrentScope(savedScope);
        }
    }
}
