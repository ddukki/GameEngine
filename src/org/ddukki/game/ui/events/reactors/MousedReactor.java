package org.ddukki.game.ui.events.reactors;

import org.ddukki.game.ui.events.MousedEvent;

public interface MousedReactor extends EventReactor {

	/** React specifically to a moused event */
	public void react(MousedEvent me);
}
