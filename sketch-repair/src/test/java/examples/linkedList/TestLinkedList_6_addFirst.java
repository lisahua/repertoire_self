/**
 * @author Lisa Apr 17, 2016 TestLinkedList_6.java 
 */
package examples.linkedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLinkedList_6_addFirst {

	private LinkedList_6_base getLinkedList() {
	// apply to DLLERR1, DLLERR3, DLLERR4
		return  new LinkedList_6_DLLERR4();
	}
	@Test
	public void test1() {
		 LinkedList_6_base list = getLinkedList();
		 assertEquals(list.header.next,list.header);
		 assertEquals(list.header.previous, list.header);
		 assertEquals(list.size,0);
	}
	
	@Test
	public void test2() {
		 LinkedList_6_base list = getLinkedList();
		 list.addFirst(7);
		 //element 7
		 assertEquals(list.header.next.element,7);
		 assertEquals(list.header.next.next,list.header);
		 assertEquals(list.header.next.previous,list.header);
		 
//		 assertEquals(list.header.previous.element,7);
		 assertEquals(list.size,1);
	}
	@Test
	public void test3() {
		 LinkedList_6_base list = getLinkedList();
		 list.addFirst(7);
		 list.addFirst(4);
		 //element 4
		 assertEquals(list.header.next.element,4);
		 assertEquals(list.header.next.previous,list.header);
		 assertEquals(list.header.next.next.element,7);
		 //element 7
		 assertEquals(list.header.next.next.previous.element,4);
		 assertEquals(list.header.next.next.next,list.header);
		 
//		 assertEquals(list.header.previous.element,7);
	 
		 assertEquals(list.size,2);
	}
	
	@Test
	public void test4() {
		 LinkedList_6_base list = getLinkedList();
		 list.addFirst(7);
		 list.addFirst(4);
		 list.addFirst(6);
		 //element 6
		 assertEquals(list.header.next.element,6);
		 assertEquals(list.header.next.next.element,4);
		 assertEquals(list.header.next.previous,list.header);
		 //element 4
		 assertEquals(list.header.next.next.previous.element,6);
		 assertEquals(list.header.next.next.next.element,7);
		 //element 7
		 assertEquals(list.header.next.next.next.next,list.header);
		 assertEquals(list.header.next.next.next.previous.element,4);
		 
//		 assertEquals(list.header.previous.element,7);
		 
		 assertEquals(list.size,3);
	}
}
