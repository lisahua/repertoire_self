/**
 * @author Lisa Apr 19, 2016 SortedLinkedList.java 
 */
package examples.sortedLinkedList;

/**
 * Adapted From FindBug book
 * 
 * @author lisahua
 *
 */
public class SortedLinkedList {
	Entry header = new Entry();
	int size = 0;

	public SortedLinkedList() {
		header.next = header;

	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public void insert(int val) {
		assert val != 0;
		Entry insert = new Entry();
		insert.element = val;
		Entry e = header;
		while (!e.next.equals(header)) {
			if (e.next.element < val)
				e = e.next;
			else
				break;
		}
		insert.next = e.next;
		e.next = insert;
		size++;
	}

	public void reverse() {
		Entry ln1, ln2, ln3, ln4;
		if (header.next.equals(header))
			return;
		ln1 = header.next;
		ln2 = header.next.next;
		ln3 = header;
		while (!ln2.equals(header)) {
			ln4 = ln2.next;
			ln1.next = ln3;
			ln3 = ln1;
			ln1 = ln2;
			ln2 = ln4;
		}
		ln1.next = ln3;
		header.next = ln1;
	}

	public boolean hasLoop() {
		Entry ln1, ln2;
		if (header.next.equals(header))
			return false;
		ln1 = header;
		ln2 = header;
		while (true) {
			if (ln1.next.equals(header))
				return false;
			else
				ln1 = ln1.next;

		

			if (ln2.next.equals(header) || ln2.next.next.equals(header))
				return false;
			else
				ln2 = ln2.next.next;
			if (ln1 == ln2)
				return true;
		}
	}
}

class Entry {
	int element;
	Entry next;

	Entry() {
		this.element = 0;
		this.next = null;
	}
}