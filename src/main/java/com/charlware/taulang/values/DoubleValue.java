package com.charlware.taulang.values;

import java.util.logging.LogManager;

import org.apache.commons.lang3.math.NumberUtils;

import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.abilities.Divisible;
import com.charlware.taulang.values.abilities.Addable.NotAddableException;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;
import com.charlware.taulang.values.abilities.Subtractable.NotSubtractableException;

public class DoubleValue extends NumberValue<Double> 
	implements Divisible<Double>{
    public DoubleValue(Token token) {
        super(token);
    }
    
    public DoubleValue(Double value) {
        super(value);
    }
    
    @Override
    public String asString() throws Exception {
        return getValue().toString();
    }

    @Override
    public Double asDouble() throws Exception {
        return getValue();
    }
    
    @Override
    public Double processToken() {
        return NumberUtils.toDouble(token.getSource());
    }
    
    @Override
    public Integer asInteger() throws Exception {
    	return getValue().intValue();
    }

//    @Override
//    public boolean equals(Object obj) {
//        boolean eq = super.equals(obj);
//        if(!eq && obj instanceof NumberValue) {
//            try {
//                Double d1 = getValue();
//                Double d2 = ((DoubleValue) obj).asDouble();
//                Double epsilon = getInterpreter().getRuntime().getMemory().get("math_compare_default_epsilon").execute().asDouble();
//                eq = Math.abs(d1 - d2) < epsilon;
//            } catch(Exception e) {
//                eq = false;
//            }
//        }
//        return eq;
//    }

    @Override
    public int compareTo(Value o) throws NotComparableException {
    	Double d1;
    	Double d2;
        if(o instanceof DoubleValue) {
            d1 = getValueThrowing(NotComparableException.class);
            d2 = ((DoubleValue) o).getValueThrowing(NotComparableException.class);
        }
        else if(o instanceof IntegerValue) {
            d1 = getValueThrowing(NotComparableException.class);
            d2 = ((IntegerValue) o).getValueThrowing(NotComparableException.class).doubleValue();
        }
        else {
        	throw new NotComparableException();
        }
        double c = d1 - d2;
        if(Math.abs(c) < 0.00000001)
        	return 0;
        else if(c < 0)
        	return -1;
        else
        	return 1;
    }

    @Override
    public Double add(Value x) throws NotAddableException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		Double thisValue;
    		try {
    			thisValue = getValue();
    		}
    		catch(Exception e) {
    			System.out.println("Unexpected error: " + e);
    			return null;
    		}
    		
    		// Addition can result in a Double iff double + double or double + int.
    		try {
    			Double xDouble = x.asDouble();
	    		return thisValue + xDouble;
    		} catch(Exception e) {
    			// Do Nothing
    		}
    		try {
    			Integer xInt = x.asInteger();
    			return thisValue + xInt.doubleValue();
    		}
    		catch(Exception e) {
    			throw new NotAddableException();
    		}
    	}
    }
    
    @Override
    public Double subtract(Value x) throws NotSubtractableException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		Double thisValue;
    		try {
    			thisValue = getValue();
    		}
    		catch(Exception e) {
    			System.out.println("Unexpected error: " + e);
    			return null;
    		}
    		
    		// Addition can result in a Double iff double + double or double + int.
    		try {
    			Double xDouble = x.asDouble();
	    		return thisValue - xDouble;
    		} catch(Exception e) {
    			// Do Nothing
    		}
    		try {
    			Integer xInt = x.asInteger();
    			return thisValue - xInt.doubleValue();
    		}
    		catch(Exception e) {
    			throw new NotSubtractableException();
    		}
    	}
    }
    
    @Override
    public Double multiply(Value x) throws NotMultiplyableException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		Double thisValue;
    		try {
    			thisValue = getValue();
    		}
    		catch(Exception e) {
    			System.out.println("Unexpected error: " + e);
    			return null;
    		}
    		
    		try {
    			Double xDouble = x.asDouble();
	    		return thisValue * xDouble;
    		} catch(Exception e) {
    			// Do Nothing
    		}
    		try {
    			Integer xInt = x.asInteger();
    			return thisValue * xInt.doubleValue();
    		}
    		catch(Exception e) {
    			throw new NotMultiplyableException();
    		}
    	}
    }

    @Override
    public Double divide(Value x) throws NotDivisibleException {
    	if(x == null || x == NullValue.NULL) 
    		return null;
    	else {
    		Double thisValue;
    		try {
    			thisValue = getValue();
    		}
    		catch(Exception e) {
    			System.out.println("Unexpected error: " + e);
    			return null;
    		}
    		
    		// Addition can result in a Double iff double + double or double + int.
    		try {
    			Double xDouble = x.asDouble();
	    		return thisValue / xDouble;
    		} catch(Exception e) {
    			// Do Nothing
    		}
    		try {
    			Integer xInt = x.asInteger();
    			return thisValue / xInt.doubleValue();
    		}
    		catch(Exception e) {
    			throw new NotDivisibleException();
    		}
    	}
    }
}
