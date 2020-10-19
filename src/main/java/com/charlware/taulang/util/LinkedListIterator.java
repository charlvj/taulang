package com.charlware.taulang.util;

import java.util.Iterator;

public class LinkedListIterator<T> implements Iterator<T> {

	private NonEmptyLinkedList<T> head;
	
	public LinkedListIterator(NonEmptyLinkedList<T> head) {
		this.head = new NonEmptyLinkedList<T>(head, null);  // Because the first next() should return the first element
	}
	
	@Override
	public boolean hasNext() {
		return head.hasTail();
	}

	@Override
	public T next() {
		head = head.tail();
		return head.getValue();
	}

	public NonEmptyLinkedList<T> tail() {
		return head.tail();
	}
	
}
