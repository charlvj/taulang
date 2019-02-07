/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang;

import com.charlware.taulang.Tokenizer;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.language.TokenType;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author charlvj
 */
public class TokenizerTest extends Tokenizer {
    
    public TokenizerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCurrentToken method, of class Tokenizer.
     */
    @Test
    public void testGetCurrentToken() {
        System.out.println("getCurrentToken");
        tokens = new Token[] {
            new Token("a", TokenType.IDENTIFIER)
        };
        currentToken = -1;
        assertNull(getCurrentToken());
        currentToken = 0;
        assertEquals(tokens[0], getCurrentToken());
        currentToken = 1;
        assertNull(getCurrentToken());
    }

    /**
     * Test of advance method, of class Tokenizer.
     */
    @Test
    public void testAdvance() {
        System.out.println("advance");
        tokens = new Token[] {
            new Token("a", TokenType.IDENTIFIER),
            new Token("b", TokenType.IDENTIFIER)
        };
        currentToken = -1;
        assertTrue(advance());
        assertEquals(tokens[0], getCurrentToken());
        currentToken = 0;
        assertTrue(advance());
        assertEquals(tokens[1], getCurrentToken());
        currentToken = 1;
        assertFalse(advance());
    }

    /**
     * Test of retreat method, of class Tokenizer.
     */
    @Test
    public void testRetreat() {
        System.out.println("retreat");
        tokens = new Token[] {
            new Token("a", TokenType.IDENTIFIER),
            new Token("b", TokenType.IDENTIFIER)
        };
        currentToken = 3;
        assertTrue(retreat());
        assertEquals(tokens[1], getCurrentToken());
        currentToken = 1;
        assertTrue(retreat());
        assertEquals(tokens[0], getCurrentToken());
        currentToken = 0;
        assertFalse(retreat());
    }

    /**
     * Test of hasNextToken method, of class Tokenizer.
     */
    @Test
    public void testHasNextToken() {
        System.out.println("hasNextToken");
        tokens = new Token[] {
            new Token("a", TokenType.IDENTIFIER),
            new Token("b", TokenType.IDENTIFIER)
        };
        currentToken = 0;
        assertTrue(hasNextToken());
        currentToken = 1;
        assertFalse(hasNextToken());
    }

    /**
     * Test of peekNextToken method, of class Tokenizer.
     */
    @Test
    public void testPeekNextToken() {
        System.out.println("peekNextToken");
        tokens = new Token[] {
            new Token("a", TokenType.IDENTIFIER),
            new Token("b", TokenType.IDENTIFIER)
        };
        currentToken = 0;
        assertEquals(tokens[1], peekNextToken());
    }

    /**
     * Test of parseTokens method, of class Tokenizer.
     */
    @Test
    public void testParseTokens() throws IOException {
        System.out.println("parseTokens");
        String code = "test 1 2.0 \"blah\" [ end ]";
        parseTokens(code);
        
        Token t = tokens[0];
        assertEquals("test", t.getSource());
        assertEquals(TokenType.IDENTIFIER, t.getType());
        
        t = tokens[1];
        assertEquals("1.0", t.getSource());
        assertEquals(TokenType.NUMBER, t.getType());
        
        t = tokens[2];
        assertEquals("2.0", t.getSource());
        assertEquals(TokenType.NUMBER, t.getType());
        
        t = tokens[3];
        assertEquals("blah", t.getSource());
        assertEquals(TokenType.STRING, t.getType());
        
        t = tokens[4];
        assertEquals("[", t.getSource());
        assertEquals(TokenType.LEFT_BRACKET, t.getType());
        
        t = tokens[5];
        assertEquals("end", t.getSource());
        assertEquals(TokenType.IDENTIFIER, t.getType());
        
        t = tokens[6];
        assertEquals("]", t.getSource());
        assertEquals(TokenType.RIGHT_BRACKET, t.getType());
    }
    
}
