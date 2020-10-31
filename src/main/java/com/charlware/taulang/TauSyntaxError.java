package com.charlware.taulang;

import com.charlware.taulang.language.Token;
import com.charlware.taulang.language.TokenType;

public class TauSyntaxError extends TauError {
	public TauSyntaxError(String msg, Token token) {
		super(msg, token);
	}
	
	public TauSyntaxError(String msg, String source, int startCol, int startLine) {
		super(msg, new Token(source, 
				TokenType.UNKNOWN, 
				startLine, 
				startCol, 
				startLine, 
				startCol));
	}
}
