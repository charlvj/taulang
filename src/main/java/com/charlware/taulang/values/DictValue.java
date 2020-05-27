package com.charlware.taulang.values;

import java.util.Map;

import com.charlware.taulang.language.Symbol;

public class DictValue extends AbstractValue<Map<Symbol,Value>> {

	public DictValue(Map<Symbol, Value> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Value a) throws NotComparableException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Map<Symbol, Value> processToken() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("[ ");
			getValue().entrySet()
				.stream()
				.forEach(entry -> sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" "));
			sb.append("]");
			return sb.toString();
		}
		catch(Exception e) {
			return "Error: " + e;
		}
	}
	
}
