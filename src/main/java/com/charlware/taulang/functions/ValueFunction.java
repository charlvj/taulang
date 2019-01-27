/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class ValueFunction extends Function {
    private final Value value;
    public ValueFunction(String name, Value value) {
        super.name = name;
        this.value = value;
        noParams();
    }

    @Override
    public Value execute(Value[] params) {
        return value;
    }
}
