package com.charlware.taulang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.charlware.taulang.language.Token;
import com.charlware.taulang.language.TokenType;

public class Tokenizer {
	private final char[] code;
	private final int numChars;
	
	private int currentPos = 0;
	private char currentChar = 0;
	private int currentLine = 0;
	
	private List<Token> tokens = new ArrayList<>();
	
	public Tokenizer(String code) {
		this.code = code.toCharArray();
		numChars = this.code.length;
	}
	
	private boolean isEOF() {
		return currentPos >= numChars;
	}
	
	private char next() {
		currentPos++;
		if(!isEOF())
			currentChar = code[currentPos];
		return currentChar;
	}
	
	private void setCurrentPos(int newPos) {
		currentPos = newPos;
		currentChar = code[currentPos];
	}
	
	public Iterator<Token> parseTokens() {
		setCurrentPos(0);
		while(!isEOF()) {
			if(currentChar == '/' && code[currentPos + 1] == '/') {
				lineComment();
			}
			else if(currentChar == '/' && code[currentPos + 1] == '*') {
				blockComment();
			}
			else {
				switch(code[currentPos]) {
				case '[': openBracket(); break;
				case ']': closeBracket(); break;
				case Character.LINE_SEPARATOR: currentLine++; break;
				case '"': string(); break;
				case ':': symbol(); break;
				default:
					if(Character.isDigit(currentChar) || currentChar == '-') {
						number();
					}
					else if(!Character.isWhitespace(currentChar)){
						identifier();
					}
				}
			}
			next();
		}
		
		return tokens.iterator();
	}
	
	private void openBracket() {
		tokens.add(new Token(""+code[currentPos], 
				TokenType.LEFT_BRACKET, 
				currentLine, 
				currentPos, 
				currentLine, 
				currentPos));
	}
	
	private void closeBracket() {
		tokens.add(new Token(""+code[currentPos], 
				TokenType.RIGHT_BRACKET, 
				currentLine, 
				currentPos, 
				currentLine, 
				currentPos));
	}
	
	private void string() {
		int startLine = currentLine;
		int startPos = currentPos;
		
		StringBuilder sb = new StringBuilder();
		
		/* String is all characters until the next " */
		/* Escape char is \ */
		next();
		loop:
		while(!isEOF()) {
			switch(currentChar) {
			case '"': break loop;
			case '\\': 
				char c = escapeChar(next());
				sb.append(c);
				break;
			default:
				sb.append(currentChar);
			}
			next();
		}
		tokens.add(new Token(sb.toString(), 
				TokenType.STRING, 
				startLine, 
				startPos, 
				currentLine, 
				currentPos));
	}
	
	private char escapeChar(char c) {
		switch(c) {
		case 'n': return Character.LINE_SEPARATOR;
		case 't': return '\t';
		case '\\': return '\\';
		default: return c;
		}
	}
	
	private boolean isSeparator(char c) {
		return Character.isWhitespace(c) || c == ']';
	}
	
	private int findNextSeparator() {
		int i;
		for(i = currentPos; i < numChars; i++) {
			if(isSeparator(code[i]))
				break;
		}
		return i;
	}
	
	private String readUntilSeparator() {
		int lastCharPos = findNextSeparator() -1;
		String s = String.copyValueOf(code, currentPos, lastCharPos - currentPos + 1);
		setCurrentPos(lastCharPos);
		return s;
	}
	
	private void symbol() {
		int pos = currentPos;
		next();
		String symbolName = readUntilSeparator();
		tokens.add(new Token(symbolName, 
				TokenType.SYMBOL, 
				currentLine, 
				pos, 
				currentLine, 
				currentPos));
	}
	
	private void lineComment() {
		next();
		next();  // Two chars were matched.
		while(!isEOF()) {
			if(Character.LINE_SEPARATOR == currentChar)
				break;
			next();
		}
	}
	
	private void blockComment() {
		next();
		next();  // Two chars were matched.
		while(!isEOF()) {
			if(currentChar == '*' && code[currentPos + 1] == '/')
				break;
			next();
		}
		next();
	}
	
	private void number() {
		int pos = currentPos;
		String value = readUntilSeparator();
		if(NumberUtils.isParsable(value)) {
			double d = NumberUtils.createDouble(value);
			if ((d == Math.floor(d)) && !Double.isInfinite(d)) {
				tokens.add(
						new Token(value, 
							TokenType.INTEGER, 
							currentLine, 
							pos, 
							currentLine, 
							currentPos));
        	}
        	else {
        		tokens.add(
        				new Token(value, 
        					TokenType.DOUBLE, 
        					currentLine, 
        					pos, 
        					currentLine, 
        					currentPos));
        	}
		}
		else {
			throw new TauSyntaxError("Expected a number, got '" + value + "'", value, pos, currentLine);
		}
	}
	
	private void identifier() {
		int pos = currentPos;
		String symbolName = readUntilSeparator();
//		System.out.println("identifier: " + symbolName);
		tokens.add(new Token(symbolName, 
				TokenType.IDENTIFIER, 
				currentLine, 
				pos, 
				currentLine, 
				currentPos));
	}
	
	
	public static void main(String[] args) {
		String code = "to :greet_me [:name] /* comment */ [ printline [ \"Hello \" name ] add -3d -2.3";
		Tokenizer tokenizer = new Tokenizer(code);
		Iterator<Token> tokens = tokenizer.parseTokens();
		while(tokens.hasNext()) {
			System.out.println(tokens.next());
		}
	}
}
