package com.charlware.taulang.functions.streams;

import java.io.IOException;

import com.charlware.taulang.language.IStream;
import com.charlware.taulang.values.DoubleValue;
import com.charlware.taulang.values.Value;

public class RandomStream implements IStream {

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Value next() {
		return new DoubleValue(Math.random());
	}

	@Override
	public void close() throws IOException {
		// do nothing.
	}

	@Override
	public void write(Value value) throws IOException {
		throw new IOException("Cannot write to a RandomStream");
	}

}
