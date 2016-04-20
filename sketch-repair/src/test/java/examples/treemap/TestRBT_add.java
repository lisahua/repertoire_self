/**
 * @author Lisa Apr 18, 2016 TestTreeMap_add.java 
 */
package examples.treemap;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRBT_add {

	private RedBlackTree_base getTreeMap() {
		// apply to 
			return  new RedBlackTree_base();
		}
	@Test
	public void test1() {
		RedBlackTree_base rbt = getTreeMap();
		assertEquals(rbt.root,null);
		assertEquals(rbt.size,0);
	}
	@Test
	public void test2() {
		RedBlackTree_base rbt = getTreeMap();
		rbt.put(2);
		assertEquals(rbt.root.key,2);
		assertEquals(rbt.size,1);
	}
	
	@Test
	public void test3() {
		RedBlackTree_base rbt = getTreeMap();
		rbt.put(2);
		rbt.put(5);
		assertEquals(rbt.root.key,2);
		assertEquals(rbt.root.color,RedBlackTree_base.BLACK);
		assertEquals(rbt.root.right.key,5);
		assertEquals(rbt.root.right.color,RedBlackTree_base.RED);
		assertEquals(rbt.size,2);
	}
	
	@Test
	public void test4() {
		RedBlackTree_base rbt = getTreeMap();
		rbt.put(-1);
		rbt.put(3);
		rbt.put(6);
		rbt.put(10);
		assertEquals(rbt.root.key,3);
		assertEquals(rbt.root.right.key,6);
		assertEquals(rbt.root.left.key,-1);
		assertEquals(rbt.size,4);
	}
	
	@Test
	public void test5() {
		RedBlackTree_base rbt = getTreeMap();
		rbt.put(2);
		rbt.put(5);
		rbt.put(1);
		assertEquals(rbt.root.key,2);
		assertEquals(rbt.root.right.key,5);
		assertEquals(rbt.root.left.key,1);
		assertEquals(rbt.size,3);
	}
	
	@Test
	public void test6() {
		RedBlackTree_base rbt = getTreeMap();
		rbt.put(2);
		rbt.put(5);
		rbt.put(1);
		assertEquals(rbt.root.key,2);
		assertEquals(rbt.root.right.key,5);
		assertEquals(rbt.root.left.key,1);
		assertEquals(rbt.size,3);
	}
	
	@Test
	public void test7() {
		RedBlackTree_base rbt = getTreeMap();
		rbt.put(2);
		rbt.put(5);
		rbt.put(1);
		assertEquals(rbt.root.key,2);
		assertEquals(rbt.root.right.key,5);
		assertEquals(rbt.root.left.key,1);
		assertEquals(rbt.size,3);
	}
}
