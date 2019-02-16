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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class Runtime {

    protected RuntimeFlags flags = new RuntimeFlags();
    protected Interpreter interpreter = null;

    protected Memory memory = new Memory();

    public PrintStream stdout = System.out;
    public InputStream stdin = System.in;

    protected List<AbstractRegister> registers = new ArrayList<>();
    protected List<Value> searchPath = new ArrayList<>();

    public CallFunction callFunction = null;

    public Runtime() {
        interpreter = new Interpreter(this);

        addRegister(new SystemFunctionsRegister());
        addRegister(new ListableFunctionsRegister());
        addRegister(new MathFunctionsRegister());
        addRegister(new LogicalFunctionsRegister());

    }

    public void addRegister(AbstractRegister register) {
        register.setRuntime(this);
        registers.add(register);
    }
    
    public RuntimeFlags getFlags() {
        return flags;
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

        try {
            importStdLib();
        } catch (Exception ex) {
            stdout.println("Error loading stdlib: " + ex);
        }

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

    private void importStdLib() throws Exception {
        ImportFunction importFunc = new ImportFunction();
        importFunc.setRuntime(this);
        importFunc.execute("stdlib.tau");
//        URL stdlibURL = getClass().getResource("/stdlib.tau");
//        System.out.println("File: " + stdlibURL.getFile() + "\nFile Exists? " + );
//        importFunc.execute(new File(stdlibURL.getFile()));
//        String code = null;
//        try (InputStream in = getClass().getResourceAsStream("/stdlib.tau")) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder out = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                out.append(line);
//            }
//            code = out.toString();
//        }
//        if(code != null) {
//            interpreter.interpret(code);
//        }
    }

}
