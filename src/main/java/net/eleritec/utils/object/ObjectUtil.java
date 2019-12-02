package net.eleritec.utils.object;

public class ObjectUtil {

	public static <T> T opt(T item, T defaultItem) {
		return item==null? defaultItem: item;
	}
}
