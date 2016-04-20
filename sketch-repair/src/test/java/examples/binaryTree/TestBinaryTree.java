/**
 * @author Lisa Apr 20, 2016 TestBinaryTree.java 
 */
package examples.binaryTree;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBinaryTree {
	@Test
	public void test0() {
		BinaryTree tree = new BinaryTree();
		tree.insert(8);
		tree.insert(3);
		tree.insert(6);
		tree.insert(1);
		tree.insert(4);
		tree.insert(7);
		tree.insert(10);
		tree.insert(14);
		tree.insert(13);
		assertFalse(tree.insert(3));
		assertEquals(tree.root.key,8);
		assertEquals(tree.root.left.key,3);
		assertEquals(tree.root.right.key,10);
		assertEquals(tree.root.left.left.key,1);
		assertEquals(tree.root.left.right.key,6);
		
		assertEquals(tree.root.left.right.left.key,4);
		assertEquals(tree.root.left.right.right.key,7);
		assertEquals(tree.root.right.right.key,14);
		assertEquals(tree.root.right.right.left.key,13);
		assertEquals(tree.size,9);
	}
	@Test
	public void test_inorder() {
		BinaryTree tree = new BinaryTree();
		tree.insert(8);
		tree.insert(3);
		tree.insert(6);
		tree.insert(1);
		tree.insert(4);
		tree.insert(7);
		tree.insert(10);
		tree.insert(14);
		tree.insert(13);
		int[] result = tree.inorder();
		
		assertEquals(result[0],1);
		assertEquals(result[1],3);
		assertEquals(result[2],4);
		assertEquals(result[3],6);
		assertEquals(result[4],7);
		assertEquals(result[5],8);
		assertEquals(result[6],10);
		assertEquals(result[7],13);
		assertEquals(result[8],14);
	}
	@Test
	public void test_preorder() {
		BinaryTree tree = new BinaryTree();
		tree.insert(8);
		tree.insert(3);
		tree.insert(6);
		tree.insert(1);
		tree.insert(4);
		tree.insert(7);
		tree.insert(10);
		tree.insert(14);
		tree.insert(13);
		int[] result = tree.preorder();
		
		assertEquals(result[0],8);
		assertEquals(result[1],3);
		assertEquals(result[2],1);
		assertEquals(result[3],6);
		assertEquals(result[4],4);
		assertEquals(result[5],7);
		assertEquals(result[6],10);
		assertEquals(result[7],14);
		assertEquals(result[8],13);
	}
	@Test
	public void test_postorder() {
		BinaryTree tree = new BinaryTree();
		tree.insert(8);
		tree.insert(3);
		tree.insert(6);
		tree.insert(1);
		tree.insert(4);
		tree.insert(7);
		tree.insert(10);
		tree.insert(14);
		tree.insert(13);
		int[] result = tree.postorder();
		
		assertEquals(result[0],1);
		assertEquals(result[1],4);
		assertEquals(result[2],7);
		assertEquals(result[3],6);
		assertEquals(result[4],3);
		assertEquals(result[5],13);
		assertEquals(result[6],14);
		assertEquals(result[7],10);
		assertEquals(result[8],8);
	}
}
