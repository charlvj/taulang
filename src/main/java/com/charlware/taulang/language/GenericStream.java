package com.charlware.taulang.language;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.charlware.taulang.values.Value;

public class GenericStream implements IStream {

	private Queue<Value> queue = new ConcurrentLinkedQueue<>();
	
	@Override
	public boolean hasNext() {
		return queue.peek() != null;
	}

	@Override
	public Value next() {
		return Value.of(queue.poll());
	}

	@Override
	public void close() throws IOException {
		queue.clear();
	}

	@Override
	public void write(Value value) throws IOException {
		queue.offer(value);
	}

}
