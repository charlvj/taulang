/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.Symbol;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author charlvj
 */
public class SymbolValue extends AbstractValue<Symbol> {
    
    public SymbolValue(Token token) {
        super(token);
    }
    
    public SymbolValue(Symbol value) {
        super(value);
    }
    
    @Override
    public String asString() throws Exception {
        return getValue().getStringValue();
    }

    @Override
    public Integer asInteger() throws Exception {
        return getValue().getIntValue();
    }
    
    @Override
    public Symbol processToken() {
        return Symbol.of(token.getSource());
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof SymbolValue) {
            String s1 = getValueThrowing(NotComparableException.class).getStringValue();
            String s2 = ((SymbolValue) o).getValueThrowing(NotComparableException.class).getStringValue();
            return s1.compareTo(s2);
        }
        else {
        	throw new NotComparableException();
        }
    }
}
