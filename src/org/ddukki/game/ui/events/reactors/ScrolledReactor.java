package org.ddukki.game.ui.events.reactors;

import org.ddukki.game.ui.events.ScrolledEvent;

public interface ScrolledReactor extends EventReactor {

	/** React specifically to a scrolled event */
	public void react(ScrolledEvent se);

}
