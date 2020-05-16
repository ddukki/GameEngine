package org.ddukki.game.ui.events.reactors;

import org.ddukki.game.engine.events.reactors.EventReactor;
import org.ddukki.game.ui.events.KeyedEvent;

public interface KeyedReactor extends EventReactor {

	/** React specifically to a keyed event */
	public void react(KeyedEvent ke);

}
