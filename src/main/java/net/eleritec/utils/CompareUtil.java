package net.eleritec.utils;
import java.util.Comparator;
import java.util.function.Function;

public class CompareUtil {

	public static enum Inclusion {

		NONE(false, false), 
		LOWER(true, false), 
		UPPER(false, true), 
		BOTH(true, true)
		;
		
		private boolean includeLower;
		private boolean includeUpper;
		
		private Inclusion(boolean includesLower, boolean includesUpper) {
			this.includeLower = includesLower;
			this.includeUpper = includesUpper;
		}

		public boolean isIncludeLower() {
			return includeLower;
		}

		public boolean isIncludeUpper() {
			return includeUpper;
		}
		
	}
	
	public static <T extends Comparable<T>> boolean inRange(T minimum, T maximum, T value) {
		return inRange(minimum, maximum, value, Inclusion.BOTH);
	}

	public static <T extends Comparable<T>> boolean inRange(T minimum, T maximum, T value, Inclusion policy) {
		if(minimum==null || maximum==null || value==null) {
			return false;
		}
		
		if(compare(minimum, maximum)==0) {
			return (policy.isIncludeLower() || policy.isIncludeUpper()) && compare(minimum, value)==0;
		}
		
		return isGreater(value, minimum, policy) && isLess(value, maximum, policy);
	}
	
	public static <T extends Comparable<T>> T fitBetween(T minimum, T maximum, T value) {
		if(minimum==null || maximum==null) {
			return value;
		}
		
		if(compare(minimum, maximum)==0) {
			return minimum;
		}
		
		T result = value;
		if(isLess(result, minimum)) {
			result = minimum;
		}
		if(isGreater(result, maximum)) {
			result = maximum;
		}
		return result;
	}
	
	public static <T extends Comparable<T>> boolean isGreater(T left, T right) {
		return isGreater(left, right, Inclusion.NONE);
	}
	
	public static <T extends Comparable<T>> boolean isGreater(T left, T right, Inclusion policy) {
		policy = policy==null? Inclusion.NONE: policy;
		int comparison = compare(left, right);
		return policy.isIncludeLower()? comparison >=0: comparison>0;
	}
	
	public static <T extends Comparable<T>> boolean isLess(T left, T right) {
		return isLess(left, right, Inclusion.NONE);
	}
	
	public static <T extends Comparable<T>> boolean isLess(T left, T right, Inclusion policy) {
		policy = policy==null? Inclusion.NONE: policy;
		int comparison = compare(left, right);
		return policy.isIncludeUpper()? comparison <=0: comparison<0;
	}
	
	public static <T extends Comparable<T>> T max(T left, T right) {
		return compare(left, right) >= 0? left: right;
	}
	
	public static <T extends Comparable<T>> T min(T left, T right) {
		return compare(left, right) <= 0? left: right;
	}
	
	
	public static <T extends Comparable<T>> int compare (T left, T right) {
		if(left==right) {
			return 0;
		}
		
		if(left==null || right==null) {
			return left==null? -1: 1;
		}
		
		return left.compareTo(right);
	}
	
	public static <T, C extends Comparable<C>> int compare (T left, T right, Function<T, C> comparator) {
		C cLeft = left==null? null: comparator.apply(left);
		C cRight = right==null? null: comparator.apply(right);
		return compare(cLeft, cRight);
	}
	
	
	
	public static <T> Comparator<T> ascender(Function<T, ? extends Comparable<?>> criteria) {
		return comparator(criteria, false);
	}
	
	public static <T> Comparator<T> descender(Function<T, ? extends Comparable<?>> criteria) {
		return comparator(criteria, false);
	}
	
	private static <T, C extends Comparable<C>> Comparator<T> comparator(Function<T, ? extends Comparable<?>> criteria, boolean descending) {
		@SuppressWarnings("unchecked")
		Function<T, C> f = (Function<T, C>) criteria;
		return new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				C left = descending? f.apply(o2): f.apply(o1);
				C right = descending? f.apply(o1): f.apply(o2);
				return CompareUtil.compare(left, right);
			}
		};
	}
	
	
}
