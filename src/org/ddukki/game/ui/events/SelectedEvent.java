package org.ddukki.game.ui.events;

public class SelectedEvent extends Event {

	public boolean[] selection;

	public SelectedEvent(Object src) {
		super(src, "SelectedEvent");
	}
}
