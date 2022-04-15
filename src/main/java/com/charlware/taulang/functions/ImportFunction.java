/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import com.charlware.taulang.Memory;
import com.charlware.taulang.MemoryScope;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.util.LinkedList;
import com.charlware.taulang.values.BooleanValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class ImportFunction extends Function {

    public ImportFunction() {
        name = "import";
        params = new String[]{"filename", "namespace"};
    }

    @Override
    public Value execute(Value[] params) throws Exception {
        String filename = params[0].asString();
        String namespace = params[1].asString();

        return execute(filename, namespace);
    }

    public Value execute(String filename) throws Exception {
    	return execute(filename, null);
    }
    
    public Value execute(String filename, String namespace) throws Exception {
        Value result = null;
        InputStream stream = System.class.getResourceAsStream("/" + filename);
        if (stream != null) {
            result = execute(filename, stream, namespace);
        } else {
            File file = new File(filename);
            if (!file.exists()) {
                LinkedList<Value> searchPath = ((ListValue) runtime.getMemory().get("system.searchpath").execute()).getValue();
                for (Value path : searchPath) {
                    file = new File(path.asString(), filename);
                    if (file.exists()) {
                        break;
                    }
                }
            }
            result = execute(file, namespace);
        }
        return result;
    }

    public Value execute(String filename, InputStream stream, String namespace) throws Exception {
        String code = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }
            code = out.toString();
        }
        final String s = code;
        if (code != null) {
            return interpretWrapped(filename, namespace, () -> runtime.getInterpreter().interpret(s));
        }
        else
            return BooleanValue.FALSE;
    }

    public Value execute(File file, String namespace) throws Exception {
        Value result = null;
        if (file.exists()) {
            Path p = file.toPath();
            result = interpretWrapped(file.getAbsolutePath(), namespace,
                                      () -> runtime.getInterpreter().interpret(p));
        } else {
            runtime.stdout.println("Could not find file");
            result = BooleanValue.FALSE;
        }
        return result;
    }
    
    protected BooleanValue interpretWrapped(String contextName, String namespace, Callable callable) {
        Memory memory = runtime.getMemory();
        memory.getCurrentScope().setImportSource(true);
        MemoryScope scope = memory.pushScope();
        scope.setNamespace(namespace);
        scope.setImportScope(true);
        try {
            callable.call();
            return BooleanValue.FALSE;
        }
        catch (Exception ex) {
            runtime.stdout.println("Error interpreting " + contextName + ": " + ex);
            ex.printStackTrace(runtime.stdout);
            return BooleanValue.FALSE;
        }       
        finally {
            memory.popScope();
        }
    }
}
