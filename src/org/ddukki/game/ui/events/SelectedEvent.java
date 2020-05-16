package org.ddukki.game.ui.events;

import org.ddukki.game.engine.events.Event;

public class SelectedEvent extends Event {

	public boolean[] selection;

	public SelectedEvent(Object src) {
		super(src, "SelectedEvent");
	}
}
