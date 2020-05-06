/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import com.charlware.taulang.values.Value;

/**
 *
 * @author charlvj
 */
public interface IStream extends Iterator<Value>, Closeable {
	void write(Value value) throws IOException;
}
