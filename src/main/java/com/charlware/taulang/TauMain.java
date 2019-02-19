package com.charlware.taulang;

import com.charlware.taulang.values.Value;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class TauMain {

    private static Options progOptions = new Options();
    
    public static void main(String[] args) throws org.apache.commons.cli.ParseException, IOException {

        Runtime runtime = new Runtime();
        Interpreter interpreter = runtime.getInterpreter();
        runtime.initialize();
        
        CommandLine cl = createCLParser(args);
        Option[] options = cl.getOptions();
        String program = null;
        for (Option opt : options) {
//            System.out.print("Processing option: " + opt);
            switch (opt.getOpt()) {
                case "f":
                    String programFilename = opt.getValue(0);
                    File programFile = new File(programFilename);
                    if(!programFile.exists()) {
                        System.out.println("File does not exist: " + programFile);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    Files.lines(programFile.toPath())
                            .forEach(line -> sb.append(line).append(" "));
                    program = sb.toString();
                    break;
                case "c":
                    program = opt.getValue(0);
                    break;
                default:
                    HelpFormatter help = new HelpFormatter();
                    help.printHelp("bbl", progOptions, true);
                    return;
            }
        }
        Value result;
        try {
            result = interpreter.interpret(program);
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println("Runtime Error: " + ex);
        }
        
   }

    private static CommandLine createCLParser(String[] args) throws org.apache.commons.cli.ParseException {
        Option programOption = Option.builder("f")
                .longOpt("file")
                .argName("programFile")
                .hasArg()
                .build();
        Option codeOption = Option.builder("c")
                .longOpt("code")
                .desc("Code to execute.")
                .argName("Tau code")
                .hasArg()
                .build();
        Option helpOption = Option.builder("h")
                .longOpt("help")
                .desc("Help")
                .build();

        
        progOptions.addOption(programOption)
                .addOption(codeOption)
                .addOption(helpOption);

        CommandLineParser parser = new DefaultParser();
        return parser.parse(progOptions, args);
    }
}
