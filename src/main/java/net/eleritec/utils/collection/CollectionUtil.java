package net.eleritec.utils.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.eleritec.utils.object.ClassUtil;
import net.eleritec.utils.object.ObjectUtil;

public class CollectionUtil {

	public static <T> List<T> asList(Collection<T> collection) {
		return asList(collection, ArrayList.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> asList(Collection<T> collection, @SuppressWarnings("rawtypes") Class<? extends List> typeOfList) {
		if(collection instanceof List<?>) {
			return (List<T>) collection;
		}
		
		typeOfList = (Class<List<T>>) ObjectUtil.opt(typeOfList, ArrayList.class);
		List<T> newList = ClassUtil.newInstance(typeOfList);
		if(newList==null) {
			newList = new ArrayList<T>();
		}
		if(collection!=null) {
			newList.addAll(collection);
		}
		return newList;
	}
	
	public static <T> Set<T> asSet(Collection<T> collection) {
		return asSet(collection, HashSet.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Set<T> asSet(Collection<T> collection, @SuppressWarnings("rawtypes") Class<? extends Set> typeOfSet) {
		if(collection instanceof Set<?>) {
			return (Set<T>) collection;
		}
		
		typeOfSet = (Class<Set<T>>) ObjectUtil.opt(typeOfSet, ArrayList.class);
		Set<T> newSet = ClassUtil.newInstance(typeOfSet);
		if(newSet==null) {
			newSet = new HashSet<T>();
		}
		if(collection!=null) {
			newSet.addAll(collection);
		}
		return newSet;
	}
	
	public static <T> List<T> asList(Stream<T> stream) {
		return stream.collect(Collectors.toList());
	}
	
	public static <T> Set<T> asSet(Stream<T> stream) {
		return stream.collect(Collectors.toSet());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> listOf(Collection<?> items, Class<T> type) {
		return (List<T>) items.stream().filter(obj->ObjectUtil.isType(type, obj)).collect(Collectors.toList());
	}
	
	@SafeVarargs
	public static <T> T firstAvailable(T...items) {
		return firstMatch(items, ObjectUtil::notNull);
	}
	
	public static <T> T firstMatch(T[] items, Predicate<T> filter) {
		return firstMatch(Arrays.asList(items), filter, null);
	}
	
	public static <T> T firstMatch(T[] items, Predicate<T> filter, T defaultValue) {
		return firstMatch(Arrays.asList(items), filter, defaultValue);
	}
	
	public static <T> T firstMatch(Collection<T> items, Predicate<T> filter) {
		return firstMatch(items, filter, null);
	}
	
	public static <T> T firstMatch(Collection<T> items, Predicate<T> filter, T defaultValue) {
		T match = items==null? null: items.stream().filter(filter).findFirst().orElse(null);
		return match==null? defaultValue: match;
	}
}
