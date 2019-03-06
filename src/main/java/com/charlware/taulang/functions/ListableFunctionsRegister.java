/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Listable;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.Value;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class ListableFunctionsRegister extends AbstractRegister {
    @Override
    public void registerAll() {
        reg(new GenericFunction1("listsize", "list") {
            @Override
            public Value execute(Value list) throws Exception {
                if(list instanceof Listable) {
                    Listable listable = (Listable) list;
                    return new NumberValue(new Double(listable.size()));
                }
                return new NumberValue(Double.valueOf(0.0));
            }
        });
        reg(new GenericFunction2("getelem", "list", "index") {
            @Override
            public Value execute(Value list, Value index) throws Exception {
                if(list instanceof Listable) {
                    Listable listable = (Listable) list;
                    return (Value) listable.get(index.asNumber().intValue());
                }
                return new NumberValue(Double.valueOf(0.0));
            }
        });
        reg(new GenericFunction2("range", "start", "end") {
            @Override
            public Value execute(Value start, Value end) throws Exception {
                List<Value> list = new ArrayList<>();
                for(int i = start.asInteger(); i <= end.asInteger(); i++) {
                    list.add(new NumberValue(Double.valueOf(i)));
                }
                return new ListValue(list);
            }
            
        });
//        reg(new GenericFunction3("foreach1", "list", "varname", "code") {
//            @Override
//            public Value execute(Value list, Value varname, Value code) throws Exception {
//                if(list instanceof Listable) {
//                    ListValue listValue = (ListValue) list;
//                    ListValue codeList = (ListValue) code;
//                    ListToken codeTokens = codeList.getListToken();
//                    Value result = null;
//                    MemoryScope memory = runtime.getMemory();
//                    for(Value value: listValue.getValue()) {
//                        memory.push();
//                        runtime.register(varname.asString(), value);
//                        result = runtime.getInterpreter().eval(codeTokens.iterator());
//                    }
//                    return result;
//                }
//                return new NumberValue(Double.valueOf(0.0));
//            }
//        });
    }
}
