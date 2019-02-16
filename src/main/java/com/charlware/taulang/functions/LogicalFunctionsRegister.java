/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;
import java.util.ArrayList;

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
                if(param1 == param2) return BooleanValue.TRUE;
                if(param1 == null || param2 == null) return BooleanValue.FALSE;
                if(param1.equals(param2)) return BooleanValue.TRUE;
                return BooleanValue.FALSE;
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
                return p1.asNumber() < p2.asNumber() ? BooleanValue.TRUE : BooleanValue.FALSE;
            }
        });
        reg(new GenericFunction2("gt", "p1", "p2") {
            @Override
            public Value execute(Value p1, Value p2) throws Exception {
                return p1.asNumber() > p2.asNumber() ? BooleanValue.TRUE : BooleanValue.FALSE;
            }
        });
    }
    
}
