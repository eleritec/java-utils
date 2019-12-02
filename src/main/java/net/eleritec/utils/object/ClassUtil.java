package net.eleritec.utils.object;

public class ClassUtil {

	public static <T> T newInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
}
