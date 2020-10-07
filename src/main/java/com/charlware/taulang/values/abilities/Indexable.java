package com.charlware.taulang.values.abilities;

public interface Indexable<Type, IndexType, ValueType> {
	public static class NotIndexableException extends Exception {
		
	}
	Type put(IndexType index, ValueType value) throws NotIndexableException;
	Type remove(IndexType index) throws NotIndexableException;
	boolean contains(IndexType index) throws NotIndexableException;
	 
}
