/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.language.Function;
import com.charlware.taulang.language.TailCallValue;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.NumberValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.SymbolValue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 *
 * @author charlvj
 */
public class Interpreter {

    protected final Runtime runtime;

    public Interpreter(Runtime runtime) {
        this.runtime = runtime;
        runtime.setInterpreter(this);
    }

    public Value interpret(String code) throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.parseTokens(code);
        return interpret(tokenizer);
    }

    public Value interpret(Tokenizer tokens) throws Exception {
        return interpret(new PreProcessor(tokens));
    }
    
    public Value interpret(Path file) throws Exception {
        final StringBuilder sb = new StringBuilder();
        Files.lines(file).forEach(line -> sb.append(line).append(" "));
        return interpret(sb.toString());
    }

    public Value interpret(PreProcessor tokens) throws Exception {
        Token token;
        Value result = null;
        while (tokens.hasNext()) {
            result = eval(tokens.next(), tokens);
        }
        return result.realize();
    }

    public Value eval(Token token, Iterator<Token> tokens) throws Exception {
        Value result = null;
        switch (token.getType()) {
            case LIST:
                result = new ListValue(token);
                break;
            case NUMBER:
                result = new NumberValue(token);
                break;
            case STRING:
                result = new StringValue(token);
                break;
            case SYMBOL:
                result = new SymbolValue(token);
                break;
            case IDENTIFIER:
                Function function = runtime.getMemory().get(token.getSource());

                if (function != null) {
                    int numParams = function.getNumParams();
                    Value[] params = new Value[numParams];
                    Token tok;
                    for (int i = 0; i < numParams; i++) {
                        tok = tokens.next();
                        params[i] = eval(tok, tokens);
                    }
                    if(runtime.getFlags().isTailCallOptimizationEnabled()) {
                        if(tokens.hasNext())
                            result = function.execute(params);
                        else
                            result = new TailCallValue(function, params);
                    }
                    else
                        result = function.execute(params);
                }
                break;
        }
        if(result != null)
            result.setInterpreter(this);
        else
            result = NullValue.NULL;
        
        return result;
    }

    public Value eval(Iterator<Token> tokens) throws Exception {
        Token token;
        Value result = null;
        while (tokens.hasNext()) {
            token = tokens.next();
            result = eval(token, tokens);
        }
        return result;
    }

    public Runtime getRuntime() {
        return runtime;
    }
}
