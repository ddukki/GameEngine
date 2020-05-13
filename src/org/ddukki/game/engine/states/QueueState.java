package org.ddukki.game.engine.states;

import org.ddukki.game.engine.entities.completion.CompletionQueue;
import org.ddukki.game.engine.entities.completion.QueueAdder;

/**
 * Defines a simple state that contains a completion queue and a series of
 * queues that signal that queues are in process or are complete
 */
public class QueueState extends State {

	CompletionQueue q = new CompletionQueue();
	QueueAdder a = new QueueAdder(q);

	public QueueState() {
		super();
		entities.add(q);
		entities.add(a);
	}
}
