package org.ddukki.game.item;

/**
 * Definitive equivalences between two HeldItems; not necessarily objectively
 * equal, but defined for bartering or production of items. Depending on the
 * context, the equivalency is not necessarily both ways
 */
public class ItemEquivalence {

	/** The raw material or the first item */
	public HeldItem[] item1;

	/** The produced item or an equivalent item */
	public HeldItem[] item2;
}
