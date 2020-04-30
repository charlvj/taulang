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
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class LambdaFunction extends Function {

    static int seq = 0;
    
    public LambdaFunction() {
        name = "lambda";
        params = new String[] { "params", "code" };
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        List<Value> paramNames = ((ListValue) params[0]).getValue();
        String[] funcParamArr = new String[paramNames.size()];
        int i = 0;
        for(Value p: paramNames) {
            funcParamArr[i++] = p.asString();
        }
        ListValue codeListValue = (ListValue) params[1];
        
        Function lambdaFunction = new AnonymousFunction(funcParamArr, codeListValue);
        lambdaFunction.setRuntime(runtime);
        lambdaFunction.setMemory(memory);
        return new FunctionValue(lambdaFunction);
    }
    
}
