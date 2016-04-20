/**
 * @author Lisa Apr 19, 2016 TestSortedLinkedList.java 
 */
package examples.sortedLinkedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSortedLinkedList {

	private SortedLinkedList getLinkedList() {
		return new SortedLinkedList();
	}

	@Test
	public void test0() {
		SortedLinkedList list = getLinkedList();
		assertEquals(list.header.next, list.header);
		assertEquals(list.size,0);
		
		list.reverse();
		assertEquals(list.header.next, list.header);
		assertEquals(list.size,0);
	}
	@Test
	public void test1() {
		SortedLinkedList list = getLinkedList();
		list.insert(10);
		assertEquals(list.header.next.element,10);
		assertEquals(list.header.next.next,list.header);
		assertEquals(list.size,1);
		
		list.reverse();
		assertFalse(list.hasLoop());
		assertEquals(list.header.next.element,10);
		assertEquals(list.header.next.next,list.header);
		assertEquals(list.size,1);
	}
	@Test
	public void test2() {
		SortedLinkedList list = getLinkedList();
		list.insert(10);
		list.insert(4);
		assertEquals(list.header.next.element,4);
		assertEquals(list.header.next.next.element,10);
		assertEquals(list.header.next.next.next,list.header);
		assertEquals(list.size,2);
		assertFalse(list.hasLoop());
		list.reverse();
		
		assertEquals(list.header.next.element,10);
		assertEquals(list.header.next.next.element,4);
		assertEquals(list.header.next.next.next,list.header);
		assertEquals(list.size,2);
		assertFalse(list.hasLoop());
	}
	
	@Test
	public void test3() {
		SortedLinkedList list = getLinkedList();
		list.insert(10);
		list.insert(4);
		list.insert(6);
		assertEquals(list.header.next.element,4);
		assertEquals(list.header.next.next.element,6);
		assertEquals(list.header.next.next.next.element,10);
		assertEquals(list.header.next.next.next.next,list.header);
		assertEquals(list.size,3);
		assertFalse(list.hasLoop());
		list.reverse();
		assertEquals(list.header.next.element,10);
		assertEquals(list.header.next.next.element,6);
		assertEquals(list.header.next.next.next.element,4);
		assertEquals(list.size,3);
		assertFalse(list.hasLoop());
	}
	@Test
	public void test4() {
		SortedLinkedList list = getLinkedList();
		list.insert(10);
		list.header.next.next = list.header.next;
		assertTrue(list.hasLoop());
	}
	@Test
	public void test5() {
		SortedLinkedList list = getLinkedList();
		list.insert(10);
		list.insert(4);
		
		list.header.next.next.next = list.header.next;
		assertTrue(list.hasLoop());
	}
	@Test
	public void test6() {
		SortedLinkedList list = getLinkedList();
		list.insert(10);
		list.insert(4);
		list.insert(6);
		
		list.header.next.next.next.next = list.header.next;
		assertTrue(list.hasLoop());
	}

}
