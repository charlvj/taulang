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
	boolean enableTracer = true;
	String tracerFile = "/tmp/taulang.trace";
    boolean tailCallOptimizationEnabled = false;

    public boolean isTailCallOptimizationEnabled() {
        return tailCallOptimizationEnabled;
    }

    public void setTailCallOptimizationEnabled(boolean tailCallOptimizationEnabled) {
        this.tailCallOptimizationEnabled = tailCallOptimizationEnabled;
    }

	public boolean isEnableTracer() {
		return enableTracer;
	}

	public void setEnableTracer(boolean enableTracer) {
		this.enableTracer = enableTracer;
	}

	public String getTracerFile() {
		return tracerFile;
	}

	public void setTracerFile(String tracerFile) {
		this.tracerFile = tracerFile;
	}
    
    
}
