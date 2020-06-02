package org.ddukki.game.engine.states;

import org.ddukki.game.engine.entities.completion.CompletionItem;
import org.ddukki.game.engine.entities.completion.CompletionQueue;
import org.ddukki.game.ui.ListUI;
import org.ddukki.game.ui.TextFieldUI;
import org.ddukki.game.ui.button.TextButtonUI;
import org.ddukki.game.ui.events.SubmittedEvent;
import org.ddukki.game.ui.events.reactors.SubmittedReactor;

/**
 * Defines a simple state that contains a completion queue and a series of
 * queues that signal that queues are in process or are complete
 */
public class QueueState extends State implements SubmittedReactor {

	CompletionQueue q = new CompletionQueue();
	TextButtonUI addCompletion = new TextButtonUI();

	ListUI items = new ListUI();
	TextFieldUI tf = new TextFieldUI();
	TextButtonUI addList = new TextButtonUI();

	public QueueState() {
		super();
		addCompletion.name = "Add Completion Item";
		addCompletion.x = 650;
		addCompletion.y = 10;
		entities.add(q);
		entities.add(addCompletion);

		items.x = 10;
		items.y = 10;
		items.w = 200;
		items.h = 300;
		tf.x = 10;
		tf.y = 350;
		tf.w = 200;
		tf.h = 20;
		addList.x = 20;
		addList.y = 400;
		addList.name = "Add List Item";
		entities.add(items);
		entities.add(tf);
		entities.add(addList);

		addCompletion.submittedReactors.add(this);
		addList.submittedReactors.add(this);
		tf.submittedReactors.add(this);
	}

	@Override
	public void react(SubmittedEvent se) {
		if (se.source == addCompletion) {
			q.add(new CompletionItem("Item", (int) (1000 * Math.random()), 0));
		}

		if (se.source == addList || se.source == tf) {
			items.addItem(tf.s);
		}
	}
}
