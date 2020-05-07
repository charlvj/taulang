package com.charlware.taulang.values.abilities;

import com.charlware.taulang.values.Value;

public interface Comparable {
	
	public static class NotComparableException extends Exception {
		
	}
	
	int compareTo(Value a) throws NotComparableException;
}
