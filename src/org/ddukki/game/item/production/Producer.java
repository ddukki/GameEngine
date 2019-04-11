package org.ddukki.game.item.production;

import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.item.HeldItem;
import org.ddukki.game.item.ItemEquivalence;

public class Producer {

	protected List<Recipe> recipes = new ArrayList<>();

	protected List<HeldItem> rawMaterials = new ArrayList<>();
	protected List<HeldItem> producedItems = new ArrayList<>();

	/**  */
	public void produceItems(final Recipe r, final int quantity) {
		
	}
}
