/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class TauTest {
	private Path tauTestsPath;
	private String tauTestsPathName;
	
	@Before
	public void setup() throws URISyntaxException {
		tauTestsPath = new File(
                com.charlware.taulang.Runtime.class.getResource("/tau/test/test_types.tau").toURI()
        ).getParentFile().toPath();
		tauTestsPathName = tauTestsPath.toFile().getAbsolutePath();
	}
	
    @Test
    public void testTau() throws Exception {
        Files.walk(tauTestsPath)
            .filter(p -> p.toFile().getName().endsWith(".tau"))
            .forEach(p -> runTest(p));
    }
    
    private void runTest(Path filename) {
        com.charlware.taulang.Runtime runtime = new com.charlware.taulang.Runtime();
        List<Value> searchPath = runtime.getSearchPath();
        searchPath.add(new StringValue(tauTestsPathName));
        Interpreter interpreter = runtime.getInterpreter();
        runtime.initialize();
        Value result;
        try {
            System.out.println("Running " + filename.getFileName());
            result = interpreter.interpret(filename);
        }
        catch (Exception ex) {
            result = new ErrorValue(ErrorFactory.createError("Test Failed - " + ex));
        }
        assertFalse("An error was returned: " + result, result instanceof ErrorValue);
    }
}
