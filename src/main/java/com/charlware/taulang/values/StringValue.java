/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.InvalidCastException;
import com.charlware.taulang.language.Token;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author charlvj
 */
public class StringValue extends AbstractValue<String> implements Listable<String> {
    public StringValue(Token token) {
        super(token);
    }

    public StringValue(String value) {
        super(value);
    }
    
    protected String processToken() throws Exception {
        return token.getSource();
    }
    
    @Override
    public String asString() throws Exception {
        return getValue();
    }

    @Override
    public Double asNumber() throws Exception {
        String value = getValue();
        if(NumberUtils.isCreatable(value)) {
            return NumberUtils.toDouble(value);
        }
        throw new InvalidCastException(value, "Number");
    }

    @Override
    public int size() {
        try {
            return getValue().length();
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public String get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void set(int index, String elem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
