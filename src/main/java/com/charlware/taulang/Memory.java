/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.functions.ValueFunction;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.Value;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author charlvj
 */
public class Memory implements Iterable<Map<String,Function>> {
    String currentContext = null;
    Deque<Map<String,Function>> functions = new ArrayDeque<>();
    Map<String,Function> functionsBase = new HashMap<>();
    Map<String,Function> functionsHead;
    
    Map<String,Deque<Map<String,Function>>> contexts = new HashMap<>();
    
    public Memory() {
        setSystemContext();
    }
    
    public void put(String name, Function function) {
        function.setContext(currentContext);
        functionsHead.put(name, function);
    }
    
    public void put(Function function) {
        put(function.getName(), function);
    }
    
    public Function get(String name) {
        Function function = null;
        for(Map<String,Function> map: functions) {
            function = map.get(name);
            if(function != null)
                break;
        }
        return function;
    }
    
    public Function remove(String name) {
        Function function = null;
        for(Map<String,Function> map: functions) {
            function = map.remove(name);
            if(function != null)
                break;
        }
        return function;
    }
    
    public void push() {
        functionsHead = new HashMap<>();
        functions.push(functionsHead);
    }
    
    public void pop() {
        functions.pop();
        functionsHead = functions.peek();
    }
    
    public void elevate(String name) {
        // For now, just make it global...
        functionsBase.put(name, remove(name));
    }
    
    public void setContext(String name) {
        Deque<Map<String,Function>> context = contexts.get(name);
        if(context == null) {
            context = new ArrayDeque<>();
            context.push(functionsBase);
            contexts.put(name, context);
        }
        functions = context;
        functionsHead = functionsBase;
        currentContext = name;
    }
    
    public void setSystemContext() {
        setContext("__SYSTEM__");
    }
    
    public String getCurrentContext() {
        return currentContext;
    }
    
    @Override
    public Iterator<Map<String,Function>> iterator() {
        return functions.iterator();
    }
}
