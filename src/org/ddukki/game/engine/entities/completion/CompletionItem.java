package org.ddukki.game.engine.entities.completion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.ui.events.Event;

/**
 * An entity that keeps track of the progress of a process currently proceeding
 * 
 */
public class CompletionItem extends UIEntity {
	protected CompletionQueue q;
	protected int queueIndex = 0;

	private int endValue = 0;
	private int currValue = 0;

	private double currPercentage = 0;

	private DecimalFormat df = new DecimalFormat("##0.000");

	private String name = "Item";

	public CompletionItem(final String name,
			final int endValue,
			final int currValue) {

		this.name = name;
		this.endValue = endValue;
		this.currValue = currValue;

		w = 100;
		h = 50;
	}

	public int getCurrentValue() {
		return currValue;
	}

	public int getEndValue() {
		return endValue;
	}

	public String getName() {
		return name;
	}

	public int getQueueIndex() {
		return queueIndex;
	}

	@Override
	public void react(Event e) {
		// TODO Auto-generated method stub

	}

	public void setCurrentValue(final int currValue) {
		this.currValue = currValue;
	}

	public void setEndValue(final int endValue) {
		this.endValue = endValue;
	}

	@Override
	public void update() {
		// currValue++;

		if (currValue == 0 && endValue == 0) {
			currPercentage = 1;
		} else {
			currPercentage = (double) currValue / endValue;
		}

		if (currPercentage >= 1) {
			q.remove(this);
		}
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		// Find the index of this item in the list
		x = q.x + 5;
		y = q.y + 5 + (h + 5) * queueIndex - q.queueOffset;

		// Draw main box
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		// Draw percentage string
		g.setColor(Color.black);
		g.drawString(df.format(currPercentage * 100) + "%", x + 5, y + 10);

		// Draw percentage bar
		g.setColor(Color.darkGray);
		g.fillRect(x + 5, y + 20, (int) (40 * currPercentage), 5);
		g.setColor(Color.black);
		g.drawRect(x + 5, y + 20, 40, 5);
	}
}
