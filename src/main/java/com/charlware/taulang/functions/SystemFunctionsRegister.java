/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class SystemFunctionsRegister extends AbstractRegister {
    
    
    @Override
    public void registerAll() {
        reg(new ExposeFunction());
        reg(new CallFunction());
        reg(new LambdaFunction());
//        reg(new GenericFunction2("call" , "callable") {
//            @Override
//            public Value execute(Value callable) throws Exception {
//                if(callable instanceof ListValue) {
//                    ListToken listToken = ((ListValue) callable).getListToken();
//                    return runtime.getInterpreter().eval(listToken.iterator());
//                }
//                return null;
//            }
//        });
        reg(new GenericFunction0("system.memory.currentcontext") {
            @Override
            public Value execute() throws Exception {
                return new StringValue(runtime.getMemory().getCurrentContext());
            }
        });
//        reg(new GenericFunction2("getelem", "list", "index") {
//            @Override
//            public Value execute(Value list, Value index) throws Exception {
//                if (list instanceof Listable) {
//                    Listable listable = (Listable) list;
//                    return (Value) listable.get(index.asNumber().intValue());
//                }
//                return new NumberValue(Double.valueOf(0.0));
//            }
//        });
    }
}
