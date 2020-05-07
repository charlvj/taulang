/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.IStream;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;
import com.charlware.taulang.language.DefinedStream;


/**
 *
 * @author charlvj
 */
public class StreamValue extends AbstractValue<IStream> {

    public StreamValue(IStream value) {
        super(value);
    }

    @Override
    protected DefinedStream processToken() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        throw new NotComparableException();
    }
}
