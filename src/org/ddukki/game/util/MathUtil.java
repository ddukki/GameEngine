package org.ddukki.game.util;

import java.awt.image.BufferedImage;

public class MathUtil {
	public static double[] minMax(final double[] m) {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < m.length; i++) {
			if (min > m[i]) {
				min = m[i];
			}
			if (max < m[i]) {
				max = m[i];
			}
		}

		return new double[] { min, max };
	}

	public static double[] minMax(final double[][] m) {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < m.length; i++) {
			final double[] mm = minMax(m[i]);
			if (min > mm[0]) {
				min = mm[0];
			}
			if (max < mm[1]) {
				max = mm[1];
			}
		}

		return new double[] { min, max };
	}

	public static double[] normalize(final double[] m) {
		final double[] mm = minMax(m);
		final int x = m.length;

		if (x == 1) {
			return new double[] { 1 };
		}

		final double[] n = new double[x];
		final double r = mm[1] - mm[0];
		for (int i = 0; i < x; i++) {
			n[i] = (m[i] - mm[0]) / r;
		}

		return n;
	}

	public static double[][] normalize(final double[][] m) {
		final double[] mm = minMax(m);
		final int x = m.length;
		final int y = m[0].length;

		final double[][] n = new double[x][y];
		final double r = mm[1] - mm[0];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				n[i][j] = (m[i][j] - mm[0]) / r;
			}
		}

		return n;
	}

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

	public static double[] sumi(double[] a, double[] b) {
		for (int i = 0; i < a.length; i++) {
			a[i] += b[i];
		}

		return a;
	}

	public static double[][] sumi(double[][] a, double[][] b) {
		for (int i = 0; i < a.length; i++) {
			sumi(a[i], b[i]);
		}

		return a;
	}

	public static int[] sumi(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			a[i] = a[i] + b[i];
		}

		return a;
	}

	public static BufferedImage toImage(final double[][] m) {
		final double[][] n = normalize(m);
		final int x = m.length;
		final int y = m[0].length;

		// generate pixels
		BufferedImage b = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		final int[][] p = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				p[i][j] = (0xFF << 24) + (((int) (255 * n[i][j])) << 16)
						+ (((int) (255 * n[i][j])) << 8)
						+ ((int) (255 * n[i][j]));
				b.setRGB(j, i, p[i][j]);
			}
		}

		return b;
	}
}
