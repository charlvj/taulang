/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.Value;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author charlvj
 */
public class TauTest {
    @Test
    public void testTau() throws Exception {
        com.charlware.taulang.Runtime runtime = new com.charlware.taulang.Runtime();
        Interpreter interpreter = runtime.getInterpreter();
        runtime.initialize();

        Path path = new File(
                com.charlware.taulang.Runtime.class.getResource("/tau/test/test_types.tau").toURI()
        ).getParentFile().toPath();
        Files.walk(path)
            .filter(p -> p.toFile().getName().endsWith(".tau"))
            .forEach(p -> runTest(p));
//        Files.walk(path)
//            .map(Path::getFileName)
//            .map(p -> p.toFile())
//            .filter(n -> n.getName().endsWith(".tau"))
//            .forEach(filename -> runTest(filename));
        
    }
    
    private void runTest(Path filename) {
        com.charlware.taulang.Runtime runtime = new com.charlware.taulang.Runtime();
        Interpreter interpreter = runtime.getInterpreter();
        runtime.initialize();
        Value result;
        try {
            System.out.println("Testing " + filename);
            result = interpreter.interpret(filename);
        }
        catch (Exception ex) {
            result = new ErrorValue(ErrorFactory.createError("Test Failed - " + ex));
        }
        assertFalse("An error was returned: " + result, result instanceof ErrorValue);
    }
}
