package org.ddukki.game.ui.events.reactors;

import org.ddukki.game.ui.events.SelectedEvent;

public interface SelectedReactor extends EventReactor {

	/** React specifically to a selected event */
	public void react(SelectedEvent se);

}
