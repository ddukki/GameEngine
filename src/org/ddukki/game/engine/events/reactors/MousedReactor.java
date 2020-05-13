package org.ddukki.game.engine.events.reactors;

import org.ddukki.game.engine.events.MousedEvent;

public interface MousedReactor extends EventReactor {

	/** React specifically to a moused event */
	public void react(MousedEvent me);
}
