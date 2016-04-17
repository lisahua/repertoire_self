/**
 * @author Lisa Apr 17, 2016 LinkedList_7.java 
 */
package examples.linkedList;

import java.util.NoSuchElementException;

/**
 * Adapted from jdk / openjdk / 7-b147 / java.util.LinkedList (@see <a href=
 * "http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b27/java/util/LinkedList.java">
 * LinkedList.java</a>). This file was changed to improve performance in jdk-7
 * (@see
 * <a href="http://hg.openjdk.java.net/jdk7/jdk7/jdk/rev/6d24852165ba">jdk7-
 * revision-6d24852165ba</a>).
 * <p>
 *  I remove the transient property for
 * simplicity. I assume the int 0 represents null in generic type.
 * </p>
 * 
 * @author lisahua
 *
 */
public class LinkedList_6_jdk<E> {
	private Entry<E> header = new Entry<E>(null, null, null);
	private int size = 0;

	public LinkedList_6_jdk() {
		header.next = header.previous = header;
	}

	private Entry<E> addBefore(E e, Entry<E> entry) {
		Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
		newEntry.previous.next = newEntry;
		newEntry.next.previous = newEntry;
		size++;
		return newEntry;
	}

	public void addFirst(E e) {
		addBefore(e, header.next);
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	public void addLast(E e) {
		addBefore(e, header);
	}

	private E remove(Entry<E> e) {
		if (e == header)
			throw new NoSuchElementException();

		E result = e.element;
		e.previous.next = e.next;
		e.next.previous = e.previous;
		e.next = e.previous = null;
		e.element = null;
		size--;
		return result;
	}

	public boolean remove(Object o) {
        if (o==null) {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (e.element==null) {
                    remove(e);
                    return true;
                }
            }
        } else {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }
        }
        return false;
    }


	public E removeFirst() {
		return remove(header.next);
	}

	public E removeLast() {
		return remove(header.previous);
	}

	public int indexOf(Object o) {
        int index = 0;
        if (o==null) {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (e.element==null)
                    return index;
                index++;
            }
        } else {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (o.equals(e.element))
                    return index;
                index++;
            }
        }
        return -1;
    }


	public void clear() {
		Entry<E> e = header.next;
		while (e != header) {
			Entry<E> next = e.next;
			e.next = e.previous = null;
			e.element = null;
			e = next;
		}
		header.next = header.previous = header;
		size = 0;
	}

	public E getFirst() {
		if (size == 0)
			throw new NoSuchElementException();
		return header.next.element;
	}

	public E getLast() {
		if (size == 0)
			throw new NoSuchElementException();
		return header.previous.element;
	}

	private static class Entry<E> {
		E element;
		Entry<E> next;
		Entry<E> previous;

		Entry(E element, Entry<E> next, Entry<E> previous) {
			this.element = element;
			this.next = next;
			this.previous = previous;
		}
	}
}
