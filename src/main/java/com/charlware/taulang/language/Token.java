/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import java.util.Objects;

/**
 *
 * @author charlvj
 */
public class Token {
    private String source;
    private TokenType type;
    private int startLine;
    private int startCol;
    private int endLine;
    private int endCol;
    private Token nextToken;

    public Token() {
        
    }
    
    public Token(String source, TokenType type) {
        this.source = source;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "Token{" + "source=" + source + ", type=" + type + ", startLine=" + startLine + ", startCol=" + startCol + ", endLine=" + endLine + ", endCol=" + endCol + ", nextToken=" + nextToken + '}';
    }
    
    public String toStringShort() {
        return source;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if (this.startLine != other.startLine) {
            return false;
        }
        if (this.startCol != other.startCol) {
            return false;
        }
        if (this.endLine != other.endLine) {
            return false;
        }
        if (this.endCol != other.endCol) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.nextToken, other.nextToken)) {
            return false;
        }
        return true;
    }

    
    
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TokenType type) {
        this.type = type;
    }
    
    public boolean isType(TokenType type) {
        return this.type == type;
    }

    /**
     * @return the startLine
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * @param startLine the startLine to set
     */
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    /**
     * @return the startCol
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * @param startCol the startCol to set
     */
    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    /**
     * @return the endLine
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * @param endLine the endLine to set
     */
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    /**
     * @return the endCol
     */
    public int getEndCol() {
        return endCol;
    }

    /**
     * @param endCol the endCol to set
     */
    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    /**
     * @return the nextToken
     */
    public Token getNextToken() {
        return nextToken;
    }

    /**
     * @param nextToken the nextToken to set
     */
    public void setNextToken(Token nextToken) {
        this.nextToken = nextToken;
    }
    
    
}
