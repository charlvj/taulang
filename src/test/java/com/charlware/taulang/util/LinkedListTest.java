package com.charlware.taulang.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class LinkedListTest {

	private LinkedList<Integer> list = null;
	
	@Test
	public void testBasics() {
		list = LinkedList.of(1);
		list = list.push(2);
		list = list.push(3);
		Iterator<Integer> it = list.iterator();
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(3, list.size());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testAddAll() {
		list = LinkedList.of(1, 2, 3);
		LinkedList<Integer> l2 = LinkedList.of(4, 5, 6);
		list = l2.pushAll(list);
		Iterator<Integer> it = list.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
		assertEquals(Integer.valueOf(5), it.next());
		assertEquals(Integer.valueOf(6), it.next());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testInsert() {
		list = new NonEmptyLinkedList<>(1);
		list = list.push(2);
		list = list.push(3);
		list = list.insert(2, 0);
		Iterator<Integer> it = list.iterator();
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(0), it.next());
		assertEquals(Integer.valueOf(1), it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testOfList() {
		list = LinkedList.of(List.of(1, 2, 3));
		Iterator<Integer> it = list.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testOfEmptyList() {
		list = LinkedList.of(new ArrayList<Integer>());
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testIteratorEmpty() {
		list = LinkedList.of();
		assertTrue(list instanceof EmptyLinkedList);
		Iterator<Integer> it = list.iterator();
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testGet() {
		list = LinkedList.of(1, 2, 3);
		assertEquals(Integer.valueOf(1), list.get(0));
		assertEquals(Integer.valueOf(2), list.get(1));
		assertEquals(Integer.valueOf(3), list.get(2));
	}
	
	@Test
	public void testSet() {
		list = LinkedList.of(1, 2, 3);
		list = list.set(0, 3);
		list = list.set(2, 1);
		assertEquals(Integer.valueOf(3), list.get(0));
		assertEquals(Integer.valueOf(2), list.get(1));
		assertEquals(Integer.valueOf(1), list.get(2));
	}
}
