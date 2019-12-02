package net.eleritec.utils.object;

import java.util.function.Function;
import java.util.function.Supplier;

public class ClassUtil {

	public static <T> T newInstance(Class<T> type) {
		try {
			return type.newInstance();
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
}
