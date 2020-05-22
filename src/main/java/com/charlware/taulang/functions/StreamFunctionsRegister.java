/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.DefinedStream;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.FileInputStream;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.GenericStream;
import com.charlware.taulang.language.IStream;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.IntegerValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.StreamValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class StreamFunctionsRegister extends AbstractRegister {

    @Override
    public void registerAll() {
    	reg(new GenericFunction1("has_next", "stream") {
    		@Override
    		public Value execute(Value streamValue) throws Exception {
    			if(!(streamValue instanceof StreamValue)) {
    				return new ErrorValue(ErrorFactory.createInvalidParamsError("has_next: Expected a Stream value"));
    			}
    			return BooleanValue.valueOf(((StreamValue)streamValue).getValue().hasNext());
    		}
    	});
    	
    	reg(new GenericFunction1("next", "stream") {
    		@Override
    		public Value execute(Value streamValue) throws Exception {
    			if(!(streamValue instanceof StreamValue)) {
    				return new ErrorValue(ErrorFactory.createInvalidParamsError("next: Expected a Stream value"));
    			}
    			return ((StreamValue)streamValue).getValue().next();
    		}
    	});
    	
    	reg(new GenericFunction2("next_n", "stream", "count") {
    		@Override
    		public Value execute(Value streamValue, Value countValue) throws Exception {
    			if(!(streamValue instanceof StreamValue)) {
    				return new ErrorValue(ErrorFactory.createInvalidParamsError("next_n: Expected a Stream value"));
    			}
    			int count = 0;
    			boolean readAll = false;
    			if(countValue == NullValue.NULL) 
    				readAll = true;
    			else if(countValue instanceof IntegerValue) 
    				count = countValue.asInteger();
    			else
    				return new ErrorValue(ErrorFactory.createInvalidParamsError("next_n: Expected an integer for count"));
    			
    			List<Value> a = new ArrayList<>(readAll ? 10 : count);
    			if(count > 0) {
	    			IStream stream = ((StreamValue)streamValue).getValue();
	    			
	    			int i = 0;
	    			while(stream.hasNext()) {
	    				if(!readAll && i >= count)
	    					break;
	    				a.add(stream.next());
	    				i++;
	    			}
    			}
    			return new ListValue(a);
    		}
    	});
    	
    	reg(new GenericFunction2("write", "stream", "value") {
    		@Override
    		public Value execute(Value streamValue, Value value) throws Exception {
    			if(!(streamValue instanceof StreamValue)) {
    				return new ErrorValue(ErrorFactory.createInvalidParamsError("write: Expected a Stream value"));
    			}
    			((StreamValue) streamValue).getValue().write(value);
    			return streamValue;
    		}
    	});
    	
        reg(new GenericFunction1("is_stream", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return BooleanValue.valueOf(value instanceof StreamValue);
            }
            
        });
        
        reg(new GenericFunction0("new_stream") {
			@Override
			public Value execute() throws Exception {
				StreamValue value = new StreamValue(new GenericStream());
				return value;
			}
        });
        
        reg(new GenericFunction3("new_stream_source", "initial_state", "has_next_callable", "next_callable") {
            @Override
            public Value execute(Value initialStateValue, Value hasNextFunctionValue, Value nextFunctionValue) throws Exception {
                if(!(initialStateValue instanceof ListValue)) {
                    return new ErrorValue(ErrorFactory.createInvalidParamsError("Initial State is expected to be a list."));
                }
                ListValue initialState = (ListValue) initialStateValue;
                
//                String functionName = hasNextFunctionValue.asString();
//                Function hasNextFunction = getMemory().get(functionName);
//                
//                functionName = nextFunctionValue.asString();
//                Function nextFunction = getMemory().get(functionName);
                DefinedStream stream = new DefinedStream(runtime, initialState, hasNextFunctionValue, nextFunctionValue);
                return new StreamValue(stream);
            }
        });
        
        reg(new GenericFunction3("collect", "initial_state", "code", "stream") {
        	@Override
        	public Value execute(Value initialStateValue, Value codeValue, Value streamValue) throws Exception {
        		if(!(streamValue instanceof StreamValue)) {
        			return new ErrorValue(ErrorFactory.createInvalidParamsError("collect expects a stream as the third parameter"));
        		}
        		IStream stream = ((StreamValue) streamValue).getValue();
        		Value state = initialStateValue;
        		while(!(state instanceof ErrorValue) && stream.hasNext()) {
        			Value params = new ListValue(List.of(state, stream.next()));
        			state = runtime.callFunction.execute(codeValue, params);
        		}
        		return state;
        	}
        });
        
//        reg(new GenericFunction2("for_each", "stream", "proc") {
//            @Override
//            public Value execute(Value streamValue, Value procValue) throws Exception {
//                if(streamValue instanceof ErrorValue)
//                    return streamValue;
//                
//                if(!(streamValue instanceof StreamValue)) {
//                    return new ErrorValue(ErrorFactory.createInvalidParamsError("for_each expects a stream as a first parameter."));
//                }
//                IStream stream = ((StreamValue) streamValue).getValue();
//                
//                Function function = null;
//                if(procValue instanceof FunctionValue) {
//                    function = ((FunctionValue) procValue).getValue();
//                }
//                
//                if(function == null) {
//                    return new ErrorValue(ErrorFactory.createInvalidParamsError("for_each expects a function as a second parameter."));
//                }
//                
//                Value result = NullValue.NULL;
//                while(stream.hasNext()) {
//                    Value nextValue = stream.next();
//                    result = function.execute(new Value[] {nextValue});
//                    if(result instanceof ErrorValue) break;
//                }
//                stream.close();
//
//                return result;
//            }
//            
//        });
        
        reg(new GenericFunction1("stream_from_uri", "uri") {
            @Override
            public Value execute(Value uriValue) throws Exception {
                String uri = uriValue.asString();
                FileInputStream stream = new FileInputStream(uri);
                try {
                    stream.open();
                    return new StreamValue(stream);
                }
                catch(Exception e) {
                    return new ErrorValue(ErrorFactory.createError("Error opening file stream: " + e));
                }
            }
            
        });
        
        reg(new GenericFunction1("list_to_stream", "list") {
            @Override
            public Value execute(Value listValue) throws Exception {
                if(!(listValue instanceof ListValue)) 
                    return new ErrorValue(ErrorFactory.createInvalidParamsError("Expected a list."));
                
                final ListValue list = (ListValue) listValue;
                IStream stream = new IStream() {
                    Iterator<Value> it = list.getValue().iterator();
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public Value next() {
                        return it.next();
                    }

                    @Override
                    public void close() throws IOException {
                        // Nada
                    }
                    
                    @Override
                    public void write(Value v) throws IOException {
                    	throw new IOException("Not Supported");
                    }
                };
                return new StreamValue(stream);
            }
            
        });
        
        reg(new GenericFunction1("sreadline", "stream") {
        	@Override
        	public Value execute(Value streamValue) throws Exception {
        		if(!(streamValue instanceof StreamValue)) {
    				return new ErrorValue(ErrorFactory.createInvalidParamsError("has_next: Expected a Stream value"));
    			}
        		IStream stream = ((StreamValue) streamValue).getValue();
        		StringBuffer sb = new StringBuffer();
        		while(stream.hasNext()) {
        			char c = (char) stream.next().asInteger().intValue();
        			if(c == '\n') 
        				break;
        			sb.append(c);
        		}
        		return new StringValue(sb.toString());
        	}
        });
    }
    
}
