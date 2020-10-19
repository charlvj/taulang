package com.charlware.taulang.util;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NonEmptyLinkedList<T> implements LinkedList<T> {
	private final NonEmptyLinkedList<T> tail;
	private final T value;
	private final int size;
	
	public NonEmptyLinkedList(T value) {
		this.value = value;
		this.tail = null;
		this.size = 1;
	}
	
	public NonEmptyLinkedList(NonEmptyLinkedList<T> nextNode, T value) {
		this.value = value;
		this.tail = nextNode;
		this.size = tail == null? 1 : tail.size + 1;
	}

	@Override
	public NonEmptyLinkedList<T> tail() {
		return tail;
	}
	
	public T getValue() {
		return value;
	}
	
	@Override 
	public T head() {
		return getValue();
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public NonEmptyLinkedList<T> push(T t) {
		return new NonEmptyLinkedList<>(this, t);
	}
	
	@Override
	public NonEmptyLinkedList<T> pop() {
		return tail;
	}
	
	@Override
	public NonEmptyLinkedList<T> reverse() {
		Iterator<T> it = iterator();
		NonEmptyLinkedList<T> newList = new NonEmptyLinkedList<>(it.next());
		while(it.hasNext()) {
			newList = newList.push(it.next());
		}
		return newList;
	}
	
	@Override
	public NonEmptyLinkedList<T> insert(int index, T t) {
		LinkedListIterator<T> it = llIterator();
		NonEmptyLinkedList<T> reverse = new NonEmptyLinkedList<>(it.next());
		for(int i = 1; i < index; i++) {
			if(!it.hasNext()) {
				throw new IndexOutOfBoundsException(index);
			}
			reverse = reverse.push(it.next());
		}
		NonEmptyLinkedList<T> newList = new NonEmptyLinkedList<T>(it.tail(), t);
		return newList.pushAllInReverse(reverse);
	}
	
	public NonEmptyLinkedList<T> pushAllInReverse(LinkedList<T> list) {
		Iterator<T> it = list.iterator();
		NonEmptyLinkedList<T> newList = this;
		while(it.hasNext()) {
			newList = newList.push(it.next());
		}
		return newList;
	}
	
	@Override
	public NonEmptyLinkedList<T> pushAll(LinkedList<T> list) {
		return pushAllInReverse(list.reverse());
	}

	@Override
	public Iterator<T> iterator() {
		return llIterator();
	}
	
	public LinkedListIterator<T> llIterator() {
		return new LinkedListIterator<T>(this);
	}
	

	@Override
	public LinkedList<T> add(T t) {
		LinkedList<T> list = LinkedList.of(t);
		return list.pushAll(this);
	}
	
	@Override
	public T get(int index) {
		LinkedListIterator<T> it = llIterator();
		for(int i = 0; i < index; i++) {
			if(!it.hasNext()) {
				throw new IndexOutOfBoundsException(index);
			}
			it.next();
		}
		return it.next();
	}
	
	@Override
	public LinkedList<T> set(int index, T t) {
		LinkedListIterator<T> it = llIterator();
		LinkedList<T> reverse = new EmptyLinkedList();
		for(int i = 0; i < index; i++) {
			if(!it.hasNext()) {
				throw new IndexOutOfBoundsException(index);
			}
			reverse = reverse.push(it.next());
		}
		it.next();  // pop off the one to replace
		NonEmptyLinkedList<T> newList = new NonEmptyLinkedList<T>(it.tail(), t);
		return newList.pushAllInReverse(reverse);
	}

}
