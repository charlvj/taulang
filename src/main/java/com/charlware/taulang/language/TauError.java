/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public class TauError {
    private int code = -1;
    private String message;
    private Token token;
    private Value reference;
    private boolean fatal;
    
    public TauError(String msg) {
        this.message = msg;
    }
    
    public String toString() {
        return "Error: " + message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Value getReference() {
        return reference;
    }

    public void setReference(Value reference) {
        this.reference = reference;
    }

    public boolean isFatal() {
        return fatal;
    }

    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }
    
    
}
