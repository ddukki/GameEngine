package org.ddukki.game.engine.entities;

import java.awt.Graphics2D;

public abstract class Entity {
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;
	
	public abstract void updateGraphic(final Graphics2D g);

	public abstract void update();
}
