/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.functions.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.IStream;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class TextIOStream implements IStream {

    private String urlString;
    private InputStreamReader reader = null;
    private PrintWriter writer = null;
    private IOStreamType mode;
    
    public TextIOStream(String urlString) {
        this.urlString = urlString;
    }
    
    public TextIOStream(InputStream in, OutputStream output) {
    	this.reader = new InputStreamReader(in);
    	this.writer = new PrintWriter(output);
    }
    
    public void open() throws IOException, URISyntaxException {
        File file = new File(urlString);
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            writer = new PrintWriter(new FileOutputStream(file));
        }
        catch(Exception e) {
            reader = null;
            throw e;
        }
    }
    
    @Override
    public boolean hasNext() {
        try {
            if(reader == null)
                open();
            if(reader != null)
                return reader.ready();
            else
                return false;
        } catch (Exception ex) {
            Logger.getLogger(TextIOStream.class.getName()).log(Level.SEVERE, "Caught!");
            return false;
        }
    }

    @Override
    public Value next() {
        try {
            if(reader == null)
                open();

            int b = reader.read();
            return new StringValue("" + ((char) b));
        } catch (URISyntaxException ex) {
            Logger.getLogger(TextIOStream.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorValue(ErrorFactory.createError(ex.toString()));
        } catch (IOException ex) {
            Logger.getLogger(TextIOStream.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorValue(ErrorFactory.createError(ex.toString()));
        }
    }

    @Override
    public void close() throws IOException {
    	System.out.println("Closing stream: " + urlString);
        if(reader != null)
            reader.close();
        if(writer != null)
        	writer.close();
    }
    
    @Override
    public void write(Value value) throws IOException {
    	try {
			writer.print(value.asString());
			writer.flush();
		} catch (Exception e) {
			throw new IOException("Could not write value to output stream: " + e.getMessage(), e);
		}
    }
    
}
