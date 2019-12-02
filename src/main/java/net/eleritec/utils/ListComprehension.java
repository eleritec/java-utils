package net.eleritec.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListComprehension {
	private static final List<?> EMPTY = Collections.unmodifiableList(Arrays.asList());
	
	public static List<Integer> range(int stop) {
		return range(0, stop, 1);
	}

	public static List<Integer> range(int start, int stop) {
		return range(start, stop, 1);
	}

	public static List<Integer> range(int start, int stop, int step) {
		if(start==stop) {
			return new ArrayList<Integer>();
		}
		
		boolean reverse = stop < start;
		if(reverse) {
			step = -Math.abs(step);
		}
		Predicate<Integer> tester = reverse? 
					(current)->{return current > stop;}: 
					(current)->{return current < stop;};
		
		List<Integer> range = new ArrayList<Integer>();
		int current = start;
		while(tester.test(current)) {
			range.add(current);
			current += step;
		}
		return range;
	}
	
	@SafeVarargs
	public static List<String> list(String text, Predicate<String>...filters) {
		return list(null, characters(text), filters);
	}
	
	@SafeVarargs
	public static <R> List<R> list(Function<String, R> tx, String text, Predicate<String>...filters) {
		return list(tx, characters(text), filters);
	}
	
	@SafeVarargs
	public static <T, R> List<R> list(T[] array, Predicate<T>...filters) {
		return list(null, Arrays.asList(array), filters);
	}
	
	@SafeVarargs
	public static <T, R> List<R> list(Collection<T> collection, Predicate<T>...filters) {
		return list(null, collection, filters);
	}
	
	@SafeVarargs
	public static <T, R> List<R> list(Function<T, R> tx, T[] array, Predicate<T>...filters) {
		return list(tx, Arrays.asList(array), filters);
	}

	@SafeVarargs
	public static <T, R> List<R> list(Function<T, R> tx, Collection<T> collection, Predicate<T>...filters) {
		return stream(tx, collection, filters).collect(Collectors.toList());
	}
	
	@SafeVarargs
	public static <T, R> Set<R> set(T[] array, Predicate<T>...filters) {
		return set(null, Arrays.asList(array), filters);
	}
	
	@SafeVarargs
	public static <T, R> Set<R> set(Function<T, R> tx, T[] array, Predicate<T>...filters) {
		return set(tx, Arrays.asList(array), filters);
	}
	
	@SafeVarargs
	public static <T, R> Set<R> set(Collection<T> collection, Predicate<T>...filters) {
		return set(null, collection, filters);
	}
	
	@SafeVarargs
	public static <T, R> Set<R> set(Function<T, R> tx, Collection<T> collection, Predicate<T>...filters) {
		return stream(tx, collection, filters).collect(Collectors.toSet());
	}
	
	@SuppressWarnings("unchecked")
	@SafeVarargs
	private static <T, R> Stream<R> stream(Function<T, R> tx, Collection<T> collection, Predicate<T>...filters) {
		Stream<T> stream = collection==null? (Stream<T>) EMPTY.stream(): collection.stream();
		stream = stream.filter(consolidate(filters));
		return tx==null? (Stream<R>) stream: stream.map(tx);
	}
	
	@SafeVarargs
	public static <T> int indexOf(List<T> list, Predicate<T>...filters) {
		Predicate<T> matcher = consolidate(filters);
		for(int i=0; list!=null && i<list.size(); i++) {
			if(matcher.test(list.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	@SafeVarargs
	public static <T> int lastIndexOf(List<T> list, Predicate<T>...filters) {
		int lastIndex = -1;
		Predicate<T> matcher = consolidate(filters);
		for(int i=0; list!=null && i<list.size(); i++) {
			if(matcher.test(list.get(i))) {
				lastIndex = i;
			}
		}
		return lastIndex;
	}
	
	private static <T> Predicate<T> consolidate(Predicate<T>[] predicates) {
		return consolidate(Arrays.asList(predicates));
	}
	
	private static <T> Predicate<T> consolidate(List<Predicate<T>> predicates) {
		return new Predicate<T>() {
			@Override
			public boolean test(T t) {
				for(Predicate<T> p: predicates) {
					if(!p.test(t)) {
						return false;
					}
				}
				return true;
			}			
		};
	}
	
	private static String notNull(String text) {
		return text==null? "": text;
	}
	
	private static List<String> characters(String text) {
		text = notNull(text);
		List<String> characters = new ArrayList<String>(); 
		for(int i=0; i<text.length(); i++) {
			characters.add(String.valueOf(text.charAt(i)));
		}
		return characters;
	}
	
}
