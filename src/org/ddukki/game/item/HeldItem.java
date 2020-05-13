package org.ddukki.game.item;

public class HeldItem {
	/** The class of item being held */
	public Item item;

	/** The quantity of the item being held */
	public int quantity;

	public void difference(HeldItem hi) {
		if (hi.item != item) {
			throw new IllegalArgumentException(
					"The two items must be of the same type!");
		}

		quantity -= hi.quantity;
		quantity = quantity < 0 ? 0 : quantity;
	}

	/**
	 * @return whether this held item has enough quantity of the same item as
	 *         the given {@code HeldItem}
	 */
	public boolean enough(HeldItem hi) {
		return item == hi.item && quantity >= hi.quantity;
	}

	/**
	 * @return whether this exactly equals the given item; the class of items
	 *         must be the same and the quantity must be equal
	 */
	public boolean equals(HeldItem hi) {
		return item == hi.item && quantity == hi.quantity;
	}

	public void merge(HeldItem hi) {
		if (hi.item != item) {
			throw new IllegalArgumentException(
					"The two items must be of the same type!");
		}

		// Completely transfer quantity from one to another
		quantity += hi.quantity;
		hi.quantity = 0;
	}
}
