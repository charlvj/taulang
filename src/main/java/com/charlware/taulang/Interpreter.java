/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.TailCallValue;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.DoubleValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.FunctionValue;
import com.charlware.taulang.values.IntegerValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.SymbolValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class Interpreter {

    protected final Runtime runtime;
    
    protected InterpreterHook hook = null;
    
    private AtomicInteger stepCounter = new AtomicInteger(0);

    public Interpreter(Runtime runtime) {
        this.runtime = runtime;
        runtime.setInterpreter(this);
    }

    public Value interpret(String code) throws Exception {
        Tokenizer tokenizer = new Tokenizer(code);
        Iterator<Token> tokens = tokenizer.parseTokens();
//        while(tokens.hasNext()) {
//			System.out.println(tokens.next());
//		}
        return interpret(new PreProcessor(tokens));
    }

    public Value interpret(Path file) throws Exception {
        final StringBuilder sb = new StringBuilder();
        Files.lines(file).forEach(line -> sb.append(line).append("\n"));
        return interpret(sb.toString());
    }

    public Value interpret(PreProcessor tokens) throws Exception {
        Token token;
        Value result = null;
        while (tokens.hasNext()) {
            result = eval(tokens.next(), tokens);
            if(result instanceof ErrorValue) break;
        }
        if(result == null) result = NullValue.NULL;
        return result.realize();
    }

    public Value eval(Token token, Iterator<Token> tokens) throws Exception {
        if(runtime.flags.isEnableTracer()) {
        	runtime.tracer.println("Token: " + stepCounter.getAndIncrement() + " - " + token.toStringShort());
        	runtime.memory.getCurrentScope().printKeys(runtime.tracer);
        	runtime.tracer.println("---");
        }
        if(hook != null) {
        	hook.aboutToEval(token);
        }
    	Value result = null;
        switch (token.getType()) {
            case LIST:
                result = new ListValue(token);
                break;
            case DOUBLE:
                result = new DoubleValue(token);
                break;
            case INTEGER:
            	result = new IntegerValue(token);
            	break;
            case STRING:
                result = new StringValue(token);
                break;
            case SYMBOL:
                result = new SymbolValue(token);
                break;
            case IDENTIFIER_NO_EVAL:
            	Function functionValue = runtime.getMemory().get(token.getSource());
            	result = new FunctionValue(functionValue);
            	break;
            case IDENTIFIER:
                Function function = runtime.getMemory().get(token.getSource());

                if(function == null) {
                    result = new ErrorValue(ErrorFactory.createError("Function not defined: " + token.getSource()));
                }
                else {
                    int numParams = function.getNumParams();
                    Value[] params = new Value[numParams];
                    Token tok;
                    for (int i = 0; i < numParams; i++) {
                        tok = tokens.next();
                        params[i] = eval(tok, tokens);
                    }
                    if(runtime.getFlags().isTailCallOptimizationEnabled()) {
                        if(tokens.hasNext()) {
                        	result = function.execute(params);
//                        	result = runtime.functionCaller.call(function, params);
                        }
                        else
                            result = new TailCallValue(function, params);
                    }
                    else {
                        result = function.execute(params);
//                        result = runtime.functionCaller.call(function, params);
                    }
                }
                break;
        }
        if(result != null)
            result.setInterpreter(this);
        else
            result = NullValue.NULL;
        
        result.setMemoryScope(runtime.getMemory().getCurrentScope());
        
        if(hook != null) {
        	hook.evaluated(token, result);
        }
        
        return result;
    }

    public Value eval(Iterator<Token> tokens) throws Exception {
        Token token;
        Value result = null;
        while (tokens.hasNext()) {
            token = tokens.next();
            result = eval(token, tokens);
            if(result instanceof ErrorValue) 
                break;
//            if(result instanceof ErrorValue 
//                    && ((ErrorValue) result).getValue().isFatal()) 
//                break;
        }
        return result;
    }

    public Runtime getRuntime() {
        return runtime;
    }
    
    public void setInterpreterHook(InterpreterHook hook) {
    	this.hook = hook;
    }
}
