/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author charlvj
 */
public class TauObject {
    private Map<String,Function> functions = new HashMap();
    private String type = "Object";
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public Function get(String functionName) {
        return functions.get(functionName);
    }
    
    public void put(String functionName, Function function) {
        functions.put(functionName, function);
    }
}
