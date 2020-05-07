package com.charlware.taulang.values.abilities;

import com.charlware.taulang.values.Value;
import com.charlware.taulang.values.abilities.Multiplyable.NotMultiplyableException;

public interface Divisible<Type> {
	
	public static class NotDivisibleException extends Exception {
		
	}
	
	Type divide(Value a) throws NotDivisibleException;
}	