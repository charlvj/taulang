/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.language.Function;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author charlvj
 */
public class MemoryScope implements Iterable<Map<String,Function>> {
    final MemoryScope parent;
    final Map<String,Function> functions = new HashMap();
    
    public MemoryScope(MemoryScope parent) {
        this.parent = parent;
//        setSystemContext();
    }
    
    public void put(String name, Function function) {
        if(function.getMemory() == null)
            function.setMemory(this);
        functions.put(name, function);
    }
    
    public void put(Function function) {
        put(function.getName(), function);
    }
    
    public Function get(String name) {
        Function function = functions.get(name);
        if(function == null && parent != null)
            function = parent.get(name);
        return function;
    }
    
    public Function remove(String name) {
        Function function = functions.remove(name);
        if(function == null && parent != null) 
            function = parent.remove(name);
        return function;
    }
    
    public MemoryScope push() {
        return new MemoryScope(this);
    }
    
    public MemoryScope pop() {
        return parent;
    }
    
    public void elevate(String name) {
        if(parent != null) {
            Function function = functions.remove(name);
            if(function != null) 
                parent.put(name, function);
        }
    }
    
    public void elevateToSystem(String name) {
        if(parent != null) {
            if(parent.parent == null) {
                elevate(name);
            }
            else {
                parent.elevateToSystem(name);
            }
        }
    }
    
//    public void setContext(String name) {
//        Deque<Map<String,Function>> context = contexts.get(name);
//        if(context == null) {
//            context = new ArrayDeque<>();
//            context.push(functionsBase);
//            contexts.put(name, context);
//        }
//        functions = context;
//        functionsHead = functionsBase;
//        currentContext = name;
//    }
//    
//    public void setSystemContext() {
//        setContext("__SYSTEM__");
//    }
//    
//    public String getCurrentContext() {
//        return currentContext;
//    }
    
    @Override
    public Iterator<Map<String,Function>> iterator() {
        return null;
    }
}
