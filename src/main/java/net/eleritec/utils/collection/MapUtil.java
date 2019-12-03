package net.eleritec.utils.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import net.eleritec.utils.function.FunctionUtil;
import net.eleritec.utils.object.ClassUtil;

public class MapUtil {

	public static <K, V> Map<K, V> mapBy(Collection<V> items, Function<V, K> keyField) {
		Map<K, V> map = new HashMap<K, V>();
		for(V value: items) {
			K key = FunctionUtil.apply(keyField, value, true);
			if(key!=null) {
				map.put(key, value);
			}
		}
		return map;
	}
	
	public static <K, V> Map<K, V> mapBy(V[] items, Function<V, K> keyField) {
		return mapBy(Arrays.asList(items), keyField);
	}
	
	public static <K, I, V> Map<K, V> mapBy(Collection<V> items, Function<V, I> keyField, Function<I, K> tx) {
		Map<K, V> map = new HashMap<K, V>();
		for(V value: items) {
			I intermediate = FunctionUtil.apply(keyField, value, true);
			K key = intermediate==null? null: tx.apply(intermediate);
			if(key!=null) {
				map.put(key, value);
			}
		}
		return map;
	}
	
	public static <K, I, V> Map<K, V> mapBy(V[] items, Function<V, I> keyField, Function<I, K> tx) {
		return mapBy(Arrays.asList(items), keyField, tx);
	}
	
	public static <K, V> Map<K, V> mapBy(Stream<V> values, Function<V, K> keyField) {
		return mapBy(CollectionUtil.asList(values), keyField);
	}
	
	public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
		V value = map.get(key);
		if(value==null) {
			value = defaultValue;
			if(value!=null) {
				map.put(key, value);
			}
		}
		return value;
	}
	
	public static <K, V> V resolve(Map<K, V> map, K key, Function<K, V> generator) {
		V value = map.get(key);
		if(value==null) {
			value = generator==null? null: generator.apply(key);
			if(value!=null) {
				map.put(key, value);
			}
		}
		return value;
	}
	
	@SafeVarargs
	public static <K, V> Set<V> values(Map<K, V> map, K... keys) {
		return values(map, Arrays.asList(keys));
	}

	public static <K, V> Set<V> values(Map<K, V> map, Collection<K> keys) {
		Set<V> values = new HashSet<V>();
		for(K key: keys) {
			V value = key==null? null: map.get(key);
			if(value!=null) {
				values.add(value);
			}
		}
		return values;
	}
	
	@SafeVarargs
	public static <K, V> Map<K, V> getAll(Map<K, V> map, K... keys) {
		return getAll(map, Arrays.asList(keys));
	}

	public static <K, V> Map<K, V> getAll(Map<K, V> map, Collection<K> keys) {
		Map<K, V> subMap = newMap(map.getClass());
		for(K key: keys) {
			V value = key==null? null: map.get(key);
			if(value!=null) {
				subMap.put(key, value);
			}
		}
		return subMap;
	}
	
	private static <K, V> Map<K, V> newMap(@SuppressWarnings("rawtypes") Class<? extends Map> type) {
		@SuppressWarnings("unchecked")
		Map<K, V> newMap = ClassUtil.newInstance(type);
		return newMap==null? newMap(HashMap.class): newMap;
	}

}
