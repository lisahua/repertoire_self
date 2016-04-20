/**
 * @author Lisa Apr 18, 2016 PriorityQueue_base.java 
 */
package examples.heap;

public class MinHeap2 {
	int size;
	int[] queue = new int[100];// we assume the heap will have no more
								// than 100 elements.

	/**
	 * Inserts the specified element into this priority queue.
	 */
	public boolean add(int value) {
		assert value != 0;
		int i = size;
		while (i > 0) {
			int parent = (i - 1) / 2;
			int p_val = queue[parent];
			if (value >= p_val)
				break;
			queue[i] = p_val;
			i = parent;
		}
		queue[i] = value;
		size++;
		return true;
	}

	
}
