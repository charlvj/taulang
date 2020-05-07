/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import org.apache.commons.lang3.math.NumberUtils;

import com.charlware.taulang.language.InvalidCastException;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.abilities.Addable;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

/**
 *
 * @author charlvj
 */
public class StringValue extends AbstractValue<String> implements Listable<String>, Addable<String> {
    public StringValue(Token token) {
        super(token);
    }

    public StringValue(String value) {
        super(value);
    }
    
    protected String processToken() throws Exception {
        return token.getSource();
    }
    
    @Override
    public String asString() throws Exception {
        return getValue();
    }

    @Override
    public Double asDouble() throws Exception {
        String value = getValue();
        if(NumberUtils.isCreatable(value)) {
            return NumberUtils.toDouble(value);
        }
        throw new InvalidCastException(value, "Double");
    }
    
    @Override
    public Integer asInteger() throws Exception {
        String value = getValue();
        if(NumberUtils.isCreatable(value)) {
            return NumberUtils.toInt(value);
        }
        throw new InvalidCastException(value, "Integer");
    }

    @Override
    public int size() {
        try {
            return getValue().length();
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public String get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set(int index, String elem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String add(Value x) throws NotAddableException {
    	if(x == NullValue.NULL) 
    		return null;
    	
    	try {
    		String s = getValue();
    		return s + x.asString();
    	}
    	catch(Exception e) {
    		System.out.println("Unexpected error: " + e);
    	}
		return null;
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof StringValue) {
            String s1 = getValueThrowing(NotComparableException.class);
            String s2 = ((StringValue) o).getValueThrowing(NotComparableException.class);
            return s1.compareTo(s2);
        }
        else {
        	throw new NotComparableException();
        }
    }
}
