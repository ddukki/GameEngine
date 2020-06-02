package org.ddukki.game.ui.button;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;

public class TextButtonUI extends ButtonUI {
	/** The name of the button and also the string that is displayed */
	public String name = "Button";

	@Override
	public void update() {
		final FontMetrics fm = Engine.gp.fm;

		// Get the required dimensions of the string and pad the button
		h = fm.getHeight() + fm.getDescent() + 8;
		w = fm.stringWidth(name) + 8;

		hbx = new RectangularHitbox(x, y, w, h);
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		final FontMetrics fm = Engine.gp.fm;
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		// Draw the string centered in the button, adjusted for padding
		g.drawString(name, x + 4, y + 4 + fm.getHeight());
	}
}
