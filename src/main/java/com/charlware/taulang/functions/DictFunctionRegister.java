package com.charlware.taulang.functions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.charlware.taulang.AbstractRegister;
import com.charlware.taulang.language.ErrorFactory;
import com.charlware.taulang.language.Symbol;
import com.charlware.taulang.values.DictValue;
import com.charlware.taulang.values.ErrorValue;
import com.charlware.taulang.values.ListValue;
import com.charlware.taulang.values.NullValue;
import com.charlware.taulang.values.Value;

public class DictFunctionRegister extends AbstractRegister {

	@Override
	public void registerAll() {
		reg(new GenericFunction1("dict", "data") {
			@Override 
			public Value execute(Value dataValue) throws Exception {
				if(!(dataValue instanceof ListValue)) {
					return new ErrorValue(ErrorFactory.createInvalidParamsError("Dict requires a list of keys and values"));
				}
				Map<Symbol,Value> map = new HashMap<>();
				List<Value> list = ((ListValue) dataValue).getValue();
				Iterator<Value> it = list.iterator();
				while(it.hasNext()) {
					Value keyValue = it.next();
					Value value = it.hasNext() ? it.next() : null;
					Symbol key = Symbol.of(keyValue.asString());
					map.put(key, value);
				}
				return new DictValue(map);
			}
		});
		reg(new GenericFunction2("get", "dictionary", "key") {
			@Override
			public Value execute(Value dictValue, Value keyValue) throws Exception {
				if(!(dictValue instanceof DictValue)) {
					return new ErrorValue(ErrorFactory.createInvalidParamsError("get requires a dict value as first parameter"));
				}
				Map<Symbol,Value> map = ((DictValue) dictValue).getValue();
				Symbol key = Symbol.of(keyValue.asString());
				Value value = map.get(key);
				if(value == null) value = NullValue.NULL;
				return value;
			}
		});
		
		reg(new GenericFunction2("put", "dictionary", "data") {
			@Override
			public Value execute(Value dictValue, Value dataValue) throws Exception {
				if(!(dictValue instanceof DictValue)) {
					return new ErrorValue(ErrorFactory.createInvalidParamsError("get requires a dict value as first parameter"));
				}
				if(!(dataValue instanceof ListValue)) {
					return new ErrorValue(ErrorFactory.createInvalidParamsError("Dict requires a list of keys and values"));
				}
				Map<Symbol,Value> oldMap = ((DictValue) dictValue).getValue();
				Map<Symbol,Value> map = new HashMap<>();
				map.putAll(oldMap);
				
				List<Value> list = ((ListValue) dataValue).getValue();
				Iterator<Value> it = list.iterator();
				while(it.hasNext()) {
					Value keyValue = it.next();
					Value value = it.hasNext() ? it.next() : null;
					Symbol key = Symbol.of(keyValue.asString());
					map.put(key, value);
				}
				return new DictValue(map);
			}
		});
	}

}
