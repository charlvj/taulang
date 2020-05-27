/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.functions.streams.RandomStream;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.DoubleValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.IntegerValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Listable;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.ObjectValue;
import com.charlware.taulang.values.StreamValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.SymbolValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class SystemFunctionsRegister extends AbstractRegister {
    
	@Override
    public void registerAll() {
    	reg("randomizer", new StreamValue(new RandomStream()));
        reg(new ExposeFunction());
        reg(new CallFunction());
        reg(new LambdaFunction());
        reg(new GenericFunction2("alias", "aliasName", "existingFunction") {
            @Override
            public Value execute(Value aliasName, Value existingFunc) throws Exception {
                MemoryScope memory = runtime.getMemory().getCurrentScope();
                Function func = memory.get(existingFunc.asString());
                memory.put(aliasName.asString(), func);
                return aliasName;
            }
        });

		registerMeta();
        registerTypes();
        registerControl();
    }
	
	public void registerMeta() {
		reg(new GenericFunction0("dev.breakpoint") {
			@Override
			public Value execute() throws Exception {
				return BooleanValue.TRUE;
			}
		});
		reg(new GenericFunction1("is_defined", "function_name") {
			@Override
			public Value execute(Value functionName) throws Exception {
				String name = functionName.asString();
				Function f = runtime.getMemory().getCurrentScope().get(name);
				return BooleanValue.valueOf(f != null);
			}
		});
		reg(new GenericFunction0("meta.all_functions") {
			@Override
			public Value execute() throws Exception {
				MemoryScope scope = runtime.getMemory().getCurrentScope();
				SortedSet<String> keys = new TreeSet<>();
				while(scope != null) {
					keys.addAll(scope.keySet());
					scope = scope.getParent();
				}
				return new ListValue(
						keys.stream()
						.map(k -> Value.of(k))
						.collect(Collectors.toList()));
			}
		});
		reg(new GenericFunction1("meta.info", "function_name") {
			@Override
			public Value execute(Value functionName) throws Exception {
				String name = functionName.asString();
				Function f = runtime.getMemory().getCurrentScope().get(name);
				if(f == null) {
					return NullValue.NULL;
				}
				List<Value> info = new ArrayList<>();
				info.add(Value.of(f.getName()));
				for(String s: f.getParams()) {
					info.add(Value.of(s));
				}
				return new ListValue(info);
			}
		});
	}
    
    public void registerTypes() {
        reg(new GenericFunction1("is_number", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof NumberValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_integer", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof IntegerValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("is_double", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof DoubleValue ? trueValue() : falseValue());
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
        
        reg(new GenericFunction1("is_stream", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return (value instanceof StreamValue ? trueValue() : falseValue());
            }
        });
        
        reg(new GenericFunction1("as_string", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return new StringValue(value.asString());
            }
        });
        
        reg(new GenericFunction1("as_integer", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return new IntegerValue(value.asInteger());
            }
        });
        
        reg(new GenericFunction1("as_double", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return new DoubleValue(value.asDouble());
            }
        });
        
        reg(new GenericFunction1("as_boolean", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return new BooleanValue(value.asBoolean());
            }
        });
    }
    
    private void registerControl() {
    	reg(new WhileFunction());
    }
}
