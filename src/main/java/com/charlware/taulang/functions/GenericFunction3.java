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
public abstract class GenericFunction3 extends Function {

    public GenericFunction3(String name, String param1Name, String param2Name, String param3Name) {
        super.name = name;
        super.params = new String[] {param1Name, param2Name, param3Name};
    }

    public abstract Value execute(Value param1, Value param2, Value param3) throws Exception;
    
    @Override
    public Value execute(Value[] params) throws Exception {
        return execute(params[0], params[1], params[2]);
    }
    
}
