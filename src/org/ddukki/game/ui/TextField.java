package org.ddukki.game.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.engine.events.MousedEvent;
import org.ddukki.game.engine.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.KeyedEvent;
import org.ddukki.game.ui.events.reactors.KeyedReactor;

/** A simple textfield for typing in characters */
public class TextField extends Entity implements KeyedReactor, MousedReactor {

	/** The stored string that is displayed in the field */
	public String s = "";

	/** Where the cursor should be pointing */
	public int cPos = 0;

	/** The current position of the animated cursor */
	public int aPos = 0;

	/** Animated frame number */
	public int pf = 5, af = 0;

	/** The frame counter for cursor animation */
	private int fc = 0;

	/** The last place the mouse was clicked within the textfield */
	private int mX = -1;

	/** The amount by which the field is offset in the x-direction */
	private int fOffset = 0;

	@Override
	public void react(Event e) {
		if (KeyedEvent.class.isInstance(e)) {
			react((KeyedEvent) e);
		} else if (MousedEvent.class.isInstance(e)) {
			react((MousedEvent) e);
		}
	}

	@Override
	public void react(KeyedEvent ke) {
		// Don't react to key events if this is not the focused entity
		if (Engine.keyFocus != this) {
			return;
		}

		if (ke.type == KeyedEvent.EventType.TYPED) {
			if (Character.isLetterOrDigit(ke.tc) || Character.isSpaceChar(ke.tc)
					|| KeyedEvent.isPunctuation(ke.tc)) {
				s = s.concat(String.valueOf(ke.tc));
				cPos++;
				af = pf;
			}
			// Reset the cursor animation
			fc = 0;
		} else if (ke.type == KeyedEvent.EventType.BUTTON_DOWN) {
			if (ke.kc == KeyedEvent.KC_LEFT) {
				if (cPos != 0) {
					cPos--;
					af = pf;
				}
			} else if (ke.kc == KeyedEvent.KC_RIGHT) {
				if (cPos < s.length()) {
					cPos++;
					af = pf;
				}
			} else if (ke.kc == KeyedEvent.KC_HOME) {
				cPos = 0;
				af = pf;
			} else if (ke.kc == KeyedEvent.KC_END) {
				cPos = s.length();
				af = pf;
			}

			// Delete the character before the cursor
			else if (ke.kc == KeyedEvent.KC_BACK_SPACE && cPos != 0) {
				s = s.substring(0, cPos - 1)
						.concat(s.substring(cPos, s.length()));
				cPos--;
				af = pf;
			}
			// Delete the character before the cursor
			else if (ke.kc == KeyedEvent.KC_DELETE && cPos < s.length()) {
				s = s.substring(0, cPos)
						.concat(s.substring(cPos + 1, s.length()));
			}
			// Reset the cursor animation
			fc = 0;
		}

		ke.reacted = true;
	}

	@Override
	public void react(MousedEvent me) {
		/*
		 * If clicked, place the cursor as close to the click location as
		 * possible; if the click is on the extremes of the bounds, place the
		 * cursor at one of the ends
		 */
		if ((me.type == MousedEvent.EventType.CLICKED
				|| me.type == MousedEvent.EventType.BUTTON_UP)
				&& hbx.contains(me.x, me.y)) {
			mX = me.x;
			Engine.keyFocus = this;
		}
	}

	@Override
	public void update() {
		hbx = new RectangularHitbox(x, y, w, h);

		// Increment the frame count and loop every 60 frames
		fc = (fc + 1) % 60;
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		// Update the bounding box of the string onscreen
		FontMetrics fm = g.getFontMetrics();

		// Calculate the width of the string
		int sw = fm.stringWidth(s);
		/*
		 * Reset the offset if the width of the field is big enough for the
		 * whole of the string
		 */
		if (sw < w - 6) {
			fOffset = 0;
		}

		/** Keep track of the hitbox of the string */
		RectangularHitbox sbx =
				new RectangularHitbox(x + 4, y, sw, fm.getHeight());

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		// Set the clip bounds
		g.setClip(x + 2, y + 2, w - 4, h - 4);

		g.drawString(s, sbx.x - fOffset, y + sbx.h);

		// Update the cursor position if mX is not negative
		if (mX > -1) {
			// offset the mouse by the field offset
			mX += fOffset;

			// If the mouse position is at the extreme end of the string, set it
			if (mX > sbx.x + sbx.w) {
				cPos = s.length();
			} else if (mX < sbx.x) {
				cPos = 0;
			} else {
				// Otherwise loop through the string and find the closest char
				int minDiff = Integer.MAX_VALUE;
				for (int i = 0; i < s.length(); i++) {
					int diff = Math.abs(
							mX - (sbx.x + fm.stringWidth(s.substring(0, i))));
					if (diff < minDiff) {
						cPos = i;
						minDiff = diff;
					}
				}
			}

			mX = -1;
		}

		// Calculate cursor position
		if (cPos > s.length()) {
			cPos = s.length();
			af = pf;
		}
		final int scX = fm.stringWidth(s.substring(0, cPos));
		final int pcX = scX + sbx.x - fOffset;

		// Calculate the offset by the placement of the cursor
		if (sw > w - 6) {
			if (pcX < sbx.x) {
				fOffset -= sbx.x - pcX;
			} else if (pcX > sbx.x + w - 8) {
				fOffset += pcX - (sbx.x + w - 8);
			}
		}

		// Calculate where the cursor should animate to this frame
		if (aPos != pcX) {
			if (af == 0) {
				aPos = pcX;
			} else {
				aPos += (pcX - aPos) / af;
			}

			af--;
		}

		if (fc < 30 && Engine.keyFocus == this) {
			g.drawLine(aPos, sbx.y + 2, aPos, sbx.y + sbx.h);
		}

		g.setClip(null);
	}

}