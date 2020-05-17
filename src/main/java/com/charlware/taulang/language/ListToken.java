/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author charlvj
 */
public class ListToken extends Token implements Iterable<Token> {
    protected List<Token> tokens = new ArrayList<>();

    public ListToken() {
        super();
        setSource("<array>");
        setType(TokenType.LIST);
    }
    
    public ListToken add(Token token) {
        tokens.add(token);
        return this;
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }
    
    public Stream<Token> stream() {
        return tokens.stream();
    }
    
    public int size() {
        return tokens.size();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Token token: tokens) {
            sb.append(" ").append(token);
        }
        sb.append(" ]");
        return sb.toString();
    }
    
    @Override
    public String toStringShort() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Token token: tokens) {
            sb.append(" ").append(token.toStringShort());
        }
        sb.append(" ]");
        return sb.toString();
    }
}
