package org.ddukki.game.util;

public class MathUtil {
	public static int sum(int[] a) {
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}

		return sum;
	}

	public static int[] sum(int[] a, int[] b) {
		final int[] c = new int[a.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = a[i] + b[i];
		}

		return c;
	}

	public static int[] sumi(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			a[i] = a[i] + b[i];
		}

		return a;
	}
}
