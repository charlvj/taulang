/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

/**
 *
 * @author charlvj
 */
public class RuntimeFlags {
    boolean tailCallOptimizationEnabled = false;

    public boolean isTailCallOptimizationEnabled() {
        return tailCallOptimizationEnabled;
    }

    public void setTailCallOptimizationEnabled(boolean tailCallOptimizationEnabled) {
        this.tailCallOptimizationEnabled = tailCallOptimizationEnabled;
    }
    
    
}
