/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.TauError;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

/**
 *
 * @author charlvj
 */
public class ErrorValue extends AbstractValue<TauError> {

    public ErrorValue(TauError error) {
        super(error);
        realized = true;
    }
    
    public ErrorValue(String msg) {
        this(ErrorFactory.createError(msg));
    }
    
    @Override
    protected TauError processToken() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asString() throws Exception {
        return getValue().toString();
    }

    @Override
    public Integer asInteger() throws Exception {
        return getValue().getCode();
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof ErrorValue) {
            String s1 = getValueThrowing(NotComparableException.class).getMessage();
            String s2 = ((ErrorValue) o).getValueThrowing(NotComparableException.class).getMessage();
            return s1.compareTo(s2);
        }
        else {
        	throw new NotComparableException();
        }
    }
    
}