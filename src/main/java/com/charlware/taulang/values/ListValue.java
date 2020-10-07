/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.values.abilities.Addable;

/**
 *
 * @author charlvj
 */
public class ListValue extends AbstractValue<List<Value>> 
	implements Listable<Value>,
			   Addable<Value>{

	public static final ListValue EMPTY_LIST = new ListValue(Collections.EMPTY_LIST);
	
    public ListValue(Token token) {
        super(token);
    }
    
    public ListValue(List<Value> list) {
        super(list);
    }
    
    public ListValue(Value[] list) {
        this(List.of(list));
    }

//    public Iterator<Value> iterator() {
//        return getValue().iterator();
//    }
//
//    public Stream<Value> stream() {
//        return getValue().stream();
//    }

    public List<Value> processToken() throws Exception {
        ListToken listToken = (ListToken) token;
        List<Value> list = new ArrayList<>(listToken.size());
        Iterator<Token> iterator = listToken.iterator();
        Token tok;
        while (iterator.hasNext()) {
            tok = iterator.next();
            Value v = getInterpreter().eval(tok, iterator);
            
            if(getInterpreter().getRuntime().getFlags().isTailCallOptimizationEnabled())
                v = v.realize();
            
            list.add(v);
        }
        return list;
    }

    @Override
    public String asString() throws Exception {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append("[ ");
        for (Value value : getValue()) {
            sb.append(value.asString());
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    public ListToken getListToken() {
        return (ListToken) token;
    }

    @Override
    public int size() {
        try {
            int size = getValue().size();
            return size;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public Value get(int i) {
        try {
            return getValue().get(i);
        } catch (Exception ex) {
            return new NullValue();
        }
    }

    @Override
    public void set(int index, Value elem) {
        try {
            getValue().set(index, elem);
        } catch (Exception ex) {
            // Just do nothing for now
        }
    }
    
    @Override
    public int compareTo(Value o) throws NotComparableException {
        if(o instanceof ListValue) {
        	// compare sizes? I guess?
            int s1 = size();
            int s2 = ((ListValue) o).getValueThrowing(NotComparableException.class).size();
            return Integer.compare(s1, s2);
        }
        else {
        	throw new NotComparableException();
        }
    }

	@Override
	public Value add(Value a) throws NotAddableException {
		List<Value> oldList = getValueThrowing(NotAddableException.class);
		List<Value> newList = new ArrayList<>(oldList);
		newList.add(a);
		return new ListValue(newList);
	}
}
