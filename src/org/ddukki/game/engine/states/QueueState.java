package org.ddukki.game.engine.states;

import org.ddukki.game.engine.entities.completion.CompletionItem;
import org.ddukki.game.engine.entities.completion.CompletionQueue;
import org.ddukki.game.ui.ButtonUI;
import org.ddukki.game.ui.ListUI;
import org.ddukki.game.ui.events.SubmittedEvent;
import org.ddukki.game.ui.events.reactors.SubmittedReactor;

/**
 * Defines a simple state that contains a completion queue and a series of
 * queues that signal that queues are in process or are complete
 */
public class QueueState extends State implements SubmittedReactor {

	CompletionQueue q = new CompletionQueue();
	ButtonUI a = new ButtonUI();
	ListUI tf = new ListUI();

	public QueueState() {
		super();
		a.name = "Add Item";
		a.x = 100;
		a.y = 100;
		entities.add(q);
		entities.add(a);

		tf.x = 300;
		tf.y = 300;
		tf.w = 200;
		tf.h = 300;
		entities.add(tf);

		a.submittedReactors.add(this);

		for (int i = 0; i < 20; i++) {
			tf.strings.add("Thing " + (i + 1));
			tf.selected.add(i % 6 == 0);
		}
	}

	@Override
	public void react(SubmittedEvent se) {
		if (se.source == a) {
			q.add(new CompletionItem("Item", (int) (1000 * Math.random()), 0));
		}

	}
}
