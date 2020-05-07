/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.TauObject;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.abilities.Comparable.NotComparableException;

/**
 *
 * @author charlvj
 */
public class ObjectValue extends AbstractValue<TauObject> {

    public ObjectValue(Token token) {
        super(token);
    }
    
    public ObjectValue(TauObject obj) {
        super(obj);
    }

    @Override
    protected TauObject processToken() throws Exception {
        /* No token to process */
        return new TauObject();
    }

    @Override
    public int compareTo(Value o) throws NotComparableException {
        throw new NotComparableException();
    }
}
