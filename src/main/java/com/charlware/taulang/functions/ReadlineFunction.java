/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.StringValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author charlvj
 */
public class ReadlineFunction extends Function {
    BufferedReader reader = null;
    
    public ReadlineFunction() {
        name = "readline";
        noParams();
    }

    protected BufferedReader reader() {
        if(reader == null) {
            reader = new BufferedReader(new InputStreamReader(runtime.stdin));
        }
        return reader;
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        String s = reader().readLine();
        return new StringValue(s);
    }

}
