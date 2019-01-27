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
public abstract class GenericFunction2 extends Function {

    public GenericFunction2(String name, String param1Name, String param2Name) {
        super.name = name;
        super.params = new String[] {param1Name, param2Name};
    }

    public abstract Value execute(Value param1, Value param2) throws Exception;
    
    @Override
    public Value execute(Value[] params) throws Exception {
        return execute(params[0], params[1]);
    }
    
}
