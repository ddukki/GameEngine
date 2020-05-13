package org.ddukki.game.engine.events.reactors;

import org.ddukki.game.engine.events.Event;

/** The general interface for reacting to events */
public interface EventReactor {

	/** React to a general event */
	public void react(Event e);
}
