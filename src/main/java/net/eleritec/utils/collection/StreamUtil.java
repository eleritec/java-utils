package net.eleritec.utils.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.eleritec.utils.NumberUtil;
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
		return predicates.length==0? items.stream(): items.stream().filter(all(predicates));
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
	public static <T> Stream<T> notNull(T...items) {
		return notNull(Arrays.asList(items));
	}

	public static <T> Stream<T> notNull(Collection<T> items) {
		return filter(items, ObjectUtil::notNull);
	}
	
	public static <T> Stream<ListIndex<T>> indices(Collection<T> items) {
		List<T> list = CollectionUtil.asList(items);
		return map(i->new ListIndex<T>(i, list.get(i)), NumberUtil.range(list.size()));
	}	
	
	@SafeVarargs
	public static <T> Consumer<T> guard(Consumer<T> consumer, Predicate<T>...filters) {
		return filters.length==0? consumer: (t)->{
			if(all(t, filters)) {
				consumer.accept(t);
			}
		};
	}
	
	@SafeVarargs
	public static <T> boolean all(T candidate, Predicate<T>... predicates) {
		return all(candidate, Arrays.asList(predicates));
	}

	public static <T> boolean all(T candidate, Collection<Predicate<T>> predicates) {
		return all(predicates).test(candidate);
	}
	
	@SafeVarargs
	public static <T> Predicate<T> all(Predicate<T>... predicates) {
		return all(Arrays.asList(predicates));
	}

	public static <T> Predicate<T> all(Collection<Predicate<T>> predicates) {
		return new Predicate<T>() {
			@Override
			public boolean test(T t) {
				return predicates.stream().filter(p->p!=null).allMatch(p->p.test(t));
			}			
		};
	}
	
	@SafeVarargs
	public static <T> boolean any(T candidate, Predicate<T>... predicates) {
		return any(candidate, Arrays.asList(predicates));
	}

	public static <T> boolean any(T candidate, Collection<Predicate<T>> predicates) {
		return any(predicates).test(candidate);
	}
	
	@SafeVarargs
	public static <T> Predicate<T> any(Predicate<T>... predicates) {
		return all(Arrays.asList(predicates));
	}

	public static <T> Predicate<T> any(Collection<Predicate<T>> predicates) {
		return new Predicate<T>() {
			@Override
			public boolean test(T t) {
				return predicates.stream().anyMatch(p->p.test(t));
			}			
		};
	}
	
}
