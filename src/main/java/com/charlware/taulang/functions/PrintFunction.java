/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.ListValue;

/**
 *
 * @author charlvj
 */
public class PrintFunction extends Function {

    public PrintFunction() {
        name = "print";
        params = new String[] { "message" };
    }

    @Override
    public Value execute(Value[] params) throws Exception {
        Value param = params[0];
        String msg;
        if(param == null) {
            msg = "<null>";
        } if(param instanceof ListValue) {
            ListValue listValue = (ListValue) param;
            StringBuilder sb = new StringBuilder();
            for(Value value: listValue.getValue()) {
                if(value == null) sb.append("<null>");
                else sb.append(value.asString());
            }
            msg = sb.toString();
        } else {
            msg = param.asString();
        }
        runtime.stdout.print(msg);
        runtime.stdout.flush();
        return null;
    }
    
}
