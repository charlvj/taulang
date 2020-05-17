/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class DefinedStream implements IStream {
    private Value[] state;
    private Value hasNextCallable;
    private Value nextCallable;
    private com.charlware.taulang.Runtime runtime;
    
    public DefinedStream(com.charlware.taulang.Runtime runtime, ListValue startingState, Value hasNextCallable, Value nextCallable) {
        try {
        	this.runtime = runtime;
        	this.state = startingState.getValue().toArray(new Value[0]);
            this.hasNextCallable = hasNextCallable;
            this.nextCallable = nextCallable;
        } catch (Exception ex) {
            Logger.getLogger(DefinedStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DefinedStream(Value[] startingState, Value hasNextCallable, Value nextCallable) {
        this.state = startingState;
        this.hasNextCallable = hasNextCallable;
        this.nextCallable = nextCallable;
    }
    
    @Override
    public boolean hasNext() {
        try {
            return runtime.callFunction.execute(hasNextCallable, new ListValue(state)) == BooleanValue.TRUE;
        } catch (Exception ex) {
            Logger.getLogger(DefinedStream.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Value next() {
        try {
            Value result = runtime.callFunction.execute(nextCallable, new ListValue(state));
            if(result instanceof ListValue) {
                ListValue resultList = (ListValue) result;
                state = ((ListValue) resultList.get(1)).getValue().toArray(new Value[0]);
                return resultList.get(0);
            }
            else {
                return result;
            }
        } catch (Exception ex) {
            Logger.getLogger(DefinedStream.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorValue(ErrorFactory.createFatalError("Error getting next stream value: " + ex));
        }
    }

    @Override
    public void close() throws IOException {
        // ...
    }

	@Override
	public void write(Value value) throws IOException {
		throw new IOException("Defined Stream does not support writing.");
	}
}
