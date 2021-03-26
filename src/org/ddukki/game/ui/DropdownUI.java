package org.ddukki.game.ui;

import java.awt.Graphics2D;

import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.ui.events.Event;

public class DropdownUI extends UIEntity {

	public int maxHeight = 300;

	public ListUI selectionList = new ListUI();

	public boolean dispList = false;

	public void addItem(String s) {
		selectionList.addItem(s);
	}

	public int count() {
		return selectionList.count();
	}

	@Override
	public void react(Event e) {
		// TODO Auto-generated method stub

	}

	public void removeItem(int i) {
		selectionList.removeItem(i);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGraphic(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
