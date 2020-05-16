package org.ddukki.game.engine.states;

import org.ddukki.game.engine.entities.completion.CompletionQueue;
import org.ddukki.game.engine.entities.completion.QueueAdder;
import org.ddukki.game.ui.ListUI;

/**
 * Defines a simple state that contains a completion queue and a series of
 * queues that signal that queues are in process or are complete
 */
public class QueueState extends State {

	CompletionQueue q = new CompletionQueue();
	QueueAdder a = new QueueAdder(q);
	ListUI tf = new ListUI();

	public QueueState() {
		super();
		// entities.add(q);
		// entities.add(a);

		tf.x = 300;
		tf.y = 300;
		tf.w = 200;
		tf.h = 300;
		entities.add(tf);

		for (int i = 0; i < 20; i++) {
			tf.strings.add("Thing " + (i + 1));
			tf.selected.add(i % 6 == 0);
		}
	}
}
