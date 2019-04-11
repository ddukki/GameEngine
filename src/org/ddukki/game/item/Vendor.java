package org.ddukki.game.item;

import java.util.ArrayList;
import java.util.List;

public class Vendor {
	protected int currency = 0;
	protected List<HeldItem> items = new ArrayList<>();

	/**
	 * @param hi the held items that will be sold to this vendor
	 * 
	 * @return whether the transaction is valid and was completed
	 */
	public boolean sellItems(HeldItem hi) {
		// Ensure that the vendor has enough money to buy
		final int val = hi.item.value;
		final int quantity = hi.quantity;
		final int total = val * quantity;

		if (total > currency) {
			return false;
		}

		currency -= total;

		// Add the item to the list
		boolean merged = false;
		for (HeldItem i : items) {
			if (i.item == hi.item) {
				i.merge(hi);
				merged = true;
				break;
			}
		}

		// If no merging happened, add this item to the vendor's list
		if (!merged) {
			items.add(hi);
		}

		return true;
	}

	/**
	 * @param hi the items to be bought from this vendor
	 * 
	 * @return whether the item was properly bought
	 */
	public boolean buyItems(HeldItem hi) {
		// Ensure that the vendor has enough items to sell
		for (HeldItem i : items) {
			if (hi.item == i.item && hi.quantity <= i.quantity) {

				i.quantity -= hi.quantity;
				if (i.quantity == 0) {
					items.remove(i);
				}

				return true;
			}
		}

		return false;
	}
}
