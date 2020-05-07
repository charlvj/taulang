/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.InvalidCastException;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

/**
 *
 * @author charlvj
 */
public class FunctionValue extends AbstractValue<Function> {

    public FunctionValue(Function function) {
        super(function);
    }

    @Override
    protected Function processToken() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asString() throws Exception {
        return getValue().getName();
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof FunctionValue) {
            String s1 = getValueThrowing(NotComparableException.class).getName();
            String s2 = ((FunctionValue) o).getValueThrowing(NotComparableException.class).getName();
            return s1.compareTo(s2);
        }
        else {
        	throw new NotComparableException();
        }
    }
}
