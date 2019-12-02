package net.eleritec.utils;

import static net.eleritec.utils.ListComprehension.list;
import static net.eleritec.utils.ListComprehension.range;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListComprehensionTest {

	@Test
	public void testIf() {
		List<Integer> numbers = null;
		List<String> strings = null;
		
		// single IF statement
		// numbers = [ x for x in range(20) if x % 2 == 0]
		numbers = list(range(20), x-> x % 2 == 0);
		assertList(numbers, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18);

		// double IF statement
		// numbers = [y for y in range(100) if y % 2 == 0 if y % 5 == 0]
		numbers = list(range(100), y-> y % 2 == 0, y-> y % 5 == 0); // using two predicates
		assertList(numbers, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90);
		numbers = list(range(100), y-> y % 2 == 0 && y % 5 == 0); // using single predicate
		assertList(numbers, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90);
		
		// ["Even" if i%2==0 else "Odd" for i in range(10)]
		strings = list(i-> i % 2 == 0 ? "Even": "Odd", range(10));
		assertList(strings, "Even", "Odd", "Even", "Odd", "Even", "Odd", "Even", "Odd", "Even", "Odd");
	}

	@Test
	public void testNestedWithMatrixTranspose() {
		List<List<Integer>> transposed = null;
		Integer[][] matrix = { { 1, 2 }, { 3, 4 }, { 5, 6 }, { 7, 8 } };

		// transposed = [[row[i] for row in matrix] for i in range(2)]
		transposed = list(i -> list(row -> row[i], matrix), range(2));
		// expecting [[1, 3, 5, 7], [2, 4, 6, 8]]
		assertEquals(2, transposed.size());
		assertList(transposed.get(0), 1, 3, 5, 7);
		assertList(transposed.get(1), 2, 4, 6, 8);
	}

	@Test
	public void testLettersInString() {
		// list = [ letter for letter in 'human' ]
		assertList(list("human"), "h", "u", "m", "a", "n");
	}

	
	@SafeVarargs
	private final <T> void assertList(List<T> actual, T...expected) {
		assertEquals(Arrays.asList(expected), actual);
	}

}
