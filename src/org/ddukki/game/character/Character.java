package org.ddukki.game.character;

/**
 * The class that defines a character that has attributes and stats; the
 * character may be any creature or hero and is a general way to define a
 * sentient entity that can interact with the game
 */
public abstract class Character {

	/** The statistics for how the character interacts with the game */
	protected int[] stats;

	/** The name of the character */
	protected String name;

	/**
	 * The attributes that may be depleted at some point; these are the maximum
	 * values
	 */
	protected int[] maxAttributes;

	/**
	 * The attributes that may be depleted at some point; these are the current
	 * values
	 */
	protected int[] currAttributes;

	public Character(final String name,
			final int[] stats,
			final int[] maxAttributes) {
		this.stats = stats;
		this.name = name;

		this.maxAttributes = maxAttributes;
		this.currAttributes = maxAttributes.clone();
	}

	public int[][] getAttributes() {
		return new int[][] { currAttributes, maxAttributes };
	}

	public int getCurrentAttribute(final int attr) {
		return currAttributes[attr];
	}

	public int getMaxAttribute(final int attr) {
		return maxAttributes[attr];
	}

	public String getName() {
		return name;
	}

	public int[] getStats() {
		return stats;
	}

	public void setAttributes(int[][] attr) {
		if (attr.length != 2) {
			throw new IllegalArgumentException(
					"There must be exactly two arrays in the attributes matrix");
		}

		currAttributes = attr[0];
		maxAttributes = attr[1];
	}

	public void setCurrentAttribute(final int attr, final int val) {
		currAttributes[attr] = val;
	}

	public void setMaxAttribute(final int attr, final int val) {
		maxAttributes[attr] = val;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStats(final int[] stats) {
		this.stats = stats;
	}
}
