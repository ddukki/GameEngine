package org.ddukki.game.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;

/** A simple text-based label UI component */
public class TextLabelUI extends UIEntity {

	/** String that indicates the string that will be drawn on-screen */
	public String s = "";

	/** The color with which the text will be drawn */
	public Color c = Color.black;

	public TextLabelUI(String s) {
		this.s = s;
		update();
	}

	@Override
	public void react(Event e) {

	}

	@Override
	public void setDimensions(int nx, int ny, int nw, int nh) {
		x = nx;
		y = ny;
		w = nw < 1 ? w : nw;
		h = nh < 1 ? h : nh;
	}

	@Override
	public void update() {
		final FontMetrics fm = Engine.gp.fm;
		w = fm.stringWidth(s);
		h = fm.getHeight() + fm.getDescent();

		// Update the hitbox
		hbx = new RectangularHitbox(x, y, w, h);
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		final FontMetrics fm = Engine.gp.fm;
		g.setColor(c);
		g.drawString(s, x, y + fm.getHeight());
		g.setColor(Color.black);
	}

}
