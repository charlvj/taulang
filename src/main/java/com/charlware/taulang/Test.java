/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;
import java.io.File;
import java.util.List;

/**
 *
 * @author charlvj
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String code = "";

//        code = "print \"What is your name? \" make \"name\" readline print [ \"Hello \" name ]";
//        code = "print [ 3 \"+\" 2 \"=\" plus plus 1 2 2 ]";
//        code = "repeat 5 [ print \"Hello!\" ]";
//        code = "make \"counter\" 1 repeat 5 [ print [ counter newline ] make \"counter\" plus 1 counter ] print \"Done\"";
//        code = "make \"p\" [ 1 2 3 ] print p";
        code = "to \"test.printline\" [ \"msg\" ] [ print [ msg newline ] ] test.printline \"Hello World!\" test.printline \"Yo!\"";
//        code = "make \"list\" [ 1 2 3 4 ] print [ \"List Size: \" listsize list newline \"Third: \" getelem list 2 ]";

        Runtime runtime = new Runtime();
        Interpreter interpreter = runtime.getInterpreter();
        runtime.initialize();
//        interpreter.interpret(code);
        List<Value> searchPath = runtime.getSearchPath();
        searchPath.add(new StringValue("/home/charlvj/NetBeansProjects/TauLang/src/main/taulang"));
        File file = new File("test.logo");
        if (!file.exists()) {
            for (Value path : searchPath) {
                file = new File(path.asString(), "test.logo");
                if (file.exists()) {
                    break;
                }
            }
        }
//        File file = new File("~/NetBeansProjects/rocketlauncher/CvJLogo/src/main/cvjlogo/test.logo");
        if(file.exists())
            interpreter.interpret(file.toPath());
        else
            System.out.println("Could not find file");
    }
}
