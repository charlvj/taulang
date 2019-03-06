/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.language.Function;

/**
 *
 * @author charlvj
 */
public class Memory {
    private final MemoryScope systemScope = new MemoryScope(null);
    private MemoryScope currentScope = systemScope;
    
    public MemoryScope getSystemScope() {
        return systemScope;
    }
    
    public MemoryScope getCurrentScope() {
        return currentScope;
    }
    
    public void setCurrentScope(MemoryScope newScope) {
        currentScope = newScope;
    }
    
    public MemoryScope pushScope() {
        currentScope = currentScope.push();
        return currentScope;
    }
    
    public void popScope() {
        MemoryScope scope = currentScope.pop();
        if(scope != null)
            currentScope = scope;
    }
    
    public Function get(String name) {
        return currentScope.get(name);
    }
}
