/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.InvalidCastException;

/**
 *
 * @author charlvj
 */
public class FunctionValue extends AbstractValue<Function> {

    public FunctionValue(Function function) {
        super(function);
    }

    @Override
    protected Function processToken() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asString() throws Exception {
        return getValue().getName();
    }
}
