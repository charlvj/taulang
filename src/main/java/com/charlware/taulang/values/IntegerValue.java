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
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof IntegerValue) {
            Integer d1 = getValueThrowing(NotComparableException.class);
            Integer d2 = ((IntegerValue) o).getValueThrowing(NotComparableException.class);
            return d1.compareTo(d2);
        }
        else if(o instanceof DoubleValue) {
        	Double d1 = getValueThrowing(NotComparableException.class).doubleValue();
            Double d2 = ((DoubleValue) o).getValueThrowing(NotComparableException.class);
            return d1.compareTo(d2);
        }
        else {
        	throw new NotComparableException();
        }
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
    	else if(x instanceof IntegerValue) {
    		try {
    			Integer thisValue = getValue();
	    		Integer xInt = x.asInteger();
	    		return thisValue * xInt;
    		} catch(Exception e) {
    			// Integer multiplication is the only thing that produces an integer.
    			throw new NotMultiplyableException();
    		}
    	}
    	else
    		throw new NotMultiplyableException();
    }

	@Override
	public Double asDouble() throws Exception {
		return Double.valueOf(getValue());
	}

	@Override
	public Integer divide(Value x) throws NotDivisibleException {
		if(x == null || x == NullValue.NULL) 
    		return null;
    	else if(x instanceof IntegerValue) {
    		try {
    			Integer thisValue = getValue();
	    		Integer xInt = x.asInteger();
	    		return thisValue / xInt;
    		} catch(Exception e) {
    			// Integer multiplication is the only thing that produces an integer.
    			throw new NotDivisibleException();
    		}
    	}
    	else
    		throw new NotDivisibleException();
	}
}
