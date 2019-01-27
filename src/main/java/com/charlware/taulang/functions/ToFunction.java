/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.Memory;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class ToFunction extends Function {

    public ToFunction() {
        name = "to";
        params = new String[] {"name", "params", "code"};
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        String funcName = params[0].asString();
        ListValue funcParams = (ListValue) params[1];
        Value[] values = funcParams.getValue().toArray(new Value[] {});
        String[] funcParamArr = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            funcParamArr[i] = values[i].asString();
        }
        ListValue funcCode = (ListValue) params[2];
        ListToken funcTokens = funcCode.getListToken();
        
        Function function = new Function() {
            private ListToken code;
            
            {
                name = funcName;
                params = funcParamArr;
                runtime = ToFunction.this.runtime;
                code = funcTokens;
            }

            @Override
            public Value execute(Value[] paramValues) throws Exception {
                Memory memory = runtime.getMemory();
                memory.push();
                try {
                    for(int i = 0; i < params.length; i++) {
                        memory.put(new ValueFunction(params[i], paramValues[i]));
                    }
                    Value result = runtime.getInterpreter().eval(code.iterator());
                    return result;
                } 
                finally {
                    memory.pop();
                }
            }
        };
        function.setRuntime(runtime);
        runtime.getMemory().put(function);
        return null;
    }
    
}
