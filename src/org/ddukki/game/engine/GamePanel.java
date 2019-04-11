package org.ddukki.game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/** */
	private static final long serialVersionUID = 1L;
	public static DecimalFormat df = new DecimalFormat("##0.###");

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
