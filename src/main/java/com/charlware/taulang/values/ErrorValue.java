/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.TauError;

/**
 *
 * @author charlvj
 */
public class ErrorValue extends AbstractValue<TauError> {

    public ErrorValue(TauError error) {
        super(error);
        realized = true;
    }
    
    public ErrorValue(String msg) {
        this(ErrorFactory.createError(msg));
    }
    
    @Override
    protected TauError processToken() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asString() throws Exception {
        return getValue().toString();
    }

    @Override
    public Double asNumber() throws Exception {
        return (double) getValue().getCode();
    }
    
}