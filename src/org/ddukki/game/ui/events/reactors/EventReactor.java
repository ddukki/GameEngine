package org.ddukki.game.ui.events.reactors;

import org.ddukki.game.ui.events.Event;

/** The general interface for reacting to events */
public interface EventReactor {

	/** React to a general event */
	public void react(Event e);
}
