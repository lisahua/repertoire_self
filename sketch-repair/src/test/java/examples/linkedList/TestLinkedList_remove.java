/**
a * @author Lisa Apr 17, 2016 TestLinkedList_6.java 
 */
package examples.linkedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLinkedList_remove {

	private LinkedList_base getLinkedList() {
	// apply to DLLERR1, DLLERR3, DLLERR4
		return  new LinkedList_base();
	}
	@Test
	public void test1() {
		 LinkedList_base list = getLinkedList();
		 list.addFirst(7);
		 list.addFirst(4);
		 list.addFirst(6);
		 
		 assertEquals(list.remove(5),false);
		 assertEquals(list.size,3);
		 assertEquals(list.remove(4),true);
		 assertEquals(list.header.next.element,6);
		 assertEquals(list.header.next.next.element, 7);
		 assertEquals(list.header.previous.element, 7);
		 assertEquals(list.size,2);
	}
	
	@Test
	public void test2() {
		 LinkedList_base list = getLinkedList();
		 list.addFirst(7);
		 
		 assertEquals(list.remove(5),false);
		 assertEquals(list.size,1);
		 assertEquals(list.remove(7),true);
		 assertEquals(list.header.next,list.header);
		 assertEquals(list.header.previous, list.header);
		 assertEquals(list.size,0);
	}
	
	
	@Test
	public void test3() {
		 LinkedList_base list = getLinkedList();
		 list.addFirst(7);
		 list.addFirst(4);
		 list.addFirst(6);
		 
		 assertEquals(list.remove(6),true);
		 assertEquals(list.header.next.element,4);
		 assertEquals(list.header.previous.element,7);
		 assertEquals(list.size,2);
		 
		 assertEquals(list.remove(4),true);
		 assertEquals(list.header.next.element,7);
		 assertEquals(list.header.previous.element,7);
		 assertEquals(list.size,1);
		 
		 assertEquals(list.remove(7),true);
		 assertEquals(list.header.next,list.header);
		 assertEquals(list.header.previous,list.header);
		 assertEquals(list.size,0);
		 
		 assertEquals(list.remove(2),false);
		 assertEquals(list.header.next,list.header);
		 assertEquals(list.header.previous,list.header);
		 assertEquals(list.size,0); 
	}
	
	
}
