package org.ddukki.game.engine;

import javax.swing.JFrame;

import org.ddukki.game.engine.states.QueueState;
import org.ddukki.game.ui.events.reactors.KeyedReactor;

/***/
public class Engine extends JFrame {
	/**  */
	private static final long serialVersionUID = 1L;

	public static int FPS = 60;

	public static GamePanel gp = new GamePanel();

	/** Only this entity should parse key events */
	public static KeyedReactor keyFocus = null;

	public static Loop l = new Loop();

	public Engine() {
		setFocusTraversalKeysEnabled(false);

		// The frame has the key listener, as a JPanel seems to not respond
		addKeyListener(gp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(gp);
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setVisible(true);

		l.start();
	}

	public static void main(String[] args) {
		Loop.currentState = new QueueState();
		Loop.states.add(Loop.currentState);
		new Engine();
	}
}
