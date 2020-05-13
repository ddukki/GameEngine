package org.ddukki.game.engine;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.states.QueueState;
import org.ddukki.game.engine.states.State;
import org.ddukki.game.ui.menu.Menu;

/***/
public class Loop implements ActionListener {

	public static double frameRate = 0;
	public static long lastFrame = 0;

	public static List<State> states = new ArrayList<>();
	public static State currentState;

	public Loop() {
		currentState = new QueueState();
		currentState.addEntity(new Menu());
		states.add(currentState);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final long thisFrame = System.nanoTime();
		final long frame = thisFrame - lastFrame;

		frameRate = 1000_000_000d / frame;

		lastFrame = thisFrame;

		updateState();
		Engine.gp.repaint();
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
