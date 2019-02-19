/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

/**
 *
 * @author charlvj
 */
public class ErrorFactory {
    public static TauError createError(String msg) {
        return new TauError(msg);
    }
    public static TauError createFatalError(String msg) {
        TauError e = new TauError(msg);
        e.setFatal(true);
        return e;
    }
    public static TauError createInvalidParamsError(String msg) {
        return createFatalError("Invalid Parameters: " + msg);
    }
}
