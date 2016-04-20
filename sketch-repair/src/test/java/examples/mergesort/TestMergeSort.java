/**
 * @author Lisa Apr 20, 2016 TestMergeSort.java 
 */
package examples.mergesort;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMergeSort {
	@Test
	public void test0() {
		int[] value = { 38, 27, 43, 3, 9, 82, 10 };
		MergeSort.mergeSort(value);
		assertEquals(value[0], 3);
		assertEquals(value[1], 9);
		assertEquals(value[2], 10);
		assertEquals(value[3], 27);
		assertEquals(value[4], 38);
		assertEquals(value[5], 43);
		assertEquals(value[6], 82);
	}

}
