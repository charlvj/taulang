/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.language.TokenType;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author charlvj
 */
public class PreProcessor implements Iterator<Token>, Iterable<Token> {
    protected Iterator<Token> tokens;
    protected Token currentToken;
    
    public PreProcessor(Iterator<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean hasNext() {
        return tokens.hasNext();
    }

    @Override
    public Token next() {
        currentToken = tokens.next();
        if(currentToken.isType(TokenType.LEFT_BRACKET)) {
        	currentToken = processList();
        }
        return currentToken;
    }

    @Override
    public Iterator<Token> iterator() {
        return this;
    }
    
    protected Token processList() {
        ListToken list = new ListToken();
        list.setStartCol(currentToken.getStartCol());
        list.setStartLine(currentToken.getStartLine());
        
        Token token;
        boolean closed = false;
        while(hasNext()) {
        	token = next();
            
            if(token.isType(TokenType.RIGHT_BRACKET)) {
            	closed = true;
                break;
            }
            
            if(token.isType(TokenType.LEFT_BRACKET)) 
                token = processList();

            list.add(token);
        }
        
        if(!closed && !hasNext()) {
            throw new TauSyntaxError("Reached EOF before the end of a list!", list);
        }
        
        list.setEndCol(currentToken.getEndCol());
        list.setEndLine(currentToken.getEndLine());
        
        return list;
    }
    
    public static void main(String[] args) throws IOException {
        Tokenizer tokenizer = new Tokenizer("test [ 1 2 ] \"Blah\" [1 [\"a\" \"b\"] 4]");
        Iterator<Token> tokens = tokenizer.parseTokens();
        PreProcessor pp = new PreProcessor(tokens);
        for(Token token: pp) {
            System.out.println(token);
        }
    }

}
