/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.StringValue;
import com.charlware.taulang.values.Value;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlvj
 */
public class FileInputStream implements IStream {

    private final String urlString;
    private BufferedReader reader = null;
    
    public FileInputStream(String urlString) {
        this.urlString = urlString;
    }
    
    public void open() throws IOException, URISyntaxException {
        File file = new File(urlString);
        try {
            reader = new BufferedReader(new FileReader(file));
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
            Logger.getLogger(FileInputStream.class.getName()).log(Level.SEVERE, "Caught!");
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
            Logger.getLogger(FileInputStream.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorValue(ErrorFactory.createError(ex.toString()));
        } catch (IOException ex) {
            Logger.getLogger(FileInputStream.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorValue(ErrorFactory.createError(ex.toString()));
        }
    }

    @Override
    public void close() throws IOException {
        if(reader != null)
            reader.close();
    }
    
}
