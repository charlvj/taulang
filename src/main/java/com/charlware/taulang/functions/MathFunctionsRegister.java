/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.Value;
import java.util.Arrays;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 *
 * @author charlvj
 */
public class MathFunctionsRegister extends AbstractRegister {
    @Override
    public void registerAll() {
        runtime.register("pi", new NumberValue(Math.PI));
        
        reg(new GenericFunction2("plus", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
                return new NumberValue(param1.asNumber() + param2.asNumber());
            }
        });
        reg(new GenericFunction2("minus", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
                return new NumberValue(param1.asNumber() - param2.asNumber());
            }
        });
        reg(new GenericFunction2("mult", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
                return new NumberValue(param1.asNumber() * param2.asNumber());
            }
        });
        reg(new GenericFunction2("div", "value1", "value2") {
            @Override
            public Value execute(Value param1, Value param2) throws Exception {
                return new NumberValue(param1.asNumber() / param2.asNumber());
            }
        });
//        reg(new GenericFunction1("sin", "x") {
//            @Override
//            public Value execute(Value x) throws Exception {
//                return new NumberValue(Math.sin(x.asNumber()));
//            }
//        });
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
                               d = (Double) MethodUtils.invokeStaticMethod(Math.class, fname, x.asNumber());
                               return new NumberValue(d);
                           } catch (Exception ex) {
                               return new ErrorValue(ErrorFactory.createError("Error executing " + fname + " : " + ex));
                           }
                       }
                    });
                });
        
        reg(new GenericFunction2("atan2", "x", "y") {
            @Override
            public Value execute(Value x, Value y) throws Exception {
                return new NumberValue(Math.atan2(x.asNumber(), y.asNumber()));
            }
        });
    }
}
