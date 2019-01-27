/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.Interpreter;

/**
 *
 * @author charlvj
 */
public class NullValue implements Value {

    @Override
    public void setInterpreter(Interpreter interpreter) {
        
    }

    @Override
    public Value realize() throws Exception {
        return this;
    }

    @Override
    public String getType() {
        return "Null";
    }

    @Override
    public String asString() throws Exception {
        return null;
    }

    @Override
    public Double asNumber() throws Exception {
        return null;
    }

    @Override
    public Integer asInteger() throws Exception {
        return null;
    }
    
}
