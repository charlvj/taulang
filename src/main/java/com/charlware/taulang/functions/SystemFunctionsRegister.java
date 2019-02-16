/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.Memory;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.Listable;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.ObjectValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.SymbolValue;
import com.charlware.taulang.values.Value;
import java.util.Map;

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
        
        reg(new GenericFunction0("system.memory.dump") {
            @Override
            public Value execute() throws Exception {
                Memory memory = runtime.getMemory();
                for(Map<String,Function> hash: memory) {
                    for(Map.Entry entry: hash.entrySet()) {
                        System.out.print(entry.getKey() + "  ");
                    }
                    System.out.println();
                }
                return null;
            }
            
        });
        
        reg(new GenericFunction2("alias", "aliasName", "existingFunction") {
            @Override
            public Value execute(Value aliasName, Value existingFunc) throws Exception {
                Memory memory = runtime.getMemory();
                Function func = memory.get(existingFunc.asString());
                memory.put(aliasName.asString(), func);
                return aliasName;
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
    
    public void registerTypes() {
        reg(new GenericFunction1("is_number", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof NumberValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_logical", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof BooleanValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_list", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof Listable ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_string", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof StringValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_symbol", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof SymbolValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_null", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof NullValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_function", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof FunctionValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_object", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof ObjectValue ? trueValue() : falseValue());
            }
        });
    }
}
