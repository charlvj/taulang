/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.charlware.taulang.functions.CallFunction;
import com.charlware.taulang.functions.ErrorFunctionRegister;
import com.charlware.taulang.functions.ImportFunction;
import com.charlware.taulang.functions.ListableFunctionsRegister;
import com.charlware.taulang.functions.LogicalFunctionsRegister;
import com.charlware.taulang.functions.MakeFunction;
import com.charlware.taulang.functions.MathFunctionsRegister;
import com.charlware.taulang.functions.ObjectFunctionsRegister;
import com.charlware.taulang.functions.PrintFunction;
import com.charlware.taulang.functions.ReadlineFunction;
import com.charlware.taulang.functions.RepeatFunction;
import com.charlware.taulang.functions.StreamFunctionsRegister;
import com.charlware.taulang.functions.SystemFunctionsRegister;
import com.charlware.taulang.functions.ToFunction;
import com.charlware.taulang.functions.ValueFunction;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.FunctionCaller;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

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
    
    public PrintStream tracer = null;

    protected List<AbstractRegister> registers = new ArrayList<>();
    protected List<Value> searchPath = new ArrayList<>();

    public CallFunction callFunction = null;
    public final FunctionCaller functionCaller;

    public Runtime() {
    	functionCaller = new FunctionCaller(this);
        interpreter = new Interpreter(this);
        
        addDefaultSearchPath();

        addRegister(new SystemFunctionsRegister());
        addRegister(new ErrorFunctionRegister());
        addRegister(new ListableFunctionsRegister());
        addRegister(new MathFunctionsRegister());
        addRegister(new LogicalFunctionsRegister());
        addRegister(new ObjectFunctionsRegister());
        addRegister(new StreamFunctionsRegister());

        if(flags.isEnableTracer()) {
        	try {
				tracer = new PrintStream(flags.getTracerFile());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
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

    private void addDefaultSearchPath() {
    	try {
	    	Path path= new File(
	                com.charlware.taulang.Runtime.class.getResource("/tau").toURI()
	        ).getParentFile().toPath();
			searchPath.add(new StringValue(path.toFile().getAbsolutePath()));
    	}
    	catch(Exception e) {
    		System.out.println("Could not find default search path.");
    	}
    }
    
    public void register(String name, Function function) {
        memory.getSystemScope().put(name, function);
        function.setRuntime(this);
    }

    public void register(Function function) {
        memory.getSystemScope().put(function.getName(), function);
        function.setRuntime(this);
    }

    public void register(String name, Value value) {
        Function function = new ValueFunction(name, value);
        memory.getSystemScope().put(name, function);
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
            importStdLibs();
        } catch (Exception ex) {
            stdout.println("Error loading stdlib: " + ex);
        }

        getQuickAccessFunctions();
        
        memory.pushScope();
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
        callFunction = (CallFunction) memory.getCurrentScope().get("call");
    }

    private void importStdLibs() throws Exception {
        ImportFunction importFunc = new ImportFunction();
        importFunc.setRuntime(this);
        importFunc.execute("stdlib.tau");
        importFunc.execute("stdmath.tau");
        importFunc.execute("stdstreams.tau");
    }

}
