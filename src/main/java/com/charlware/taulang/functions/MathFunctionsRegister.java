/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import java.util.Arrays;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.DoubleValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.IntegerValue;
import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.abilities.Addable;
import com.charlware.taulang.values.abilities.Addable.NotAddableException;
import com.charlware.taulang.values.abilities.Divisible;
import com.charlware.taulang.values.abilities.Divisible.NotDivisibleException;
import com.charlware.taulang.values.abilities.Multiplyable;
import com.charlware.taulang.values.abilities.Multiplyable.NotMultiplyableException;
import com.charlware.taulang.values.abilities.Subtractable;
import com.charlware.taulang.values.abilities.Subtractable.NotSubtractableException;

/**
 *
 * @author charlvj
 */
public class MathFunctionsRegister extends AbstractRegister {
    @Override
    public void registerAll() {
        runtime.register("pi", new DoubleValue(Math.PI));
        runtime.register("math_compare_default_epsilon", new DoubleValue(0.00001));
        
        reg(new GenericFunction2("plus", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
            	try {
	            	Value result = null;
	            	if(param1 instanceof Addable) {
	            		try {
	            			Addable p1 = (Addable) param1;
	            			result = Value.of(p1.add(param2));
	            		}
	            		catch(NotAddableException nae) {
	            			// leave result null
	            		}
	            	}
	            	if(result == null && param2 instanceof Addable) {
	            		Addable p2 = (Addable) param2;
	            		result = Value.of(p2.add(param1));
	            	}
	            	if(result == null)
	            		throw new NotAddableException();
	            	else
	            		return result;
            	}
            	catch(NotAddableException nae) {
            		return new ErrorValue(ErrorFactory.createInvalidParamsError("Two values cannot be added: " + param1.asString() + " + " + param2.asString()));
            	}
            }
        });
        alias("add", "plus");
        reg(new GenericFunction2("minus", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
            	try {
	            	Value result = null;
	            	if(param1 instanceof Subtractable) {
	            		try {
	            			Subtractable p1 = (Subtractable) param1;
	            			result = Value.of(p1.subtract(param2));
	            		} 
	            		catch(NotSubtractableException nse) {
	            			// leave result null
	            		}
	            	}
	            	if(result == null && param2 instanceof Addable) {
	            		Subtractable p2 = (Subtractable) param2;
	            		result = Value.of(p2.subtract(param1));
	            	}
	            	if(result == null) 
	            		throw new NotSubtractableException();
	            	else
	            		return result;
            	}
            	catch(NotSubtractableException nae) {
            		return new ErrorValue(ErrorFactory.createInvalidParamsError("Two values cannot be subtracted: " + param1.asString() + " - " + param2.asString()));
            	}
            }
        });
        reg(new GenericFunction2("mult", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
            	try {
	            	Value result = null;
	            	if(param1 instanceof Multiplyable) {
	            		try {
	            			Multiplyable p1 = (Multiplyable) param1;
	            			result = Value.of(p1.multiply(param2));
	            		} 
	            		catch(NotMultiplyableException nse) {
	            			// leave result null
	            		}
	            	}
	            	if(result == null && param2 instanceof Addable) {
	            		Multiplyable p2 = (Multiplyable) param2;
	            		result = Value.of(p2.multiply(param1));
	            	}
	            	if(result == null) 
	            		throw new NotMultiplyableException();
	            	else
	            		return result;
            	}
            	catch(NotMultiplyableException nae) {
            		return new ErrorValue(ErrorFactory.createInvalidParamsError("Two values cannot be multiplied: " + param1.asString() + " - " + param2.asString()));
            	}
            }
        });
        reg(new GenericFunction2("div", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
            	try {
	            	Value result = null;
	            	if(param1 instanceof Divisible) {
	            		try {
	            			Divisible p1 = (Divisible) param1;
	            			result = Value.of(p1.divide(param2));
	            		} 
	            		catch(NotDivisibleException nse) {
	            			// leave result null
	            		}
	            	}
	            	// division is one-way
	            	
	            	if(result == null) 
	            		throw new NotDivisibleException();
	            	else
	            		return result;
            	}
            	catch(NotDivisibleException nae) {
            		return new ErrorValue(ErrorFactory.createInvalidParamsError("Two values cannot be divided: " + param1.asString() + " - " + param2.asString()));
            	}
            }
        });
        reg(new GenericFunction1("round", "x") {
            @Override
            public Value execute(Value x) throws Exception {
                return new IntegerValue((int) Math.round(x.asDouble()));
            }
        });
        registerMathClass();
    }
    
    private void registerMathClass() {
        Arrays.asList("sin", "cos", "tan", 
                "sinh", "cosh", "tanh",
                "asin", "acos", "atan",
                "log", "log10",
                "toDegrees", "toRadians",
                "sqrt")
                .stream()
                .forEach(fname -> {
                    reg(new GenericFunction1(fname, "x") {
                       @Override
                       public Value execute(Value x) {
                           Double d;
                           try {
                               d = (Double) MethodUtils.invokeStaticMethod(Math.class, fname, x.asDouble());
                               return new DoubleValue(d);
                           } catch (Exception ex) {
                               return new ErrorValue(ErrorFactory.createError("Error executing " + fname + " : " + ex));
                           }
                       }
                    });
                });
        
        reg(new GenericFunction2("atan2", "x", "y") {
            @Override
            public Value execute(Value x, Value y) throws Exception {
                return new DoubleValue(Math.atan2(x.asDouble(), y.asDouble()));
            }
        });
    }
}
