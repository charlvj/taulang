/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.Symbol;
import com.charlware.taulang.language.Token;
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
    public Double asNumber() throws Exception {
        return Double.valueOf(getValue().getIntValue());
    }
    
    @Override
    public Symbol processToken() {
        return new Symbol(token.getSource());
    }
}
