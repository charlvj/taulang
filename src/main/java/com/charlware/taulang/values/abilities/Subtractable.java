package com.charlware.taulang.values.abilities;

import com.charlware.taulang.values.Value;

public interface Subtractable<Type> {
	public static class NotSubtractableException extends Exception {}
	
	Type subtract(Value a) throws NotSubtractableException;
}
