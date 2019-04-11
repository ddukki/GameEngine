package org.ddukki.game.engine;

import javax.swing.JFrame;
import javax.swing.Timer;

/***/
public class Engine {
	public static int FPS = 60;

	public static Timer t;

	public static GamePanel gp = new GamePanel();

	public static Loop l = new Loop();
	
	public static void main(String[] args) {
		final int delay = 1000 / FPS;
		t = new Timer(delay, l);

		t.start();
		
		JFrame frame = new JFrame();
		frame.add(gp);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
