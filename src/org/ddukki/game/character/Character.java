package org.ddukki.game.character;

public abstract class Character {
	protected int[] stats;
	protected String name;

	protected int[] maxAttributes;
	protected int[] currAttributes;

	public Character(final String name, final int[] stats,
			final int[] maxAttributes) {
		this.stats = stats;
		this.name = name;

		this.maxAttributes = maxAttributes;
		this.currAttributes = maxAttributes.clone();
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
