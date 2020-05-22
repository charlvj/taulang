/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.charlware.taulang.language.Function;

/**
 *
 * @author charlvj
 */
public class MemoryScope implements Iterable<Map<String,Function>> {
    
	final MemoryScope parent;
    final Map<String,Function> functions = new HashMap();
    boolean importSource = false;
    boolean importScope = false;
    
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
    
    public void putAll(MemoryScope scope) {
        functions.putAll(scope.functions);
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
    
    public boolean exportToImportSource(String name) {
    	Function f = get(name);
    	if(f != null) {
    		MemoryScope importSourceScope = findImportSource();
        	importSourceScope.put(f);
        	return true;
    	}
    	else
    		return false;
    }
    
    public void setImportSource(boolean isImportSource) {
    	this.importSource = isImportSource;
    }
    
    public boolean isImportSource() {
    	return importSource;
    }
    
    public void setImportScope(boolean isImportScope) {
    	this.importScope = isImportScope;
    }
    
    public boolean isImportScope() {
    	return importScope;
    }
    
    public MemoryScope findImportSource() {
    	if(importSource || parent == null)
    		return this;
    	else
    		return parent.findImportSource();
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
    
    public void printKeys(PrintStream out) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("[ ");
    	if(parent == null) 
    		sb.append("<toplevel>");
    	else
    		functions.keySet().stream().sorted().forEach(s -> sb.append(s).append(" "));
    	sb.append("]");
    	out.println(sb.toString());
    	if(parent != null) {
    		out.print("  -> ");
    		parent.printKeys(out);
    	}
    }
}
