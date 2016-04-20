/**
 * @author Lisa Apr 20, 2016 TestQuickSort.java 
 */
package examples.quicksort;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQuickSort {
//	QuickSort quickSort = new QuickSort();

	@Test
	public void test0() {
		int[] values = { 1, 12, 5, 26, 7, 14, 3, 7, 2 };
		QuickSort.quickSort(values);
		assertEquals(values[0], 1);
		assertEquals(values[1], 2);
		assertEquals(values[2], 3);
		assertEquals(values[3], 5);
		assertEquals(values[4], 7);
		assertEquals(values[5], 7);
		assertEquals(values[6], 12);
		assertEquals(values[7], 14);
		assertEquals(values[8], 26);

	}
}
