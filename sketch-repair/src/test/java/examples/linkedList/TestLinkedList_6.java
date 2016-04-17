/**
 * @author Lisa Apr 17, 2016 TestLinkedList_6.java 
 */
package examples.linkedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLinkedList_6 {

	private LinkedList_6_base getLinkedList() {
		return  new LinkedList_6_DLLERR1();
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
		 assertEquals(list.header.next.element,7);
		 assertEquals(list.header.previous.element,7);
		 
		 assertEquals(list.header.next.next,list.header);
		 assertEquals(list.header.next.previous,list.header);
		 
		 assertEquals(list.header.previous.next,list.header);
		 assertEquals(list.header.previous.previous.element,7);
		 
		 assertEquals(list.size,1);
	}
	@Test
	public void test3() {
		 LinkedList_6_base list = getLinkedList();
		 list.addFirst(7);
		 list.addFirst(4);
		 assertEquals(list.header.next.element,4);
		 assertEquals(list.header.previous.element,7);

		 assertEquals(list.header.next.previous,list.header);
		 assertEquals(list.header.next.next.element,7);
		 
		 assertEquals(list.header.previous.next,list.header);
		 assertEquals(list.header.previous.previous.element,4);
		 
		 assertEquals(list.header.next.next.previous.element,4);
		 assertEquals(list.header.next.next.next,list.header);
		 
		 assertEquals(list.size,2);
	}
}
