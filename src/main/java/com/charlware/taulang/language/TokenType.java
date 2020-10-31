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
public enum TokenType {
    COMMENT,
    INTEGER,
    DOUBLE,
    STRING,
    BOOLEAN,
    IDENTIFIER,
    IDENTIFIER_NO_EVAL,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    LIST,
    SYMBOL,
    UNKNOWN
}
