package net.eleritec.utils;

import static net.eleritec.utils.collection.CollectionUtil.asList;
import static net.eleritec.utils.collection.StreamUtil.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class StringUtil {

	public static String emptyIfNull(String text) {
		return text==null? "": text;
	}
	
	public static String trim(String text) {
		return text==null? null: text.trim();
	}
	
	public static String trimOrEmpty(String text) {
		return text==null? "": text.trim();
	}
	
	public static String toUpperCase(String text) {
		return text==null? null: text.toUpperCase();
	}
	
	public static String toLowerCase(String text) {
		return text==null? null: text.toLowerCase();
	}
	
	public static String asString(Object obj) {
		return obj==null? null: obj.toString();
	}
	
	public static boolean isEmpty(String text) {
		return isEmpty(text, true);
	}
	
	public static boolean isEmpty(String text, boolean ignoreWhitespace) {
		String test = ignoreWhitespace? trim(text): text;
		return test==null || test.isEmpty();
	}
	
	public static boolean notEmpty(String text) {
		return !isEmpty(text);
	}
	
	public static boolean notEmpty(String text, boolean ignoreWhitespace) {
		return !isEmpty(text, ignoreWhitespace);
	}
	
	public static boolean isNullish(String text) {
		return isEmpty(text) || text.trim().equalsIgnoreCase("null");
	}
	
	public static String after(String text, String prefix) {
		return after(text, prefix, "");
	}
	
	public static String after(String text, String prefix, boolean returnEmptyIfNotFound) {
		return after(text, prefix, returnEmptyIfNotFound? "": text);
	}
	
	public static String after(String text, String prefix, String defaultValue) {
		if(text!=null && prefix!=null && prefix.length() > 0) {
			int index = text.indexOf(prefix);
			if(index!=-1) {
				return text.substring(index + prefix.length());
			}
		}
		return defaultValue;
	}
	
	public static String afterLast(String text, String prefix) {
		return afterLast(text, prefix, "");
	}
	
	public static String afterLast(String text, String prefix, boolean returnEmptyIfNotFound) {
		return afterLast(text, prefix, returnEmptyIfNotFound? "": text);
	}
	
	public static String afterLast(String text, String prefix, String defaultValue) {
		if(text!=null && prefix!=null && prefix.length() > 0) {
			int index = text.lastIndexOf(prefix);
			if(index!=-1) {
				return text.substring(index + prefix.length());
			}
		}
		return defaultValue;
	}
	
	public static String before(String text, String suffix) {
		return before(text, suffix, "");
	}
	
	public static String before(String text, String suffix, boolean returnEmptyIfNotFound) {
		return before(text, suffix, returnEmptyIfNotFound? "": text);
	}
	
	public static String before(String text, String suffix, String defaultValue) {
		if(StringUtil.notEmpty(text)) {
			int index = text.indexOf(suffix);
			if(index > 0) {
				return text.substring(0, index);
			}
		}
		return defaultValue;
	}
	
	public static String beforeLast(String text, String suffix) {
		return beforeLast(text, suffix, "");
	}
	
	public static String beforeLast(String text, String suffix, boolean returnEmptyIfNotFound) {
		return beforeLast(text, suffix, returnEmptyIfNotFound? "": text);
	}
	
	public static String beforeLast(String text, String suffix, String defaultValue) {
		if(StringUtil.notEmpty(text)) {
			int index = text.lastIndexOf(suffix);
			if(index > 0) {
				return text.substring(0, index);
			}
		}
		return defaultValue;
	}
	
	public static String between(String text, String prefix, String suffix) {
		return between(text, prefix, suffix, false);
	}
	
	public static String between(String text, String prefix, String suffix, boolean toEnd) {
		if(StringUtil.notEmpty(text)) {
			int start = text.indexOf(prefix);
			if(start==-1) {
				return "";
			}
			start += prefix.length();
			
			int end = text.indexOf(suffix, start);
			if(end==-1) {
				if(!toEnd) {
					return "";
				}
				end = text.length();
			}
			return text.substring(start, end);
		}
		return "";
	}
	
	public List<String> splitAndTrim(String text) {
		return splitAndTrim(text, ",");
	}

	public List<String> splitAndTrim(String text, String delimiter) {
		return isEmpty(text)? new ArrayList<String>(0): 
				asList(map(s->trim(s), text.split(delimiter)).filter(StringUtil::notEmpty));
	}
	
	public static <T> List<String> toStrings(Collection<T> items) {
		return asList(map(t->String.valueOf(t), items));
	}
	
	public static <T> Stream<String> lineStream(String text) {
		text = trimOrEmpty(text);
		return map(line->trim(line), text.split("\n"));
	}
	
	public static <T> List<String> line(String text) {
		return asList(lineStream(text));
	}
}
