/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.functions.CallFunction;
import com.charlware.taulang.functions.ImportFunction;
import com.charlware.taulang.functions.ListableFunctionsRegister;
import com.charlware.taulang.functions.LogicalFunctionsRegister;
import com.charlware.taulang.functions.MakeFunction;
import com.charlware.taulang.functions.MathFunctionsRegister;
import com.charlware.taulang.functions.PrintFunction;
import com.charlware.taulang.functions.ReadlineFunction;
import com.charlware.taulang.functions.ValueFunction;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.functions.RepeatFunction;
import com.charlware.taulang.functions.SystemFunctionsRegister;
import com.charlware.taulang.functions.ToFunction;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.StringValue;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlvj
 */
public class Runtime {

    protected Interpreter interpreter = null;

    protected Memory memory = new Memory();

    public PrintStream stdout = System.out;
    public InputStream stdin = System.in;

    protected List<AbstractRegister> registers = new ArrayList<>();
    protected List<Value> searchPath = new ArrayList<>();

    public CallFunction callFunction = null;

    public Runtime() {
        addRegister(new SystemFunctionsRegister());
        addRegister(new ListableFunctionsRegister());
        addRegister(new MathFunctionsRegister());
        addRegister(new LogicalFunctionsRegister());
    }

    public void addRegister(AbstractRegister register) {
        register.setRuntime(this);
        registers.add(register);
    }

    public Memory getMemory() {
        return memory;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public List<Value> getSearchPath() {
        return searchPath;
    }

    public void register(String name, Function function) {
        memory.put(name, function);
        function.setRuntime(this);
    }

    public void register(Function function) {
        memory.put(function.getName(), function);
        function.setRuntime(this);
    }

    public void register(String name, Value value) {
        Function function = new ValueFunction(name, value);
        memory.put(name, function);
        function.setRuntime(this);
    }

    public void initialize() {
        initializeStreams();
        initializeMemory();
    }

    public void initializeMemory() {
        register("system.searchpath", new ListValue(searchPath));

        register("?", new NullValue());
        register(new PrintFunction());
        register("newline", new StringValue("\n"));
        register(new ReadlineFunction());
        register(new MakeFunction());
        register(new RepeatFunction());
        register(new ToFunction());
        register(new ImportFunction());

        runRegisters();

        getQuickAccessFunctions();
    }
    
    public void clearMemory() {
        memory = new Memory();
        initializeMemory();
    }

    private void runRegisters() {
        registers.stream().forEach(reg -> reg.registerAll());
    }

    public void initializeStreams() {
//        register("stdin", new )
    }

    private void getQuickAccessFunctions() {
        callFunction = (CallFunction) memory.get("call");
    }
}
