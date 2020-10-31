package com.charlware.taulang;

import com.charlware.taulang.language.Token;

public class TauError extends RuntimeException {
	private final Token token;
	
	public TauError(String message, Token token) {
		super(message);
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[TauError] ")
			.append(getMessage())
			.append(".  Token <")
			.append(token.getSource())
			.append("> @ ")
			.append(token.getStartCol())
			.append(";")
			.append(token.getStartLine());
		return sb.toString();
	}
}
