/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions;

import com.charlware.taulang.Interpreter;
import com.charlware.taulang.Memory;
import com.charlware.taulang.language.Function;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.Value;
import java.io.File;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class ImportFunction extends Function {
    public ImportFunction() {
        name = "import";
        params = new String[] {"filename", "namespace"};
    }

    @Override
    public Value execute(Value[] params) throws Exception {
        String filename = params[0].asString();
        String namespace = params[1].asString();
        
        Interpreter interpreter = runtime.getInterpreter();
        Memory memory = runtime.getMemory();
        memory.setContext(filename);
        memory.push();
        
        try {
            File file = new File(filename);
            if (!file.exists()) {
                List<Value> searchPath = ((ListValue) memory.get("system.searchpath").execute()).getValue();
                for (Value path : searchPath) {
                    file = new File(path.asString(), filename);
                    if (file.exists()) {
                        break;
                    }
                }
            }
            
            if(file.exists())
                interpreter.interpret(file.toPath());
            else
                System.out.println("Could not find file");
        }
        finally {
            runtime.getMemory().setSystemContext();
        }
        
        return null;
    }
    
}
