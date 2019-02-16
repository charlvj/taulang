/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.language.Token;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlvj
 */
public abstract class AbstractValue<V> implements Value<V> {
    protected final Token token;
    private V value;
    protected boolean realized = false;
    protected Interpreter interpreter;
    
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
    public String getType() {
        return token.getType().toString();
    }
    
    @Override
    public Integer asInteger() throws Exception {
        return asNumber().intValue();
    }
    
    protected abstract V processToken() throws Exception;
    
    @Override
    public Value realize() throws Exception {
        if(!realized) {
            value = processToken();
            realized = true;
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
    
    
}
