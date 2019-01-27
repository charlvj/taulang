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
public class InvalidCastException extends Exception {
    public InvalidCastException(String from, String to) {
        super("Could not cast value from " + from + " to " + to);
    }
}
