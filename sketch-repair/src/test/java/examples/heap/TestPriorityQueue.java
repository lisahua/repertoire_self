/**
 * @author Lisa Apr 18, 2016 TestPriorityQueue.java 
 */
package examples.heap;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPriorityQueue {
	private PriorityQueue_base getLinkedList() {
		return new PriorityQueue_base();
	}

	@Test
	public void test0() {
		PriorityQueue_base heap = getLinkedList();
		assertEquals(heap.size, 0);
	}

	@Test
	public void test1() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		assertEquals(heap.size, 1);
		assertEquals(heap.queue[0], 9);
	}

	@Test
	public void test2() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		assertEquals(heap.size, 2);
		assertEquals(heap.queue[0], 5);
		assertEquals(heap.queue[1], 9);
	}

	@Test
	public void test3() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		assertEquals(heap.size, 3);
		assertEquals(heap.queue[0], 5);
		assertEquals(heap.queue[1], 9);
		assertEquals(heap.queue[2], 8);
	}

	@Test
	public void test4() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		heap.add(3);
		assertEquals(heap.size, 4);
		assertEquals(heap.queue[0], 3);
		assertEquals(heap.queue[1], 5);
		assertEquals(heap.queue[2], 8);
		assertEquals(heap.queue[3], 9);
	}

	@Test
	public void test5() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		heap.add(3);
		heap.add(1);
		assertEquals(heap.size, 5);
		assertEquals(heap.queue[0], 1);
		assertEquals(heap.queue[1], 3);
		assertEquals(heap.queue[2], 8);
		assertEquals(heap.queue[3], 9);
		assertEquals(heap.queue[4], 5);
	}

	@Test
	public void test6() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		assertEquals(heap.size, 6);
		assertEquals(heap.queue[0], 1);
		assertEquals(heap.queue[1], 3);
		assertEquals(heap.queue[2], 6);
		assertEquals(heap.queue[3], 9);
		assertEquals(heap.queue[4], 5);
		assertEquals(heap.queue[5], 8);
	}

	@Test
	public void test7() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		heap.remove(1);
		assertEquals(heap.size, 5);
		assertEquals(heap.queue[0], 3);
		assertEquals(heap.queue[1], 5);
		assertEquals(heap.queue[2], 6);
		assertEquals(heap.queue[3], 9);
		assertEquals(heap.queue[4], 8);
	}

	@Test
	public void test8() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		heap.remove(1);
		heap.remove(9);
		assertEquals(heap.size, 4);
		assertEquals(heap.queue[0], 3);
		assertEquals(heap.queue[1], 5);
		assertEquals(heap.queue[2], 6);
		assertEquals(heap.queue[3], 8);
	}

	@Test
	public void test9() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(9);
		heap.add(5);
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		heap.remove(1);
		heap.remove(9);
		heap.remove(3);
		assertEquals(heap.size, 3);
		assertEquals(heap.queue[0], 5);
		assertEquals(heap.queue[1], 8);
		assertEquals(heap.queue[2], 6);
	}

	@Test
	public void test11() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		heap.add(9);
		heap.add(5);
		assertEquals(heap.size, 6);
		assertEquals(heap.queue[0], 1);
		assertEquals(heap.queue[1], 6);
		assertEquals(heap.queue[2], 3);
		assertEquals(heap.queue[3], 8);
		assertEquals(heap.queue[4], 9);
		assertEquals(heap.queue[5], 5);
	}

	@Test
	public void test12() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		heap.add(9);
		heap.add(5);
		heap.remove(1);
		assertEquals(heap.size, 5);
		assertEquals(heap.queue[0], 3);
		assertEquals(heap.queue[1], 6);
		assertEquals(heap.queue[2], 5);
		assertEquals(heap.queue[3], 8);
		assertEquals(heap.queue[4], 9);
	}

	@Test
	public void test13() {
		PriorityQueue_base heap = getLinkedList();
		heap.add(8);
		heap.add(3);
		heap.add(1);
		heap.add(6);
		heap.add(9);
		heap.add(5);
		heap.remove(9);
		assertEquals(heap.size, 5);
		assertEquals(heap.queue[0], 1);
		assertEquals(heap.queue[1], 5);
		assertEquals(heap.queue[2], 3);
		assertEquals(heap.queue[3], 8);
		assertEquals(heap.queue[4], 6);
	}

	@Test
	public void test14() {
		int[] value = { 5, 9, 1, 8, 6, 3 };
		PriorityQueue_base heap = new PriorityQueue_base(value);
		heap.heapSort();
		assertEquals(heap.size, 6);
		assertEquals(heap.queue[0], 1);
		assertEquals(heap.queue[1], 6);
		assertEquals(heap.queue[2], 3);
		assertEquals(heap.queue[3], 8);
		assertEquals(heap.queue[4], 9);
		assertEquals(heap.queue[5], 5);
	}

	// @Test
	// public void test15() {
	// PriorityQueue_base heap = getLinkedList();
	// heap.add(8);heap.add(3);heap.add(1);
	// heap.add(6);heap.add(9);heap.add(5);
	// heap.remove(9);
	// assertEquals(heap.size,5);
	// assertEquals(heap.queue[0],1);assertEquals(heap.queue[1],5);
	// assertEquals(heap.queue[2],3);assertEquals(heap.queue[3],8);
	// assertEquals(heap.queue[4],6);
	// }
}
