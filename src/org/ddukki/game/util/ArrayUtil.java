package org.ddukki.game.util;

import java.util.List;

public class ArrayUtil {

	/** @return an array of indices where the values are true */
	public static int[] indices(boolean[] a) {
		// Count all the true values
		int c = 0;
		for (boolean b : a) {
			c += b ? 1 : 0;
		}

		final int[] i = new int[c];
		for (int j = 0, k = 0; j < a.length; j++) {
			if (a[j]) {
				i[k] = j;
				k++;
			}
		}

		return i;
	}

	public static boolean[] toArray(List<Boolean> l) {
		final int s = l.size();
		final boolean[] a = new boolean[s];
		for (int i = 0; i < s; i++) {
			a[i] = l.get(i);
		}

		return a;
	}
}
