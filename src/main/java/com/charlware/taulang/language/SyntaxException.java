package com.charlware.taulang.language;

import java.util.Arrays;
import java.util.Vector;

public class SyntaxException extends Exception {
//	private ProgramStackEntry programEntry;
//	private ProgramStackEntry programEntry;
//	
//	public SyntaxException(String error, ProgramStackEntry programEntry) {
//		super(error);
//		this.programEntry = programEntry;
//	}
//	
//	public SyntaxException(String error, int pos, Object[] program) {
//		super(error);
//		programEntry = new ProgramStackEntry(pos, program);
//	}
//	
//	public static Object[] getAround(Object[] arr, int pos, int radius) {
//		Vector<Object> result = new Vector<Object>(arr.length);
//		for(int i = Math.max(0, pos - radius); i < Math.min(arr.length, pos + radius); i++) {
//			result.add(arr[i]);
//		}
//		return result.toArray();
//	}
//	
//	public String toString() {
//		return "Syntax Error at token " + programEntry.pos + "  " + 
//				Arrays.toString(getAround(programEntry.program, programEntry.pos, 5)) + 
//				"\n\tError: " + super.toString(); 
//	}
}
