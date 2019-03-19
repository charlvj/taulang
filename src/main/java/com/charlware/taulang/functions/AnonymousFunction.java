/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.Memory;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class AnonymousFunction extends DefinedFunction {
    static int seq = 0;

    private ListToken code;

    public AnonymousFunction(String[] funcParamArr, ListValue codeListValue) {
        this(funcParamArr, codeListValue.getListToken());
    }
    
    public AnonymousFunction(String[] funcParamArr, ListToken code) {
        super("__lambda_" + seq++, funcParamArr, code);
    }

}
