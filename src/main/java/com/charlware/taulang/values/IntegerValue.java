package com.charlware.taulang.values;

import org.apache.commons.lang3.math.NumberUtils;

import com.charlware.taulang.language.Token;

public class IntegerValue extends NumberValue<Integer> {
    public IntegerValue(Token token) {
        super(token);
    }
    
    public IntegerValue(Integer value) {
        super(value);
    }
    
    @Override
    public String asString() throws Exception {
        return getValue().toString();
    }

    @Override
    public Integer asInteger() throws Exception {
        return getValue();
    }
    
    @Override
    public Integer processToken() {
        return (int) Math.round(NumberUtils.toDouble(token.getSource()));
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = super.equals(obj);
        if(!eq && obj instanceof IntegerValue) {
            try {
                Integer d1 = asInteger();
                Integer d2 = ((IntegerValue) obj).asInteger();
                eq = d1.equals(d2);
            } catch(Exception e) {
                eq = false;
            }
        }
        return eq;
    }

    @Override
    public Integer add(Value x) throws NotAddableException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		if(x instanceof IntegerValue) {
	    		try {
	    			Integer thisValue = getValue();
		    		Integer xInt = x.asInteger();
		    		return thisValue + xInt;
	    		} catch(Exception e) {
	    			// Integer addition is the only thing that produces an integer.
	    			throw new NotAddableException();
	    		}
    		} 
    		else {
    			throw new NotAddableException();
    		}
    	}
    }
    
    @Override
    public Integer subtract(Value x) throws NotSubtractableException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		try {
    			Integer thisValue = getValue();
	    		Integer xInt = x.asInteger();
	    		return thisValue - xInt;
    		} catch(Exception e) {
    			// Integer addition is the only thing that produces an integer.
    			throw new NotSubtractableException();
    		}
    	}
    }
    
    @Override
    public Integer multiply(Value x) throws NotMultiplyableException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		try {
    			Integer thisValue = getValue();
	    		Integer xInt = x.asInteger();
	    		return thisValue * xInt;
    		} catch(Exception e) {
    			// Integer multiplication is the only thing that produces an integer.
    			throw new NotMultiplyableException();
    		}
    	}
    }

	@Override
	public Double asDouble() throws Exception {
		return Double.valueOf(getValue());
	}
}
