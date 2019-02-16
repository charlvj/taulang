/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class RepeatFunction extends Function {
    
    public RepeatFunction() {
        name = "repeat";
        params = new String[] {"times", "code"};
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        int times = params[0].asNumber().intValue();
        ListValue listValue = (ListValue) params[1];
        ListToken listToken = listValue.getListToken();
        Value result = null;
        for(int i = 0; i < times; i++) {
            result = runtime.callFunction.call(listValue, new NumberValue((double) i+1));
//            result = runtime.getInterpreter().eval(listToken.iterator());
        }
        return result;
    }
    
}
