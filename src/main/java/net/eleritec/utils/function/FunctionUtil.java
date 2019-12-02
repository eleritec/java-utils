package net.eleritec.utils.function;

import java.util.function.Function;

public class FunctionUtil {

	public static <T, R> R apply(Function<T, R> function, T input) {
		return apply(function, input, false);
	}
	
	public static <T, R> R apply(Function<T, R> function, T input, boolean silent) {
		if(silent) {
			try {
				return function==null? null: function.apply(input);
			}
			catch(Exception e) {
				return null;
			}	
		}
		return function==null? null: function.apply(input);
	}
}
