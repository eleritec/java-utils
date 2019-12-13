package net.eleritec.utils.misc;

import static net.eleritec.utils.collection.StreamUtil.*;
import static net.eleritec.utils.misc.SpreadsheetColumnSequence.getSequence;
import static net.eleritec.utils.misc.SpreadsheetColumnSequence.next;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static java.lang.String.*;

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
	
	@Test
	public void testSequenceWithStep() {
		assertList(getSequence("A", 3, 4), "A", "E", "I");
	}
	
	@Test
	public void testComplexFormulae001() {
		List<String> formulae = list(indices(getSequence("J", 10, 2)).map(col->{
			return format("$%s3-sum(%s20:%s100)", col.evenOdd("F", "G"), col.value(), col.value());
		}));

		assertEquals(10, formulae.size());
		assertEquals("$F3-sum(J20:J100)", formulae.get(0));
		assertEquals("$G3-sum(L20:L100)", formulae.get(1));
		assertEquals("$F3-sum(N20:N100)", formulae.get(2));
		assertEquals("$G3-sum(P20:P100)", formulae.get(3));
		assertEquals("$F3-sum(R20:R100)", formulae.get(4));
		assertEquals("$G3-sum(T20:T100)", formulae.get(5));
		assertEquals("$F3-sum(V20:V100)", formulae.get(6));
		assertEquals("$G3-sum(X20:X100)", formulae.get(7));
		assertEquals("$F3-sum(Z20:Z100)", formulae.get(8));
		assertEquals("$G3-sum(AB20:AB100)", formulae.get(9));
	}
	
	@SafeVarargs
	private static <T> void assertList(List<T> actual, T...expected) {
		assertEquals(Arrays.asList(expected), actual);
	}


}
