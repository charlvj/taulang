/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.values.Value;
import java.util.List;

/**
 *
 * @author charlvj
 */
public abstract class Function {
    protected String name;
    protected String[] params;
    protected com.charlware.taulang.Runtime runtime;
    protected String context = null;
    protected MemoryScope memory = null;
    protected boolean leaveInCallingScope = false;
    
    public String getName() {
        return name;
    }
    
    public String[] getParams() {
        return params;
    }
    
    public int getNumParams() {
        return params.length;
    }
    
    public void noParams() {
        params = new String[] {};
    }
    
    public void setRuntime(com.charlware.taulang.Runtime runtime) {
        this.runtime = runtime;
//        this.context = runtime.getMemory().getCurrentContext();
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public void setMemory(MemoryScope memory) {
        this.memory = memory;
    }
    
    public MemoryScope getMemory() {
//        if(memory == null) 
//            return runtime.getMemory().getCurrentScope();
//        else
            return memory;
    }
    
    public abstract Value execute(Value[] params) throws Exception;
    
    public Value execute(List<Value> params) throws Exception {
        return execute(params.toArray(new Value[] {}));
    }
    
    public Value execute() throws Exception {
        return execute(new Value[0]);
    }
}
