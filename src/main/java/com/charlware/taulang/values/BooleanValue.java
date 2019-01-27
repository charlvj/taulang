/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.language.Token;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlvj
 */
public class BooleanValue extends AbstractValue<Boolean> {

    public static BooleanValue TRUE = new BooleanValue(true);
    public static BooleanValue FALSE = new BooleanValue(false);
    
    public BooleanValue(Token token) {
        super(token);
    }
    
    public BooleanValue(Boolean value) {
        super(value);
    }

    @Override
    protected Boolean processToken() throws Exception {
        return token.getSource().equals("true");
    }

    @Override
    public String asString() throws Exception {
        Boolean value = getValue();
        if(value == null) return "?";
        else return value.toString();
    }

    @Override
    public Double asNumber() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public BooleanValue not() {
        try {
            Boolean b = getValue();
            if(b) return BooleanValue.FALSE;
            return BooleanValue.TRUE;
        } catch (Exception ex) {
            Logger.getLogger(BooleanValue.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
