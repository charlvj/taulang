/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

/**
 *
 * @author charlvj
 */
public class Symbol {
    private final int intValue;
    private final String strValue;
    
    private static int seq = 1;
    
    public Symbol(String stringValue) {
        this.intValue = seq++;
        if(stringValue.startsWith(":")) {
            stringValue = stringValue.substring(1);
        }
        this.strValue = stringValue;
    }
    
    public String getStringValue() {
        return strValue;
    }
    
    public int getIntValue() {
        return intValue;
    }

    @Override
    public int hashCode() {
        return intValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Symbol other = (Symbol) obj;
        if (this.intValue != other.intValue) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return ":" + strValue;
    }
}
