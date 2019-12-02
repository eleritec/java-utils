package net.eleritec.utils.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import net.eleritec.utils.function.FunctionUtil;

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

}
