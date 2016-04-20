/**
 * @author Lisa Apr 20, 2016 QuickSort.java 
 */
package examples.quicksort;

public class QuickSort {

	public static void quickSort(int[] values) {
		recurQuickSort(values, 0, values.length - 1);
	}

	private static void recurQuickSort(int[] array, int start, int end) {
		if (start >= end)
			return;
		int temp;
		int pivot = (start+end)/2;
		int low = start + 1;
		int high = end;
		while (true) {
			while ((low < high) && array[low] <= array[pivot])
				++low;
			while ((low <= high) && array[high] > array[pivot])
				--high;
			if (low < high) {
				temp = array[low];
				array[low] = array[high];
				array[high] = temp;
			} else
				break;
		}
		temp = array[pivot];
		array[pivot] = array[high];
		array[high] = temp;
		recurQuickSort(array, start, high-1);
		recurQuickSort(array, high + 1, end);
	}

}
