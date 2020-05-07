/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import java.util.ArrayList;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

/**
 *
 * @author charlvj
 */
public class LogicalFunctionsRegister extends AbstractRegister {

    @Override
    public void registerAll() {
        reg("true", BooleanValue.TRUE);
        reg("false", BooleanValue.FALSE);
        reg(new GenericFunction3("if", "condition", "thencode", "elsecode") {
            @Override
            public Value execute(Value conditionValue, Value thenCodeValue, Value elseCodeValue) throws Exception {
                if(conditionValue instanceof ErrorValue)
                	return conditionValue;
            	BooleanValue condition = (BooleanValue) conditionValue;
                Value toExecute;
                if(condition.getValue()) {
                    toExecute = thenCodeValue;
                } else {
                    toExecute = elseCodeValue;
                }
                
                return runtime.callFunction.execute(toExecute, new ListValue(new ArrayList<>()));
            }
        });
        reg(new GenericFunction2("eq", "param1", "param2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
            	try {
	                if(param1 == param2) return BooleanValue.TRUE;
	                if(param1 == null || param2 == null) return BooleanValue.FALSE;
	                if(param1.compareTo(param2) == 0) return BooleanValue.TRUE;
	                return BooleanValue.FALSE;
            	}
            	catch(NotComparableException e) {
            		return Value.of(ErrorFactory.createError("Can not compare " + param1 + " with " + param2));
            	}
            }
        });
        reg(new GenericFunction1("not", "param") {
            @Override
            public Value execute(Value param) throws Exception {
                BooleanValue b = (BooleanValue) param;
                return b.not();
            }
        });
        reg(new GenericFunction2("lt", "p1", "p2") {
            @Override
            public Value execute(Value p1, Value p2) throws Exception {
            	try {
            		return BooleanValue.valueOf(p1.compareTo(p2) < 0);
            	}
            	catch(NotComparableException e) {
            		return Value.of(ErrorFactory.createError("Can not compare " + p1 + " with " + p2));
            	}
            }
        });
        reg(new GenericFunction2("gt", "p1", "p2") {
            @Override
            public Value execute(Value p1, Value p2) throws Exception {
            	try {
            		return BooleanValue.valueOf(p1.compareTo(p2) > 0);
            	}
            	catch(NotComparableException e) {
            		return Value.of(ErrorFactory.createError("Can not compare " + p1 + " with " + p2));
            	}
            }
        });
    }
    
}
