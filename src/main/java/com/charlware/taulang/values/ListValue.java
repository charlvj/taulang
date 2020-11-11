/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.charlware.taulang.language.ListToken;
import com.charlware.taulang.language.Token;
import com.charlware.taulang.util.LinkedList;
import com.charlware.taulang.util.LinkedListIterator;
import com.charlware.taulang.util.NonEmptyLinkedList;
import com.charlware.taulang.values.abilities.Addable;

/**
 *
 * @author charlvj
 */
public class ListValue extends AbstractValue<LinkedList<Value>> 
	implements Listable<Value>,
			   Addable<Value>{

	public static final ListValue EMPTY_LIST = new ListValue(LinkedList.emptyList());
	
    public ListValue(Token token) {
        super(token);
    }
    
    public ListValue(LinkedList<Value> list) {
        super(list);
    }
    
    public ListValue(List<Value> list) {
    	this(LinkedList.of(list));
    }
    
    public ListValue(Value[] list) {
        this(LinkedList.of(list));
    }

//    public Iterator<Value> iterator() {
//        return getValue().iterator();
//    }
//
//    public Stream<Value> stream() {
//        return getValue().stream();
//    }

    public LinkedList<Value> processToken() throws Exception {
        ListToken listToken = (ListToken) token;
        LinkedList<Value> list = LinkedList.emptyList();
        Iterator<Token> iterator = listToken.iterator();
        Token tok;

        while (iterator.hasNext()) {
            tok = iterator.next();
            Value v = getInterpreter().eval(tok, iterator);
            
            if(getInterpreter().getRuntime().getFlags().isTailCallOptimizationEnabled())
                v = v.realize();
            
            list = list.push(v);
        }
        return list.reverse();
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
    public Value head() {
    	try {
            return getValue().head();
        } catch (Exception ex) {
            return new NullValue();
        }
    }
    
    @Override
    public ListValue tail() {
    	try {
            return new ListValue(getValue().tail());
        } catch (Exception ex) {
            return EMPTY_LIST;
        }
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
		LinkedList<Value> list = getValueThrowing(NotAddableException.class);
		return new ListValue(list.add(a));
	}
}
