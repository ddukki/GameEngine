package org.ddukki.game.util;

public class MatrixUtil {

	public static double[] defaultKernel = { 1.0 / 16, 1.0 / 4, 3.0 / 8,
			1.0 / 4, 1.0 / 16 };

	public static double[] gaussianKernel(final int size, final double sigma) {
		final double sqr2pi = Math.sqrt(2 * Math.PI);
		// ensure size is even and prepare variables
		final int width = size / 2;
		final double[] kernel = new double[width * 2 + 1];
		final double norm = 1.0 / (sqr2pi * sigma);
		final double coefficient = 2 * sigma * sigma;
		double total = 0;

		// set values and increment total
		for (int x = -width; x <= width; x++) {
			total += kernel[width + x] = norm * Math.exp(-x * x / coefficient);
		}

		// divide by total to make sure the sum of all the values is equal to 1
		for (int x = 0; x < kernel.length; x++) {
			kernel[x] /= total;
		}

		return kernel;
	}

	public static double[] mirrorEdge(final double[] m, final int p) {
		final double[] n = new double[p * 2 + m.length];

		for (int i = 0; i < p; i++) {
			n[i] = m[p - i];
			n[n.length - 1 - i] = m[m.length - 1 - p + i];
		}

		for (int i = 0; i < m.length; i++) {
			n[i + p] = m[i];
		}

		return n;
	}

	public static double[][] mirrorEdge(final double[][] m, final int p) {
		final double[][] n = new double[m.length][];

		for (int i = 0; i < m.length; i++) {
			n[i] = mirrorEdge(m[i], p);
		}

		return n;
	}

	public static double[][] threshold(final double[][] m, final double v) {
		final int x = m.length;
		final int y = m[0].length;

		final double[][] n = new double[y][x];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				n[j][i] = m[i][j] > v ? 1 : 0;
			}
		}

		return n;
	}

	public static double[][] transpose(double[][] m) {
		final int x = m.length;
		final int y = m[0].length;

		final double[][] n = new double[y][x];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				n[j][i] = m[i][j];
			}
		}

		return n;
	}

	public static double[] upscale(final double[] m, final int scale) {
		final double[] n = new double[m.length * scale];

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < scale; j++) {
				n[i * scale + j] = m[i];
			}
		}

		return n;
	}

	public static double[][] upscale(final double[][] m, final int scale) {
		final double[][] n = new double[m.length * scale][];

		for (int i = 0; i < m.length; i++) {
			final double[] u = upscale(m[i], scale);
			for (int j = 0; j < scale; j++) {
				n[i * scale + j] = u;
			}
		}

		return n;
	}

	public static double[][] waveletConvolve(final double[] k,
			final double[][] m) {
		final int p = Math.round(k.length / 2.0f);
		final double[][] n = mirrorEdge(m, p);
		final double[][] h = waveletFilter(k, n);

		final double[][] t = transpose(h);

		final double[][] c = mirrorEdge(t, p);
		final double[][] d = waveletFilter(k, c);

		return transpose(d);
	}

	public static double[] waveletFilter(final double[] k, final double[] m) {
		final int p = Math.round(k.length / 2.0f);
		final double[] n = new double[m.length - p * 2];

		for (int i = 0; i < n.length; i++) {

			// Sum the kernel over the matrix
			for (int j = 0; j < k.length; j++) {
				n[i] += k[j] * m[i + j];
			}
		}

		return n;
	}

	public static double[][] waveletFilter(final double[] k,
			final double[][] m) {
		final double[][] n = new double[m.length][];

		for (int i = 0; i < m.length; i++) {
			n[i] = waveletFilter(k, m[i]);
		}

		return n;
	}
}
