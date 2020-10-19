package com.charlware.taulang.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface LinkedList<T> extends Iterable<T> {
	T head();
	LinkedList<T> tail();
	LinkedList<T> push(T t);
	LinkedList<T> pop();
	LinkedList<T> insert(int index, T t);
	LinkedList<T> pushAll(LinkedList<T> list);
	LinkedList<T> reverse();
	LinkedList<T> add(T t);
	T get(int index);
	LinkedList<T> set(int index, T t);
	boolean isEmpty();
	int size();
	
	public default boolean hasTail() {
		return tail() != null;
	}
	
	public default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	
	public default <T> List<T> asList() {
		return (List<T>) stream().collect(Collectors.toList());
	}
	
	public static <T> LinkedList<T> emptyList() {
		return new EmptyLinkedList<T>();
	}
	
	public static <T> LinkedList<T> of(T... arr) {
		if(arr.length == 0)
			return new EmptyLinkedList<T>();
		
		NonEmptyLinkedList<T> newHead = null;
		for(int i = arr.length - 1; i >= 0; i--) {
			NonEmptyLinkedList<T> tail = newHead;
			newHead = new NonEmptyLinkedList<T>(tail, arr[i]);
		}
		return newHead;
	}
	
	public static <T> LinkedList<T> of(List<T> list) {
		if(list.isEmpty())
			return new EmptyLinkedList<T>();
		
		NonEmptyLinkedList<T> newHead = null;
		for(int i = list.size() - 1; i >= 0; i--) {
			NonEmptyLinkedList<T> tail = newHead;
			newHead = new NonEmptyLinkedList<T>(tail, list.get(i));
		}
		return newHead;
	}
}	
