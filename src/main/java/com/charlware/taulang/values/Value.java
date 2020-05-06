/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import javax.management.RuntimeErrorException;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.MemoryScope;

/**
 *
 * @author charlvj
 */
public interface Value {
    public void setInterpreter(Interpreter interpreter);
    public void setMemoryScope(MemoryScope memoryScope);
    public Value realize() throws Exception;
    public String getType();
    public String asString() throws Exception;
    public Double asDouble() throws Exception;
    public Integer asInteger() throws Exception;
    public Boolean asBoolean() throws Exception;
    
    public static Value of(Object o) {
    	if(o instanceof Value) 
    		return (Value) o;
    	if(o == null)
    		return NullValue.NULL;
    	if(o instanceof Integer)
    		return new IntegerValue((Integer) o);
    	if(o instanceof Double)
    		return new DoubleValue((Double) o);
    	if(o instanceof String)
    		return new StringValue((String) o);
    	if(o instanceof Boolean)
    		return new BooleanValue((Boolean) o);
    	
    	
    	throw new RuntimeException("Could not map the object to a Value: " + o + ": " + o.getClass());
    }
}
