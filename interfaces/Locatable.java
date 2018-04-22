package interfaces;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import concrete_classes.Point;

/*
 * For any sea creature, it has coordinates
 */
public interface Locatable {
	static Set<Integer> ids = new HashSet<Integer>();
	public Point getLocation();
	public void setLocation(Point newPoint);
	public void drawAnimal(Graphics g);
}
