/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.TauObject;
import com.charlware.taulang.language.Token;

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

}
