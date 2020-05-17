package org.ddukki.game.engine;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.ddukki.game.engine.states.QueueState;
import org.ddukki.game.ui.events.reactors.KeyedReactor;

/***/
public class Engine extends JFrame {
	/**  */
	private static final long serialVersionUID = 1L;

	public static int FPS = 60;

	public static Timer t;

	public static GamePanel gp = new GamePanel();

	/** Only this entity should parse key events */
	public static KeyedReactor keyFocus = null;

	public static Loop l = new Loop();

	public Engine() {
		final int delay = 1000 / FPS;
		t = new Timer(delay, l);

		t.start();

		// The frame has the key listener, as a JPanel seems to not respond
		addKeyListener(gp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(gp);
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		Loop.currentState = new QueueState();
		Loop.states.add(Loop.currentState);
		new Engine();
	}
}
