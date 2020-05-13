package org.ddukki.game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.ddukki.game.engine.events.MousedEvent;

/**
 * The main game panel; all the game's UI will listen to this component for I/O
 * events from the player.
 * 
 * 
 */
public class GamePanel extends JPanel implements MouseListener {

	/** */
	private static final long serialVersionUID = 1L;
	public static DecimalFormat df = new DecimalFormat("##0.###");

	public GamePanel() {
		super();
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		final MousedEvent me = new MousedEvent(e);
		Engine.l.react(me);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);
		g.drawString(df.format(Loop.frameRate) + " FPS", 10, 10);

		Engine.l.updateGraphics((Graphics2D) g);
	}
}
