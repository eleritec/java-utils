package net.eleritec.utils.collection;

import static java.util.Arrays.asList;
import static net.eleritec.utils.collection.ListComprehension.expand;
import static net.eleritec.utils.collection.ListComprehension.list;
import static net.eleritec.utils.collection.ListComprehension.range;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ListComprehensionTest {
	
	@Test
	public void testIf() {
		// single IF statement
		// numbers = [ x for x in range(20) if x % 2 == 0]
		List<Integer> numbers = list(range(20), x-> x % 2 == 0);
		assertList(numbers, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18);

		// double IF statement
		// numbers = [y for y in range(100) if y % 2 == 0 if y % 5 == 0]
		numbers = list(range(100), y-> y % 2 == 0, y-> y % 5 == 0); // using two predicates
		assertList(numbers, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90);
		numbers = list(range(100), y-> y % 2 == 0 && y % 5 == 0); // using single predicate
		assertList(numbers, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90);
		
		// ["Even" if i%2==0 else "Odd" for i in range(10)]
		List<String> strings = list(i-> i % 2 == 0 ? "Even": "Odd", range(10));
		assertList(strings, "Even", "Odd", "Even", "Odd", "Even", "Odd", "Even", "Odd", "Even", "Odd");
		
		// find common numbers in 2 lists
		List<Integer> listA = asList(1, 2, 3, 4);
		List<Integer> listB = asList(2, 3, 4, 5);
		// common_num = [a for a in list_a for b in list_b if a == b]
		numbers = list(listA, a-> listB.contains(a));
		assertList(numbers, 2, 3, 4);
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
	public void testSquareCubeList() {
		List<Integer> listA = asList(1, 2, 3);
		// square_cube_list = [ [a**2, a**3] for a in list_a]
		List<List<Integer>> squareCubes =
					list(a->asList(pow(a, 2), pow(a, 3)), listA);
		assertEquals(3, squareCubes.size());
		assertList(squareCubes.get(0), 1, 1);
		assertList(squareCubes.get(1), 4, 8);
		assertList(squareCubes.get(2), 9, 27);
	}
	
	@Test
	public void testSimpleNumberOperations() {
		Integer[] input = {1, 2, 3, 4};
		// numbers = [n**2 for n in numbers]
		List<Integer> numbers = list(n-> n*n, input);
		assertList(numbers, 1, 4, 9, 16);
	}

	@Test
	public void testStringTransform() {
		String[] words = "Hello World In Java".split(" ");
		// strings = [str.lower() for str in list_a]
		List<String> strings = list(str->str.toLowerCase(), words);
		assertList(strings, "hello", "world", "in", "java");
	}
	
	@Test 
	public void testLettersInString() {
		// list = [ letter for letter in 'human' ]
		assertList(list("human"), "h", "u", "m", "a", "n");
	}
	
	@Test
	public void testTuplesNotSameNumber() {
		List<Integer> listA = asList(1, 2, 3);
		List<Integer> listB = asList(2, 7);
		
		// different_num = [(a, b) for a in list_a for b in list_b if a != b]
		List<List<Integer>> notSame = expand(a->list(b->asList(a, b), listB, b-> a!=b), listA);
		
		assertEquals(5, notSame.size());
		assertList(notSame.get(0), 1, 2);
		assertList(notSame.get(1), 1, 7);
		assertList(notSame.get(2), 2, 7);
		assertList(notSame.get(3), 3, 2);
		assertList(notSame.get(4), 3, 7);
	}

	
	@SafeVarargs
	private final <T> void assertList(List<T> actual, T...expected) {
		assertEquals(asList(expected), actual);
	}
	
	private int pow(int number, int exponent) {
		exponent = Math.max(0, exponent);
		if(exponent==0) {
			return 1;
		}
		int value = number;
		for(int i=1; i<exponent; i++) {
			value = value * number;
		}
		return value;
	}

}
