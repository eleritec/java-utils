package net.eleritec.utils.object;

public class ObjectUtil {

	public static <T> T opt(T item, T defaultItem) {
		return item==null? defaultItem: item;
	}
	
	public static boolean isType(Class<?> type, Object obj) {
		return obj!=null && type!=null && type.isAssignableFrom(obj.getClass());
	}
	
	public static boolean isNull(Object object) {
		return object==null;
	}
	
	public static boolean notNull(Object object) {
		return !isNull(object);
	}
}
