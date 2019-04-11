package org.ddukki.game.item.production;

import org.ddukki.game.engine.entities.completion.CompletionItem;

/**  */
public class Production extends CompletionItem {

	public Production(String name, Recipe r) {
		super(name, r.timeCost, 0);
	}
}
