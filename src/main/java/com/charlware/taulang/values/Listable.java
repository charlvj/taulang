/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

/**
 *
 * @author charlvj
 */
public interface Listable<T> {
	public T head();
	public Listable<T> tail();
    public int size();
    public T get(int i);
    public void set(int index, T elem);
}
