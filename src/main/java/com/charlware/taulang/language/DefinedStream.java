/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlvj
 */
public class DefinedStream implements IStream {
    private Value[] state;
    private Function hasNextFunction;
    private Function nextFunction;
    
    public DefinedStream(ListValue startingState, Function hasNextFunction, Function nextFunction) {
        try {
            this.state = startingState.getValue().toArray(new Value[0]);
            this.hasNextFunction = hasNextFunction;
            this.nextFunction = nextFunction;
        } catch (Exception ex) {
            Logger.getLogger(DefinedStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DefinedStream(Value[] startingState, Function hasNextFunction, Function nextFunction) {
        this.state = startingState;
        this.hasNextFunction = hasNextFunction;
        this.nextFunction = nextFunction;
    }
    
    @Override
    public boolean hasNext() {
        try {
            return hasNextFunction.execute(state) == BooleanValue.TRUE;
        } catch (Exception ex) {
            Logger.getLogger(DefinedStream.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Value next() {
        try {
            Value result = nextFunction.execute(state);
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
