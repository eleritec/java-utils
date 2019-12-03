package net.eleritec.utils.object;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ClassUtil {

	public static <T> T newInstance(Class<T> type, Object...args) {
		try {
			if(args.length==0) {
				return type.newInstance();
			}
			Constructor<T> constructor = getConstructor(type, args);
			return constructor==null? null: constructor.newInstance(args);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <T, R> Function<T, R> defaultFactory(Class<R> type) {
		return (t)->{
			return newInstance(type);
		};
	}
	
	public static <T> Supplier<T> defaultSupplier(Class<T> type) {
		return ()->{
			return newInstance(type);
		};
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getConstructor(Class<T> type, Object...args) {
		List<Constructor<T>> candidates = new ArrayList<Constructor<T>>();
		for(Constructor<?> c: type.getConstructors()) {
			if(isMatch(c, args)) {
				candidates.add((Constructor<T>)c);
			}
		}
		
		switch(candidates.size()) {
		case 0:
			return null;
		case 1:
			return candidates.get(0);
		default:
			return getMostSpecific(candidates, args);
		}
	}
	
	private static <T> Constructor<T> getMostSpecific(List<Constructor<T>> candidates, Object[] args) {
		// TODO: finish writing this method
		return candidates.get(0);
	}
	
	private static boolean isMatch(Constructor<?> constructor, Object[] args) {
		Class<?>[] params = constructor.getParameterTypes();
		if(params.length!=args.length) {
			return false;
		}
		for(int i=0; i<params.length; i++) {
			if(!isMatch(params[i], args[i])) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean isMatch(Class<?> type, Object instance) {
		// check primitives in a separate method, due to auto-casting rules
		if(type.isPrimitive()) {
			return isPrimitiveMatch(type, instance);
		}
		// if we're an object type, then null will always be valid
		if(instance==null) {
			return true;
		}
		return type.isAssignableFrom(instance.getClass());
	}
	
	private static boolean isPrimitiveMatch(Class<?> type, Object instance) {
		if(instance==null || !type.isPrimitive()) {
			return false;
		}
		
		Class<?> primType = getPrimitiveType(instance);
		return type.isAssignableFrom(primType);
	}	
	
	private static Class<?> getPrimitiveType(Object obj) {
		if(obj instanceof Byte) {
			return byte.class;
		}
		if(obj instanceof Short) {
			return short.class;
		}
		if(obj instanceof Integer) {
			return int.class;
		}
		if(obj instanceof Long) {
			return long.class;
		}
		if(obj instanceof Float) {
			return float.class;
		}
		if(obj instanceof Double) {
			return double.class;
		}
		if(obj instanceof Character) {
			return char.class;
		}
		if(obj instanceof Boolean) {
			return boolean.class;
		}
		return null;
	}
}
