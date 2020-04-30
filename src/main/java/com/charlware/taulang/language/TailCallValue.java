/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class TailCallValue implements Value {

    Function function;
    Value[] params;
    
    public TailCallValue(Function function, Value[] params) {
        this.function = function;
        this.params = params;
    }
    
    @Override
    public void setInterpreter(Interpreter interpreter) {
        // Nada
    }
    
    @Override
    public void setMemoryScope(MemoryScope memoryScope) {
        // ...
    }

    @Override
    public Value realize() throws Exception {
        return function.execute(params);
    }

    @Override
    public String getType() {
        return "taillcall";
    }

    @Override
    public String asString() throws Exception {
        // Don't do anything here
        return null;
    }

    @Override
    public Double asNumber() throws Exception {
        // Don't do anything here
        return null;
    }

    @Override
    public Integer asInteger() throws Exception {
        // Don't do anything here
        return null;
    }
    
}
