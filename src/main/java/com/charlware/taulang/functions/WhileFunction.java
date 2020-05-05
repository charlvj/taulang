/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class WhileFunction extends Function {
    
    public WhileFunction() {
        name = "while";
        params = new String[] {"predicate", "code"};
    }
    
    private boolean evalPredicate(Value predicate) throws Exception {
    	BooleanValue result = (BooleanValue) runtime.callFunction.execute(predicate, ListValue.EMPTY_LIST);
    	return result.getValue();
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
    	Value predicate = params[0];
    	Value code = params[1];
    	Value result = null;
    	while(evalPredicate(predicate)) {
    		result = runtime.callFunction.execute(code, ListValue.EMPTY_LIST);
    		if(result instanceof ErrorValue) 
                break;
    	}
    	return result;
//        ListValue listValue = (ListValue) params[1];
//        ListToken listToken = listValue.getListToken();
//        Value result = null;
//        for(int i = 0; i < times; i++) {
//            result = runtime.callFunction.call(listValue, new NumberValue((double) i+1));
////            result = runtime.getInterpreter().eval(listToken.iterator());
//        }
//        return result;
    }
    
}
