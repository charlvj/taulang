/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.TauObject;
import com.charlware.taulang.values.ObjectValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class ObjectFunction extends Function {

    public ObjectFunction() {
        name = "object";
        params = new String[] {"initCode"};
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        Value callable = params[0];
        Value object = new ObjectValue(new TauObject());
        return object;
    }
    
}
