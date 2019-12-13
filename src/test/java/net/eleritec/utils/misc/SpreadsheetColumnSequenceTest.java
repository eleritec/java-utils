package net.eleritec.utils.misc;

import static net.eleritec.utils.misc.SpreadsheetColumnSequence.getSequence;
import static net.eleritec.utils.misc.SpreadsheetColumnSequence.next;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SpreadsheetColumnSequenceTest {

	@Test
	public void testNextWithEmpty() {
		assertEquals("A", next(""));
		assertEquals("A", next("   "));
		assertEquals("A", next(null));
	}
	
	@Test
	public void testSimpleNext() {
		assertEquals("B", next("a"));
		assertEquals("B", next("A"));
		assertEquals("B", next("  \nA \t"));
		assertEquals("B", next(" a   "));
	}
	
	@Test
	public void testNextMultiLetter() {
		assertEquals("FOP", next("foo"));
		assertEquals("BAS", next("bar"));
		assertEquals("BBA", next("baz"));
	}
	
	@Test
	public void testNextGarbage() {
		assertEquals("A", next("a a"));
		assertEquals("A", next("123"));
		assertEquals("A", next("foo*&"));
		assertEquals("A", next("!(&23"));
	}
	
	@Test
	public void testSequenceWithEmptySeed() {
		assertList(getSequence(null, 3), "A", "B", "C");
		assertList(getSequence("", 3), "A", "B", "C");
		assertList(getSequence("    ", 3), "A", "B", "C");
		assertList(getSequence("*&$", 3), "A", "B", "C");
	}
	
	@Test
	public void testSimpleSequence() {
		assertList(getSequence("A", 3), "A", "B", "C");
		assertList(getSequence("Y", 4), "Y", "Z", "AA", "AB");
		assertList(getSequence("foo", 4), "FOO", "FOP", "FOQ", "FOR");
	}
	
	@SafeVarargs
	private static <T> void assertList(List<T> actual, T...expected) {
		assertEquals(Arrays.asList(expected), actual);
	}
}
