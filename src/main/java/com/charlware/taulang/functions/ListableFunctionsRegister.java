/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Listable;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

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
                    return NumberValue.valueOf(listable.size());
                }
                return NumberValue.valueOf(0);
            }
        });
        reg(new GenericFunction2("getelem", "list", "index") {
            @Override
            public Value execute(Value list, Value indexValue) throws Exception {
            	int index = indexValue.asInteger();
                if(list instanceof Listable) {
                    Listable listable = (Listable) list;
                    return (Value) listable.get(index);
                }
                return new ErrorValue(ErrorFactory.createError("Index out of bounds: " + index));
            }
        });
//        reg(new GenericFunction2("remove_elem", "list", "index") {
//        	@Override 
//        	public Value execute(Value listValue, Value indexValue) throws Exception {
//        		if(!(listValue instanceof ListValue)) {
//        			return new ErrorValue(ErrorFactory.createInvalidParamsError("List expected."));
//        		}
//        		if(!(indexValue instanceof NumberValue)) {
//        			return new ErrorValue(ErrorFactory.createInvalidParamsError("Number expected"));
//        		}
//        		Listable listable = (Listable) listValue;
//        		Integer index = indexValue.asInteger();
//        		listable.
//        	}
//        });
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
    }
}
