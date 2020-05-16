package org.ddukki.game.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.ddukki.game.engine.events.MousedEvent;
import org.ddukki.game.ui.events.KeyedEvent;

/**
 * The main game panel; all the game's UI will listen to this component for I/O
 * events from the player.
 */
public class GamePanel extends JPanel implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener {

	/** */
	private static final long serialVersionUID = 1L;
	public static DecimalFormat df = new DecimalFormat("##0.###");
	public Font gameFont;
	public FontMetrics fm;

	public GamePanel() {
		super();

		// NOTE: The frame has the keylistener as the JPanel does not respond
		addMouseListener(this);
		addMouseMotionListener(this);

		try {
			gameFont = Font
					.createFont(Font.TRUETYPE_FONT,
							new File("C:\\Windows\\Fonts\\consola.ttf"))
					.deriveFont(12f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		fm = getFontMetrics(gameFont);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		final KeyedEvent ke = new KeyedEvent(e);
		ke.type = KeyedEvent.EventType.BUTTON_DOWN;
		Engine.l.react(ke);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		final KeyedEvent ke = new KeyedEvent(e);
		ke.type = KeyedEvent.EventType.BUTTON_UP;
		Engine.l.react(ke);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		final KeyedEvent ke = new KeyedEvent(e);
		ke.type = KeyedEvent.EventType.TYPED;
		Engine.l.react(ke);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		final MousedEvent me = new MousedEvent(e);
		Engine.l.react(me);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
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
	public void mouseMoved(MouseEvent e) {
		final MousedEvent me = new MousedEvent(e);
		Engine.l.react(me);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		final MousedEvent me = new MousedEvent(e);
		Engine.l.react(me);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		final MousedEvent me = new MousedEvent(e);
		Engine.l.react(me);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		final MousedEvent me = new MousedEvent(e);
		Engine.l.react(me);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(gameFont);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.WHITE);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);
		g.drawString(df.format(Loop.frameRate) + " FPS", 10, 10);

		Engine.l.updateGraphics(g2d);
	}
}
