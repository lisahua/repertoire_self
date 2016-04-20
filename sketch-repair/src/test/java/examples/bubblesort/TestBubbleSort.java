/**
 * @author Lisa Apr 20, 2016 TestBubbleSort.java 
 */
package examples.bubblesort;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBubbleSort {
	@Test
	public void test0() {
		int[] value = { 5, 1, 12, -5, 16 };
		BubbleSort.bubbleSort(value);
		assertEquals(value[0], -5);
		assertEquals(value[1], 1);
		assertEquals(value[2], 5);
		assertEquals(value[3], 12);
		assertEquals(value[4], 16);
	}

}
