package org.ddukki.game.engine;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.states.State;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.reactors.EventReactor;

/**
 * The main game loop that keeps track and updates all states and entities
 * within the game
 */
public class Loop implements ActionListener, EventReactor {

	public static double frameRate = 0;
	public static long lastFrame = 0;

	public static List<State> states = new ArrayList<>();
	public static State currentState;

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void react(Event e) {
		/*
		 * Loop through the list of states, going in reverse order (in order of
		 * elements visible on screen). It is up to individual states to check
		 * whether the event has already been reacted to or not
		 */
		final int l = states.size();
		for (int i = l - 1; i >= 0; i--) {
			states.get(i).react(e);
		}
	}

	public void start() {

		while (true) {
			final long thisFrame = System.nanoTime();
			final long thisFrameTime = thisFrame - lastFrame;

			final long targetTime = (long) (1000_000_000d / Engine.FPS);
			final long waitTime = (targetTime - thisFrameTime) / 1000_000;

			if (thisFrameTime < targetTime) {
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					System.out.println("Loop interrupt!");
				}
			}

			updateState();
			Engine.gp.repaint();

			final long thisFinalFrame = System.nanoTime();
			final long thisFinalTime = thisFinalFrame - lastFrame;
			frameRate = 1000_000_000d / thisFinalTime;
			lastFrame = thisFinalFrame;
		}
	}

	public void switchState(final int stateIndex) {
		currentState.paused = true;
		currentState = states.get(stateIndex);
	}

	public void updateGraphics(Graphics2D g) {
		currentState.updateGraphic(g);
	}

	public void updateState() {
		currentState.update();
	}
}
