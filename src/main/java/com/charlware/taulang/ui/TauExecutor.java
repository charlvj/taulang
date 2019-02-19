/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.ui;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.values.Value;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class TauExecutor {

    private final com.charlware.taulang.Runtime tauRuntime;
    private final Interpreter tau;
    
    private PrintStream out = System.out;
    private PrintStream err = System.err;
    private InputStream in = System.in;

    public TauExecutor() {
        tauRuntime = new com.charlware.taulang.Runtime();
        tauRuntime.initialize();
        tau = tauRuntime.getInterpreter();
    }

    public void runFresh(String code, List<File> imports) {
        if(bootstrap(imports)) {
            run(code);
        }
    }
    
    public void run(String code) {
        try {
            out.println("? " + code);
            Value result = tau.interpret(code);
            out.println(""+result);
        } catch (Exception ex) {
            err.println("Error: " + ex);
        }
    }
    
    public boolean bootstrap(Collection<File> imports) {
        StringBuilder bootstrap = new StringBuilder();
        imports.forEach(f -> bootstrap.append("print \"Importing " + f.getName() + "...\" printline ")
                .append("import \"")
                .append(f.getAbsolutePath())
                .append("\"")
                .append(" \"\" "));
        String s = bootstrap.toString();
        if(!s.isEmpty())
            try {
                tau.interpret(bootstrap.toString());
                return true;
            } catch (Exception ex) {
                err.println("An error occurred while bootstrapping the Tau runtime: " + ex);
                return false;
            }
        else
            return true;
    }
    
    public void reset() {
        tauRuntime.clearMemory();
    }
    
    public void setOut(PrintStream out) {
        this.out = out;
        tauRuntime.stdout = out;
    }

    public void setErr(PrintStream err) {
        this.err = err;
    }

    public void setIn(InputStream in) {
        this.in = in;
        tauRuntime.stdin = in;
    }

    
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        File f = new File("/home/cvanjaarsveldt/lang/taulang/logo.tau");
        sb.append("import \"").append(f.getAbsoluteFile()).append("\"");
        System.out.println(sb);
    }
}
