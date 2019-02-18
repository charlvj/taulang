/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.language.Token;
import com.charlware.taulang.language.TokenType;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author charlvj
 */
public class Tokenizer {

    protected Token[] tokens;
    
    protected int currentToken = -1;
    
    protected Deque<Integer> stack = new ArrayDeque<>();
    
    public Token getCurrentToken() {
        return currentToken >= 0 && currentToken < tokens.length ? tokens[currentToken] : null;
    }
    
    public boolean advance() {
        if(currentToken < 0) {
            currentToken = -1;
        } 

        currentToken++;
        return currentToken < tokens.length;
    }
    
    public boolean retreat() {
        if(currentToken >= tokens.length) {
            currentToken = tokens.length;
        } 
        
        currentToken--;
        return currentToken >= 0;
    }
    
    public boolean hasNextToken() {
        return currentToken < tokens.length - 1;
    }
    
    public boolean eof() {
        return currentToken >= tokens.length;
    }
    
    public Object peekNextToken() {
        if(hasNextToken())
            return tokens[currentToken + 1];
        else
            return null;
    }
    
    public void push() {
        stack.push(currentToken);
    }
    
    public void pop() {
        stack.pop();
    }
    
    public Tokenizer parseTokens(String code) throws IOException {
        List<Token> result = new ArrayList<>();
        Reader reader = new StringReader(code);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        tokenizer.wordChars(':', ':');
        tokenizer.wordChars('?', '?');
        tokenizer.wordChars('$', '$');
        tokenizer.wordChars('_', '_');
        tokenizer.slashStarComments(true);
        
        boolean eof = false;
        do {
            int tt = tokenizer.nextToken();
            String tokStr = tokenizer.sval;
            switch(tt) {
                case StreamTokenizer.TT_EOF:
                    eof = true;
                    break;
                case StreamTokenizer.TT_NUMBER:
                    result.add(new Token(""+tokenizer.nval, TokenType.NUMBER));
                    break;
                case StreamTokenizer.TT_WORD:
                    if(tokStr.startsWith(":")) 
                        result.add(new Token(tokStr, TokenType.SYMBOL));
                    else
                        result.add(new Token(tokStr, TokenType.IDENTIFIER));
                    break;
                case '[':
                    result.add(new Token("[", TokenType.LEFT_BRACKET));
                    break;
                case ']':
                    result.add(new Token("]", TokenType.RIGHT_BRACKET));
                    break;
                case '"':
                    result.add(new Token(tokStr, TokenType.STRING));
                    break;
                default:
//                    result.add(new Token("" + ((char) tt), TokenType.IDENTIFIER));
                    break;
            }
        } while(!eof);
        tokens = result.toArray(new Token[] {});
        return this;
    }
    
    public Tokenizer parseTokens1(String code) {
        String[] splits = code.split("\\s");
        List<Token> result = new ArrayList<>(splits.length);

        boolean inString = false;
        boolean inComment = false;

        StringBuilder currentString = new StringBuilder();
        for (String tok : splits) {
            /* ====== Comments ======= */
            if (!inString && inComment && ("\n".equals(tok) || "%".equals(tok))) {
                // End of a comment.
                inComment = false;
                continue;
            }
            if (!inString && inComment) {
                // In a  comment, so ignore
                continue;
            }
            if (!inString && "%".equals(tok)) {
                // Comment is starting.
                inComment = true;
                continue;
            }

            /* ====== Strings ======= */
            if ("\"".equals(tok)) {
                if (inString) {
                    // End of the string is reached
                    result.add(new Token(currentString.toString(), TokenType.STRING));
                    currentString = new StringBuilder();
                }
                inString = !inString;
                continue;
            }
            if (tok.startsWith("\"")) {
                if (tok.endsWith("\"")) {
                    result.add(new Token(tok.substring(1, tok.length() - 1), TokenType.STRING));
                } else {
                    currentString.append(tok.substring(1));
                    inString = true;
                }
                continue;
            }
            if (tok.endsWith("\"")) {
                currentString.append(" ").append(tok.substring(0, tok.length() - 1));
                result.add(new Token(currentString.toString(), TokenType.STRING));
                currentString = new StringBuilder();
                inString = false;
                continue;
            }
            if (inString) {
                currentString.append(" ").append(tok);
                continue;
            }

            if ("".equals(tok)) {
                continue;
            }

            if(NumberUtils.isParsable(tok)) {
                result.add(new Token(tok, TokenType.NUMBER));
            } 
            else if(tok.equals("[")) {
                result.add(new Token(tok, TokenType.LEFT_BRACKET));
            }
            else if(tok.equals("]")) {
                result.add(new Token(tok, TokenType.RIGHT_BRACKET));
            }
            else if(tok.startsWith(":")) {
                result.add(new Token(tok, TokenType.SYMBOL));
            }
//            else if(tok.equals("true") || tok.equals("false")) {
//                result.add(new Token(tok, TokenType.BOOLEAN));
//            }
            else {
                result.add(new Token(tok, TokenType.IDENTIFIER));
            }
            
        }
        tokens = result.toArray(new Token[] {});
        return this;
    }

    public static void main(String[] args) throws IOException {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.parseTokens("to :printline [:msg] [ print [msg newline] ] printline [ \"Hello World! \" 1 \" Time\" ]");
        for(Token token: tokenizer.tokens) {
            System.out.println(token);
        }
    }
    
}
