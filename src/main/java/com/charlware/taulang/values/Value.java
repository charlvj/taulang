/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.language.InvalidCastException;

/**
 *
 * @author charlvj
 */
public interface Value {
    public void setInterpreter(Interpreter interpreter);
    public Value realize() throws Exception;
    public String getType();
    public String asString() throws Exception;
    public Double asNumber() throws Exception;
    public Integer asInteger() throws Exception;
}
