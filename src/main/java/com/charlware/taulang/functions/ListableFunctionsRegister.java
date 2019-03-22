/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Listable;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author charlvj
 */
public class ListableFunctionsRegister extends AbstractRegister {
    @Override
    public void registerAll() {
        reg(new GenericFunction1("size", "list") {
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
        reg(new GenericFunction2("concat", "list1", "list2") {
            @Override
            public Value execute(Value list1, Value list2) throws Exception {
                List<Value> l1 = ((ListValue) list1).getValue();
                List<Value> l2 = ((ListValue) list2).getValue();
                List<Value> newList = new ArrayList(l1.size() + l2.size());
                newList.addAll(l1);
                newList.addAll(l2);
                return new ListValue(newList);
            }
        });
        reg(new GenericFunction2("join", "list", "chars") {
            @Override
            public Value execute(Value list, Value chars) throws Exception {
                List<Value> l1 = ((ListValue) list).getValue();
                String c = chars.asString();
                String result = l1.stream()
                        .map(v -> {
                            String s;
                            try {
                                s = v.asString();
                            } catch(Exception e) {
                                s = "";
                            }
                            return s;
                        }
                        )
                        .collect(Collectors.joining(c));
                return new StringValue(result);
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
