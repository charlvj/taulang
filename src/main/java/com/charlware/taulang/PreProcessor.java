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
    protected Tokenizer tokens;
    
    public PreProcessor(Tokenizer tokenizer) {
        this.tokens = tokenizer;
    }

    @Override
    public boolean hasNext() {
        return tokens.hasNextToken();
    }

    @Override
    public Token next() {
        tokens.advance();
        Token token = tokens.getCurrentToken();
        if(token.isType(TokenType.LEFT_BRACKET)) {
            token = processList();
        }
        return token;
    }

    @Override
    public Iterator<Token> iterator() {
        return this;
    }
    
    protected Token processList() {
        ListToken list = new ListToken();
        Token token;
        while(tokens.advance()) {
            token = tokens.getCurrentToken();
            
            if(token.isType(TokenType.RIGHT_BRACKET))
                break;
            
            if(token.isType(TokenType.LEFT_BRACKET)) 
                token = processList();

            list.add(token);
        }
        
        if(tokens.eof()) {
            // Reached the end of the code without a right bracket
            // TODO!!
        }
        
        return list;
    }
    
    public static void main(String[] args) throws IOException {
        Tokenizer tokenizer = new Tokenizer();
        PreProcessor pp = new PreProcessor(tokenizer);
        tokenizer.parseTokens("test [ 1 2 ] \"Blah\"");
        for(Token token: pp) {
            System.out.println(token);
        }
    }

}
