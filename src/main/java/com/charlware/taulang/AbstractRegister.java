/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.functions.ValueFunction;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public abstract class AbstractRegister {
    protected com.charlware.taulang.Runtime runtime;
    
    public AbstractRegister setRuntime(com.charlware.taulang.Runtime runtime) {
        this.runtime = runtime;
        return this;
    }
    
    protected void reg(Function function) {
        runtime.register(function);
    }
    
    protected void reg(String name, Value value) {
        reg(new ValueFunction(name, value));
    }
    
    protected void alias(String newName, String existingName) {
        Function aliasFunction = runtime.getMemory().get("alias");
        try {
			aliasFunction.execute(new StringValue[] {new StringValue(newName), new StringValue(existingName)});
		} catch (Exception e) {
			throw new RuntimeException("Alias could not complete from Register. ", e);
		}
    }
    
    public abstract void registerAll();
    
    protected BooleanValue trueValue() {
        return BooleanValue.TRUE;
    }
    
    protected BooleanValue falseValue() {
        return BooleanValue.FALSE;
    }
}
