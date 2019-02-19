/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.Memory;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.Symbol;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.SymbolValue;
import com.charlware.taulang.values.Value;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class CallFunction extends Function {
    
    public CallFunction() {
        name = "call";
        params = new String[] { "callable", "params" };
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        if(params.length != 2)
            return new ErrorValue(ErrorFactory.createInvalidParamsError("Two parameters expected for a call"));
        return execute(params[0], params[1]);
    }
    
    public Value execute(Value callable, Value callParams) throws Exception {
        Value result = null;
        
        if(callable instanceof SymbolValue)
            result = call((SymbolValue) callable, callParams);
        else if(callable instanceof FunctionValue) 
            result = call(((FunctionValue) callable).getValue(), callParams);
        else if(callable instanceof ListValue) 
            result = call((ListValue) callable, callParams);
        
        return result;
    }

    public Value call(SymbolValue symbol, Value callParams) throws Exception {
        Symbol sym = symbol.getValue();
        Memory memory = runtime.getMemory();
        Function function = memory.get(sym.getStringValue());
        return call(function, callParams);
    }
    
    public Value call(Function function, ListValue callParams) throws Exception {
        int numParams = function.getNumParams();
        List<Value> callParamsList = callParams.getValue();
        
        return function.execute(callParamsList);
    }
    
    public Value call(Function function, Value callParams) throws Exception {
        if(callParams instanceof ListValue) 
            return call(function, (ListValue) callParams);
        else {
            ListValue listValue = new ListValue(Arrays.asList(callParams));
            return call(function, listValue);
        }
    }

    public Value call(ListValue code, Value callParams) throws Exception {
        Memory memory = runtime.getMemory();
        memory.push();
        
        try {
            if(callParams instanceof ListValue) {
                List<Value> paramsList = ((ListValue) callParams).getValue();
                for(int i = 0; i < paramsList.size(); i++) {
                    memory.put(new ValueFunction("$" + i, paramsList.get(i)));
                }
            } else {
                memory.put(new ValueFunction("$1", callParams));
            }
            Value result = runtime.getInterpreter().eval(code.getListToken().iterator());
            return result;
        } 
        finally {
            memory.pop();
        }
    }
    
}
