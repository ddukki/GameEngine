package org.ddukki.game.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.KeyedEvent;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.SubmittedEvent;
import org.ddukki.game.ui.events.reactors.KeyedReactor;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.SubmittedReactor;

/** A simple textfield for typing in characters */
public class TextFieldUI extends UIEntity
		implements KeyedReactor, MousedReactor {

	/** The stored string that is displayed in the field */
	public String s = "";

	/** Where the cursor should be pointing */
	public int cPos = 0;

	/** The current position of the animated cursor */
	public int aPos = 0;

	/** Animated frame number for moving cursor */
	public int pf = 5, af = 0;

	/** The frame counter for blinking cursor animation */
	private int fc = 0;

	/** The amount by which the field is offset in the x-direction */
	private int fOffset = 0;

	public List<SubmittedReactor> submittedReactors = new ArrayList<>();

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

				if (cPos == s.length()) {
					s = s.concat(String.valueOf(ke.tc));
				} else if (cPos == 0) {
					s = String.valueOf(ke.tc) + s;
				} else {
					s = s.substring(0, cPos) + String.valueOf(ke.tc)
							+ s.substring(cPos);
				}
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
			} else if (ke.kc == KeyedEvent.KC_ENTER) {
				SubmittedEvent se = new SubmittedEvent(this);
				for (SubmittedReactor sr : submittedReactors) {
					sr.react(se);
				}
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
				|| me.type == MousedEvent.EventType.BUTTON_UP) && !me.reacted
				&& hbx.contains(me.x, me.y)) {
			int mX = me.x;

			// Update the bounding box of the string onscreen
			FontMetrics fm = Engine.gp.fm;

			// Calculate the width of the string
			int sw = fm.stringWidth(s);
			int sh = fm.getHeight();

			/** Keep track of the hitbox of the string */
			RectangularHitbox sbx = new RectangularHitbox(x + 4, y, sw, sh);

			// offset the mouse by the field offset
			mX += fOffset;

			// If mouse position is at the extreme end of the string, set it
			if (mX > sbx.x + sbx.w) {
				cPos = s.length();
			} else if (mX < sbx.x) {
				cPos = 0;
			} else {
				// Loop through the string and find the closest char
				int minDiff = Integer.MAX_VALUE;
				for (int i = 0; i < s.length(); i++) {
					int sub = fm.stringWidth(s.substring(0, i));
					int diff = Math.abs(mX - (sbx.x + sub));
					if (diff < minDiff) {
						cPos = i;
						minDiff = diff;
					}
				}
			}

			me.reacted = true;
			Engine.keyFocus = this;
		} else if (Engine.keyFocus == this
				&& me.type == MousedEvent.EventType.BUTTON_UP) {
			Engine.keyFocus = null;
		}
	}

	@Override
	public void update() {
		if (Engine.keyFocus != this && hbx != null)
			return;

		hbx = new RectangularHitbox(x, y, w, h);

		// Increment the frame count and loop every 60 frames
		fc = (fc + 1) % Engine.FPS;

		// Update the bounding box of the string onscreen
		FontMetrics fm = Engine.gp.fm;

		// Calculate the width of the string
		int sw = fm.stringWidth(s);
		int sh = fm.getHeight();

		/** Keep track of the hitbox of the string */
		RectangularHitbox sbx = new RectangularHitbox(x + 4, y, sw, sh);

		/*
		 * Reset the offset if the width of the field is big enough for the
		 * whole of the string
		 */
		if (sw < w - 6) {
			fOffset = 0;
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

			if (af > 0)
				af--;
		}
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		// Update the bounding box of the string onscreen
		FontMetrics fm = Engine.gp.fm;

		// Calculate the width of the string
		int sw = fm.stringWidth(s);
		int sh = fm.getHeight();

		/** Keep track of the hitbox of the string */
		RectangularHitbox sbx = new RectangularHitbox(x + 4, y, sw, sh);

		g.setColor(Engine.keyFocus == this ? Color.blue : Color.black);
		g.drawRect(x, y, w, h);

		// Set the clip bounds
		g.setClip(x + 2, y + 2, w - 4, h - 4);

		g.setColor(Color.black);
		g.drawString(s, sbx.x - fOffset, y + sbx.h);

		if (fc < Engine.FPS / 2 && Engine.keyFocus == this) {
			g.drawLine(aPos, sbx.y + 2, aPos, sbx.y + sbx.h);
		}

		g.setClip(null);
	}

}
