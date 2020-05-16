package org.ddukki.game.engine;

import javax.swing.JFrame;
import javax.swing.Timer;

import org.ddukki.game.ui.events.reactors.KeyedReactor;

/***/
public class Engine {
	public static int FPS = 60;

	public static Timer t;

	public static GamePanel gp = new GamePanel();

	/** Only this entity should parse key events */
	public static KeyedReactor keyFocus = null;

	public static Loop l = new Loop();

	public static void main(String[] args) {
		final int delay = 1000 / FPS;
		t = new Timer(delay, l);

		t.start();

		JFrame frame = new JFrame();
		frame.add(gp);
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// The frame has the key listener, as a JPanel seems to not respond
		frame.addKeyListener(gp);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
