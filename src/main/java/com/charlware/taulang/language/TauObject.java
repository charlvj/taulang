/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.MemoryScope;

/**
 *
 * @author charlvj
 */
public class TauObject {
    private MemoryScope scope = null;
    private String type = "Object";
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public Function get(String functionName) {
        return scope.get(functionName);
    }
    
    public void put(String functionName, Function function) {
        scope.put(functionName, function);
    }

    public MemoryScope getScope() {
        return scope;
    }

    public void setScope(MemoryScope scope) {
        this.scope = scope;
    }
    
    
}
