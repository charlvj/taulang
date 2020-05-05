package com.charlware.taulang.values.abilities;

import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.Value;

public interface Addable<Type> {
	
	public static class NotAddableException extends Exception {
		
	}
	
	Type add(Value a) throws NotAddableException;
	
//	public static Value add(Value aValue, Value bValue) throws Exception {
//		if(!(aValue instanceof Addable) && !(bValue instanceof Addable))
//    		throw new NotAddableException(aValue, bValue);
//    	
//    	String aType = aValue.getType();
//    	String bType = bValue.getType();
//    	
//    	if()
//		return null;
//	}
}
