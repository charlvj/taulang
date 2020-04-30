/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.language;

import com.charlware.taulang.values.Value;
import java.io.Closeable;
import java.util.Iterator;

/**
 *
 * @author charlvj
 */
public interface IStream extends Iterator<Value>, Closeable {
}
