/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.abilities.Addable;
import com.charlware.taulang.values.abilities.Multiplyable;
import com.charlware.taulang.values.abilities.Subtractable;

/**
 *
 * @author charlvj
 */
public abstract class NumberValue<NumberType> extends AbstractValue<NumberType> 
	implements Addable<NumberType>, 
			   Subtractable<NumberType>,
			   Multiplyable<NumberType> {
    
	public static IntegerValue valueOf(Integer i) {
		return new IntegerValue(i);
	}
	
	public static DoubleValue valueOf(Double d) {
		return new DoubleValue(d);
	}
	
    public NumberValue(Token token) {
        super(token);
    }
    
    public NumberValue(NumberType value) {
        super(value);
    }
    
    @Override
    public String asString() throws Exception {
        return getValue().toString();
    }
}
