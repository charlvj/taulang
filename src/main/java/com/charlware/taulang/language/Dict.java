package com.charlware.taulang.language;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.charlware.taulang.values.Value;

public class Dict implements Map<Symbol, Value> {

	Map<Symbol,Value> map;
	
	public Dict(Map.Entry<Symbol, Value> entries) {
		map = Map.ofEntries(entries);
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Value get(Object key) {
		return map.get(key);
	}

	@Override
	public Value put(Symbol key, Value value) {
		throw new UnsupportedOperationException("Cannot modify a Dict. Use putDerived instead.");
	}
	
	public Dict putDerived(Symbol key, Value value) {
//		Map.Entry<Symbol, Value> newEntry =
		return null;
	}

	@Override
	public Value remove(Object key) {
		throw new UnsupportedOperationException("Cannot modify a Dict. Use removeDerived instead.");
	}
	
	public Dict removeDerived(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends Symbol, ? extends Value> m) {
		throw new UnsupportedOperationException("Cannot modify a Dict. Use putAllDerived instead.");
	}
	
	public Dict putAllDerived(Map<? extends Symbol, ? extends Value> m) {
		return null;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Cannot modify a Dict.");
	}

	@Override
	public Set<Symbol> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Value> values() {
		return map.values();
	}

	@Override
	public Set<Entry<Symbol, Value>> entrySet() {
		return map.entrySet();
	}

}
