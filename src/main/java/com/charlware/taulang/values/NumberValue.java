/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.InvalidCastException;
import com.charlware.taulang.language.Token;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author charlvj
 */
public class NumberValue extends AbstractValue<Double> {
    
    public NumberValue(Token token) {
        super(token);
    }
    
    public NumberValue(Double value) {
        super(value);
    }
    
    public NumberValue(Integer value) {
        this(value.doubleValue());
    }
    
    @Override
    public String asString() throws Exception {
        return getValue().toString();
    }

    @Override
    public Double asNumber() throws Exception {
        return getValue();
    }
    
    @Override
    public Double processToken() {
        return NumberUtils.toDouble(token.getSource());
    }
}
