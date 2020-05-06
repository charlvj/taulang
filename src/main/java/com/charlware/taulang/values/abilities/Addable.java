package com.charlware.taulang.values.abilities;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.Value;

public interface Addable<Type> {
	
	public static class NotAddableException extends Exception {
		
	}
	
	Type add(Value a) throws NotAddableException;
}
