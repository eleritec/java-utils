package net.eleritec.utils.object;

import java.lang.reflect.Method;

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
	
	public static Method findMethod(Object target, String name, Class<?>... params) {
		return target == null ? null : ClassUtil.findMethod(target.getClass(), name, params);
	}
}
