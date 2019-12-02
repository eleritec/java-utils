package net.eleritec.utils.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.eleritec.utils.object.ObjectUtil;

public class StreamUtil {

	public static <T> List<T> list(Stream<T> stream) {
		return stream.collect(Collectors.toList());
	}
	
	public static <T> Set<T> set(Stream<T> stream) {
		return stream.collect(Collectors.toSet());
	}
	
	@SafeVarargs
	public static <T> Stream<T> filter(Collection<T> items, Predicate<T>...predicates) {
		return predicates.length==0? items.stream(): items.stream().filter(combine(predicates));
	}
	
	@SafeVarargs
	public static <T> Stream<T> filter(T[] items, Predicate<T>...predicates) {
		return filter(Arrays.asList(items), predicates);
	}
	
	public static <T, R> Stream<R> map(Function<T, R> transform, Collection<T> items) {
		return items.stream().map(transform);
	}
	
	@SafeVarargs
	public static <T, R> Stream<R> map(Function<T, R> transform, T...items) {
		return map(transform, Arrays.asList(items));
	}
	
	@SafeVarargs
	public static <T> Predicate<T> combine(Predicate<T>...predicates) {
		return combine(Arrays.asList(predicates));
	}
	
	public static <T> Predicate<T> combine(Collection<Predicate<T>> predicates) {
		return new Predicate<T>() {
			@Override
			public boolean test(T t) {
				if(predicates!=null) {
					for(Predicate<T> p: predicates) {
						if(!p.test(t)) {
							return false;
						}
					}
				}
				return true;
			}			
		};
	}
	
	@SafeVarargs
	public static <T> Stream<T> notNull(T...items) {
		return notNull(Arrays.asList(items));
	}

	public static <T> Stream<T> notNull(Collection<T> items) {
		return filter(items, ObjectUtil::notNull);
	}
}
