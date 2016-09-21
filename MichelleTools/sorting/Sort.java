package sorting;

import sun.applet.Main;

public class Sort {
	// Quicksort
	/**
	 * 
	 * @param arr
	 * @param left
	 *            use 0
	 * @param right
	 *            use length of array-1
	 */
	public static double[] quicksort(double[] arr, int left, int right) {
		int i = left, j = right;
		double tmp;
		double pivot = arr[(left + right) / 2];
		while (i <= j) {

			while (arr[i] < pivot) {
				i++;
			}
			while (arr[j] > pivot) {
				j--;
			}
			//System.out.println("Swap " + i + ":" + res[i] + " and " + j + ":" + res[j]);
			if (i <= j) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j--;
			}
		}
		if (left < j) {
			quicksort(arr, left, j);
		}
		if (right > i) {
			quicksort(arr, i, right);
		}
		return arr;
	}

	public static void main(String[] args) {
		// double[] testArray = { 0.0, 6.0, 10.0, 2.0, 4.0, 29.0, 1.0, 9.0, 5.0,
		// 7.0, 3.0 };
		double[] testArray = { 5.0, 1.0, 2.0, 4.0, 4.2, 3.7 };
		double[] res = quicksort(testArray, 0, testArray.length - 1);
		for (double d : res) {
			System.out.print(d + " ");
		}

	}
}