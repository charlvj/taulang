/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.Value;

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
        
    }
}
