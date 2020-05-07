/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

/**
 *
 * @author charlvj
 */
public class NullValue implements Value {

    public static NullValue NULL = new NullValue();
    
    @Override
    public void setInterpreter(Interpreter interpreter) {
        
    }

    @Override
    public Value realize() throws Exception {
        return this;
    }

    @Override
    public String getType() {
        return "?";
    }

    @Override
    public String asString() throws Exception {
        return null;
    }

    @Override
    public Double asDouble() throws Exception {
        return null;
    }

    @Override
    public Integer asInteger() throws Exception {
        return null;
    }
    
    @Override
    public Boolean asBoolean() throws Exception {
    	return null;
    }
    
    @Override
    public String toString() {
        return "?";
    }

    @Override
    public void setMemoryScope(MemoryScope memoryScope) {
        
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof NullValue) 
        	return 0;
        else {
        	return 1;
        }
    }
}
