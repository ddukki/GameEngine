package org.ddukki.game.util;

import java.util.Random;

public class RandomNumberGenerator {

	public static Random r = new Random();

	public static double[] generate(final double[] m, final double rangeMin,
			final double rangeMax) {
		final int x = m.length;
		final double range = rangeMax - rangeMin;

		for (int i = 0; i < x; i++) {
			m[i] = r.nextDouble() * range + rangeMin;
		}

		return m;
	}

	public static double[][] generate(final double[][] m, final double rangeMin,
			final double rangeMax) {
		final int x = m.length;

		for (int i = 0; i < x; i++) {
			generate(m[i], rangeMin, rangeMax);
		}

		return m;
	}

	public static void setSeed(final long s) {
		r = new Random(s);
	}
}
