/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class ExposeFunction extends Function {
    public ExposeFunction() {
        name = "expose";
        params = new String[] {"functionName"};
    }
    
    @Override
    public Value execute(Value[] params) throws Exception {
        String functionName = params[0].asString();
        MemoryScope memory = runtime.getMemory().getCurrentScope();
        boolean res = memory.exportToImportSource(functionName);
        return BooleanValue.valueOf(res);
    }
}
