package com.charlware.taulang.values.abilities;

import com.charlware.taulang.values.Value;

public interface Multiplyable<Type> {
	
	public static class NotMultiplyableException extends Exception {
		
	}
	
	Type multiply(Value a) throws NotMultiplyableException;
}	