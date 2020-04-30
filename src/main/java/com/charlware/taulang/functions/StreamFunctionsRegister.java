/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.FileInputStream;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.IStream;
import com.charlware.taulang.language.Stream;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.StreamValue;
import com.charlware.taulang.values.Value;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author charlvj
 */
public class StreamFunctionsRegister extends AbstractRegister {

    @Override
    public void registerAll() {
        reg(new GenericFunction1("is_stream", "value") {
            @Override
            public Value execute(Value value) throws Exception {
                return BooleanValue.valueOf(value instanceof StreamValue);
            }
            
        });
        
        reg(new GenericFunction3("new_stream", "initial_state", "has_next_function", "next_function") {
            @Override
            public Value execute(Value initialStateValue, Value hasNextFunctionValue, Value nextFunctionValue) throws Exception {
                if(!(initialStateValue instanceof ListValue)) {
                    return new ErrorValue(ErrorFactory.createInvalidParamsError("Initial State is expected to be a list."));
                }
                ListValue initialState = (ListValue) initialStateValue;
                
                String functionName = hasNextFunctionValue.asString();
                Function hasNextFunction = getMemory().get(functionName);
                
                functionName = nextFunctionValue.asString();
                Function nextFunction = getMemory().get(functionName);
                
                Stream stream = new Stream(initialState, hasNextFunction, nextFunction);
                return new StreamValue(stream);
            }
        });
        
        reg(new GenericFunction2("for_each", "stream", "proc") {
            @Override
            public Value execute(Value streamValue, Value procValue) throws Exception {
                if(streamValue instanceof ErrorValue)
                    return streamValue;
                
                if(!(streamValue instanceof StreamValue)) {
                    return new ErrorValue(ErrorFactory.createInvalidParamsError("for_each expects a stream as a first parameter."));
                }
                IStream stream = ((StreamValue) streamValue).getValue();
                
                Function function = null;
                if(procValue instanceof FunctionValue) {
                    function = ((FunctionValue) procValue).getValue();
                }
                
                if(function == null) {
                    return new ErrorValue(ErrorFactory.createInvalidParamsError("for_each expects a function as a second parameter."));
                }
                
                Value result = NullValue.NULL;
                while(stream.hasNext()) {
                    Value nextValue = stream.next();
                    result = function.execute(new Value[] {nextValue});
                    if(result instanceof ErrorValue) break;
                }
                stream.close();

                return result;
            }
            
        });
        
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
                };
                return new StreamValue(stream);
            }
            
        });
    }
    
}
