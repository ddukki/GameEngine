package org.ddukki.game.util;

import java.util.Random;

public class RNGen {

	public static Random r = new Random();

	/**
	 * Fills the given array, <code>m</code> with generated doubles, which are
	 * randomly generated between the given ranges <code>rangeMin</code>
	 * (inclusive) and <code>rangeMax</code> (exclusive). Note, any elements
	 * already in <code>m</code> will be overwritten.
	 */
	public static double[] generate(final double[] m,
			final double rangeMin,
			final double rangeMax) {
		final int x = m.length;
		final double range = rangeMax - rangeMin;

		for (int i = 0; i < x; i++) {
			m[i] = r.nextDouble() * range + rangeMin;
		}

		return m;
	}

	/**
	 * Fills the given matrix, <code>m</code> with generated doubles, which are
	 * randomly generated between the given ranges <code>rangeMin</code>
	 * (inclusive) and <code>rangeMax</code> (exclusive). Note, any elements
	 * already in <code>m</code> will be overwritten.
	 */
	public static double[][] generate(final double[][] m,
			final double rangeMin,
			final double rangeMax) {
		final int x = m.length;

		for (int i = 0; i < x; i++) {
			generate(m[i], rangeMin, rangeMax);
		}

		return m;
	}

	/**
	 * Generates a single integer whose value is between <code>rangeMin</code>
	 * (inclusive) and <code>rangeMax</code> (exclusive).
	 */
	public static int generate(final int rangeMin, final int rangeMax) {
		int range = rangeMax - rangeMin;
		return r.nextInt(range) + rangeMin;
	}

	public static void setSeed(final long s) {
		r = new Random(s);
	}
}
