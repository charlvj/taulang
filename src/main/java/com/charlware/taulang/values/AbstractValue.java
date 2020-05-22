/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.Token;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlvj
 */
public abstract class AbstractValue<V> implements Value {
    protected final Token token;
    private V value;
    protected boolean realized = false;
    protected Interpreter interpreter;
    
    protected MemoryScope memoryScope = null;
    
    public AbstractValue(Token token) {
        this.token = token;
    }
    
    public AbstractValue(V value) {
        this.token = null;
        this.value = value;
        this.realized = true;
    }
    
    @Override
    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
    
    public Interpreter getInterpreter() {
        return interpreter;
    }
    
    @Override
    public void setMemoryScope(MemoryScope memoryScope) {
    	if(this.memoryScope == null)
    		this.memoryScope = memoryScope;
    }
    
    @Override
    public MemoryScope getMemoryScope() {
    	return memoryScope;
    }
    
    @Override
    public String getType() {
        return token.getType().toString();
    }
    
    protected abstract V processToken() throws Exception;
    
    @Override
    public Value realize() throws Exception {
        MemoryScope savedScope = null;
        if(!realized) {
            try {
                if(memoryScope != null) {
                    savedScope = interpreter.getRuntime().getMemory().getCurrentScope();
                    interpreter.getRuntime().getMemory().setCurrentScope(memoryScope);
                }
                value = processToken();
                realized = true;
            }
            finally {
                if(memoryScope != null) {
                    interpreter.getRuntime().getMemory().setCurrentScope(savedScope);
                }
            }
        }
        return this;
    }
    
    public V getValue() throws Exception {
        if(!realized) realize();
        return value;
    }
    
    @Override
    public String toString() {
        try {
            return asString();
        } catch (Exception ex) {
            return "<Exception: " + ex.toString() + ">";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractValue<?> other = (AbstractValue<?>) obj;
        try {
            return getValue().equals(other.getValue());
        } catch (Exception ex) {
            Logger.getLogger(AbstractValue.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @Override
    public String asString() throws Exception {
    	return getValue().toString();
    }
    
    @Override
    public Double asDouble() throws Exception {
    	throw new Exception("Not Implemented Yet");
    }

    @Override
    public Integer asInteger() throws Exception {
    	throw new Exception("Not Implemented Yet");
    }

    @Override
    public Boolean asBoolean() throws Exception {
    	throw new Exception("Not Implemented Yet");
    }
    
    protected <E extends Throwable> V getValueThrowing(Class<E> toThrow) throws E {
    	try {
    		return getValue();
    	}
    	catch(Exception e) {
    		try {
				E ex = toThrow.getConstructor(Throwable.class).newInstance(e);
				throw ex;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				throw new RuntimeException("Could not create new instance of " + toThrow.getCanonicalName() + ". Error: " + e1);
			}
    	}
    }
    
}
