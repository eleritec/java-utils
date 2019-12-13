package net.eleritec.utils.misc;

import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SpreadsheetColumnSequence {
	
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String FIRST_LETTER = LETTERS.substring(0, 1);
	
	public static List<String> getSequence(String column, int count) {
		return getSequence(column, count, 1);
	}

	public static List<String> getSequence(String column, int count, int step) {
		column = validate(column);
		count = Math.max(Math.abs(count), 1);

		List<String> sequence = new ArrayList<String>();
		if(column!=null) {
			sequence.add(column);
			count--;
		}
		
		for(int i=0; i<count; i++) {
			String previous = sequence.size()==0? column: sequence.get(sequence.size()-1);
			sequence.add(next(previous, step));
		}
	
		return sequence;
	}
	
	public static String next(String column) {
		return next(column, 1);
	}
	
	public static String next(String column, int step) {
		column = validate(column);
		if(column==null) {
			return FIRST_LETTER;
		}
		
		step = Math.max(Math.abs(step), 1);
		String base = column.substring(0, column.length()-1);
		String nextChar = column.length()==1? column: column.substring(column.length()-1);
		for(int i=0; i<step; i++) {
			nextChar = nextChar(nextChar);
			if(FIRST_LETTER.equals(nextChar)) {
				base = rollover(base);
			}
		}
		
		return base + nextChar;
	}
	
	private static String rollover(String text) {
		if(text.isEmpty()) {
			return FIRST_LETTER;
		}
		
		String base = text.substring(0, text.length()-1);
		String nextChar = nextChar(text.substring(text.length()-1));
		if(FIRST_LETTER.equals(nextChar)) {
			return rollover(base) + nextChar;
		}
		
		return base + nextChar;
	}
	
	private static String nextChar(String current) {;
		int index = LETTERS.indexOf(current) + 1;
		return index >= LETTERS.length()? FIRST_LETTER: valueOf(LETTERS.charAt(index));
	}
	
	private static String validate(String column) {
		if(column==null || column.trim().isEmpty()) {
			return null;
		}
		
		column = column.trim().toUpperCase();
		Stream<String> characters = column.chars().mapToObj(i->valueOf((char)i));
		if(characters.anyMatch(c->!LETTERS.contains(c))) {
			return null;
		}
		
		return column;
	}
}
