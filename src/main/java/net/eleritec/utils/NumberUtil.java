package net.eleritec.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class NumberUtil {

	/**
	 * Creates a list of integers from 0 to <code>stop</code>, excluding
	 * <code>stop</code> and incrementing by 1.
	 * @param stop the upper/lower bound of the range, excluded from the final results
	 * @return a list of integers from 0 to <code>stop</code>, excluding
	 * <code>stop</code> and incrementing by 1.
	 * @see #range(int, int, int)
	 */
	public static List<Integer> range(int stop) {
		return range(0, stop, 1);
	}

	/**
	 * Creates a list of integers from <code>start</code> to <code>stop</code>, excluding
	 * <code>stop</code> and incrementing by 1.
	 * @param start  the number to start with
	 * @param stop the upper/lower bound of the range, excluded from the final results
	 * @return a list of integers from <code>start</code> to <code>stop</code>, excluding
	 * <code>stop</code> and incrementing by 1.
	 * @see #range(int, int, int)
	 */
	public static List<Integer> range(int start, int stop) {
		return range(start, stop, 1);
	}

	/**
	 * Creates a list of integers from <code>start</code> to <code>stop</code>, excluding
	 * <code>stop</code> and incrementing or decrementing by <code>step</code> for each 
	 * value within the range.  If start and stop are equal, then this method returns an
	 * empty list.  If stop is less than start, then <code>step</code> will be interpreted
	 * as negative, decrementing instead of incrementing while building the range. If 
	 * <code>step</code> is zero, then this method will return a list containing just the 
	 * start value.
	 * @param start the number to start with
	 * @param stop end upper/lower bound of the range, excluded from the final results
	 * @param step increment/decrement for values within the range.
	 * @return a list of integers from <code>start</code> to <code>stop</code>, excluding
	 * <code>stop</code>.
	 */
	public static List<Integer> range(int start, int stop, int step) {
		if(start==stop) {
			return new ArrayList<Integer>();
		}
		
		boolean reverse = stop < start;
		step = reverse? -Math.abs(step): Math.abs(step);
		if(step==0) {
			return Arrays.asList(start);
		}

		Predicate<Integer> tester = reverse? 
					(current)->{return current > stop;}: 
					(current)->{return current < stop;};
		
		List<Integer> range = new ArrayList<Integer>();
		int current = start;
		while(tester.test(current)) {
			range.add(current);
			current += step;
		}
		return range;
	}
	
}
