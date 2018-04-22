package concrete_classes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

import driver.AquaPanel;
import abstracts_classes.Herbivore;


public class Trochus extends Herbivore{
	// Ctor with default value
	public Trochus(){
		super();
	}
	// Ctor with custom value of speed
	public Trochus(int x){
		super(x);
	}
	// Object Method - print the object
	@Override
	public String toString() {
		return getType() + this.getClass().getSimpleName() + " "  + this.id  ; //+ "\t" + super.toString() + getListPrey()  + this.pointLocation.toString() + strGetSpeed();
	}
	// AnimalBehavior Method - print the object
	@Override
	public String getAnimalName() {
		return this.getClass().getSimpleName()+ " " + this.id;
	}
	// Move the location of the Trochus
	public void moveToWorm() {}
	@Override
	public void move() {
		if(x_dir==1) // Trochus swims to right side
		{
			if(location.getX() + horSpeed >= pan.getWidth())
				x_dir = 0;
			else
				location.setX(location.getX() + horSpeed);
		}
		else // Trochus swims to left side
		{
			if(location.getX() - horSpeed < 0)
				x_dir = 1;
			else
				location.setX(location.getX() - horSpeed);
		}
		if((int)(Math.random() * 100 + 1) <= 5){
			x_dir = x_dir == 1? 0: 1;
		}
	}
	// AnimalBehavior Method - draw the Trochus
	@Override
	public void drawAnimal(Graphics g) {
		location.setY(pan.getHeight()-size/4-27);
		g.setColor(col);
		
		int [] x_t = {location.getX() -size*3/4,location.getX() + size*3/4, location.getX()};
		int [] y_t = {location.getY() + size/2, location.getY() + size/2,location.getY()-size/2};
		Polygon t = new Polygon(x_t,y_t,3);		
		g.fillPolygon(t);
				
		Graphics2D g2 = (Graphics2D) g;
		int wd = size/40;
		g2.setStroke(new BasicStroke(wd));
				
		  // Eyes
		if(x_dir==1){
		  g2.drawLine(location.getX() + size*230/400, location.getY() + size*2/8, location.getX() +     size*4/4, location.getY() + size*0/8);
		  g2.drawLine(location.getX() + size*230/400, location.getY() + size*2/8, location.getX() + size*35/40, location.getY() - size*1/8);
		  g.fillOval(location.getX() + size*4/4 - size/20, location.getY() + size*0/8 -size/20, size/10, size/10);
		  g.fillOval(location.getX() + size*35/40 - size/20, location.getY() - size*1/8 -size/20, size/10, size/10);
		}
		else {
		  g2.drawLine(location.getX() - size*230/400, location.getY() + size*2/8, location.getX() - size*4/4, location.getY() + size*0/8);
		  g2.drawLine(location.getX() - size*230/400, location.getY() + size*2/8, location.getX() - size*35/40, location.getY() - size*1/8);
		  g.fillOval(location.getX() - size*4/4 - size/20, location.getY() + size*0/8 -size/20, size/10, size/10);
		  g.fillOval(location.getX() - size*35/40 - size/20, location.getY() - size*1/8 -size/20, size/10, size/10);
		}
		g2.setColor(new Color(255-col.getRed(),255-col.getGreen(),255-col.getBlue()));
		g2.drawLine(location.getX() - size*201/400, location.getY() + size*1/5, location.getX() + size*26/60, location.getY() + size*1/10);
		g2.drawLine(location.getX() - size*145/400, location.getY() - size*0/5, location.getX() + size*17/60, location.getY() - size*1/10);
		g2.drawLine(location.getX() - size*77/400, location.getY() - size*115/500, location.getX() + size*8/60, location.getY() - size*3/10);

		g2.setStroke(new BasicStroke(1));

		
	}

}
