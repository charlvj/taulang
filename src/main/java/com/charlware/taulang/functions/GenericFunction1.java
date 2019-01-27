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
public abstract class GenericFunction1 extends Function {

    public GenericFunction1(String name, String paramName) {
        super.name = name;
        super.params = new String[] {paramName};
    }

    public abstract Value execute(Value param) throws Exception;
    
    @Override
    public Value execute(Value[] params) throws Exception {
        return execute(params[0]);
    }
    
}
