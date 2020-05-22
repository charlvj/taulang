/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.TauError;

/**
 *
 * @author charlvj
 */
public interface Value extends com.charlware.taulang.values.abilities.Comparable {
    public void setInterpreter(Interpreter interpreter);
    public void setMemoryScope(MemoryScope memoryScope);
    public MemoryScope getMemoryScope();
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
    	if(o instanceof TauError)
    		return new ErrorValue((TauError) o);
    	
    	
    	throw new RuntimeException("Could not map the object to a Value: " + o + ": " + o.getClass());
    }
}
