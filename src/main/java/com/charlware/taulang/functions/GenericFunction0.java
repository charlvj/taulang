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
public abstract class GenericFunction0 extends Function {

    public GenericFunction0(String name) {
        super.name = name;
        noParams();
    }

    public abstract Value execute() throws Exception;
    
    @Override
    public Value execute(Value[] params) throws Exception {
        return execute();
    }
    
}
