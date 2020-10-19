package com.charlware.taulang.util;

import java.util.Iterator;

public class EmptyLinkedList<T> implements LinkedList<T> {

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				return null;
			}
			
		};
	}

	@Override
	public T head() {
		return null;
	}
	
	@Override
	public LinkedList<T> tail() {
		return this;
	}
	
	@Override
	public LinkedList<T> push(T t) {
		return new NonEmptyLinkedList<>(t);
	}

	@Override
	public LinkedList<T> pop() {
		throw new IndexOutOfBoundsException("Can't pop() from an empty list.");
	}

	@Override
	public LinkedList<T> insert(int index, T t) {
		if(index != 0)
			throw new IndexOutOfBoundsException(index);
		else
			return new NonEmptyLinkedList<>(t);
	}

	@Override
	public LinkedList<T> pushAll(LinkedList<T> list) {
		return list;
	}

	@Override
	public LinkedList<T> reverse() {
		return this;
	}
	
	@Override
	public LinkedList<T> add(T t) {
		return new NonEmptyLinkedList<T>(t);
	}
	
	@Override 
	public T get(int index) {
		throw new IndexOutOfBoundsException(index);
	}
	
	@Override 
	public LinkedList<T> set(int index, T t) {
		throw new IndexOutOfBoundsException(index);
	}
	
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public int size() {
		return 0;
	}

}
