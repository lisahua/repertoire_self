/**
 * @author Lisa Apr 17, 2016 LinkedList_7.java 
 */
package java.util.examples.linkedList;

/**
 * Adapted from jdk / openjdk / 7-b147 / java.util.LinkedList (@see <a href=
 * "http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b27/java/util/LinkedList.java">
 * LinkedList.java</a>). This file was changed to improve performance in jdk-7
 * (@see
 * <a href="http://hg.openjdk.java.net/jdk7/jdk7/jdk/rev/6d24852165ba">jdk7-
 * revision-6d24852165ba</a>).
 * <p>
 * With sentinel header. I remove the generic type for simplicity. I remove the
 * transient property for simplicity. I assume the int 0 represents null in
 * generic type.
 * </p>
 * 
 * @author lisahua
 *
 */
public class LinkedList_6_correct {
	private Entry header = new Entry();
	private int size = 0;

	public LinkedList_6_correct() {
		header.next = header.previous = header;
	}

	public void addFirst(int val) {
		Entry e = new Entry();
		e.element = val;
		e.next = header.next;
		e.previous = header;
		e.previous.next = e;
		e.next.previous = e;// insert bug here
		size++;// omission error insert here
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public void addLast(int val) {
		Entry e = new Entry();
		e.element = val;
		e.next = header;
		e.previous = header.previous;// insert bug here
		e.previous.next = e;
		e.next.previous = e;// insert bug here
		size++;// omission error insert here
	}

	private int remove(Entry e) {
		if (e == header)
			return 0;

		int result = e.element;
		e.previous.next = e.next;
		e.next.previous = e.previous;
		e.next = null;
		e.previous = null;
		e.element = 0;
		size--;
		return result;
	}

	public boolean remove(int o) {
		for (Entry e = header.next; e != header; e = e.next) {
			if (o == e.element) {
				remove(e);
				return true;
			}
		}
		return false;
	}

	public int removeFirst() {
		return remove(header.next);
	}

	public int removeLast() {
		return remove(header.previous);
	}

	public int indexOf(int o) {
		int index = 0;
		for (Entry e = header.next; e != header; e = e.next) {
			if (o == e.element)
				return index;
			index++;
		}
		return -1;
	}

	public void clear() {
		Entry e = header.next;
		while (e != header) {
			Entry next = e.next;
			e.next = e.previous = null;
			e.element = 0;
			e = next;
		}
		header.next = header.previous = header;
		size = 0;
	}

	public int getFirst() {
		if (size == 0)
			return 0;
		return header.next.element;
	}

	public int getLast() {
		if (size == 0)
			return 0;
		return header.previous.element;
	}

	private static class Entry {
		int element;
		Entry next;
		Entry previous;

		Entry() {
			this.element = 0;
			this.next = null;
			this.previous = null;
		}
	}
}
