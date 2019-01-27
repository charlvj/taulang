/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class MakeFunction extends Function {
    public MakeFunction() {
        name = "make";
        params = new String[] { "variableName", "value" };
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        String variableName = params[0].asString();
        Value value = params[1];
        runtime.register(variableName, value);
        return value;
    }

}
