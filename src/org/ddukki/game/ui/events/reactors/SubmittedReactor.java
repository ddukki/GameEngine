package org.ddukki.game.ui.events.reactors;

import org.ddukki.game.ui.events.SubmittedEvent;

public interface SubmittedReactor extends EventReactor {

	/** The reaction function for a submitted event */
	public void react(SubmittedEvent se);
}
