package org.ddukki.game.util;

public class Dice {

	/**
	 * Simulates rolling a die with the given number of sides
	 * 
	 * @param sides
	 *        the number of sides on the simulated die
	 */
	public static int d(int sides) {
		return (int) (sides * Math.random()) + 1;
	}

	/**
	 * Simulates a multiple-die roll
	 * 
	 * @param sides
	 *        an array of the number of sides on each die to roll
	 */
	public static int d(int... sides) {
		final int multi = sides.length;

		int sum = 0;
		for (int m = 0; m < multi; m++) {
			sum += d(sides[m]);
		}

		return sum;
	}

	/**
	 * Simulates a multiple-die roll
	 * 
	 * @param multi
	 *        the number of dice to roll
	 * 
	 * @param sides
	 *        the number of sides on each die
	 */
	public static int md(int multi, int sides) {

		int sum = 0;
		for (int m = 0; m < multi; m++) {
			sum += d(sides);
		}

		return sum;
	}
}
