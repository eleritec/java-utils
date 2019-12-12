package net.eleritec.utils.misc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static net.eleritec.utils.misc.SpreadsheetColumnSequence.*;

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
	public void testMultiLetter() {
		assertEquals("FOP", next("foo"));
		assertEquals("BAS", next("bar"));
		assertEquals("BBA", next("baz"));
	}
	
	@Test
	public void testGarbageInput() {
		assertEquals("A", next("a a"));
		assertEquals("A", next("123"));
		assertEquals("A", next("foo*&"));
		assertEquals("A", next("!(&23"));
	}
}
