package concrete_classes;
import interfaces.Locatable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import abstracts_classes.Predator;


public class Lobster extends Predator{
	// Ctor with default value
	public Lobster(){
		super();
	}
	// Ctor with custom value of speed
	public Lobster(int hor){
		super(hor);
	}
	// Object Method - print the object
	@Override
	public String toString() {
		return getType() + this.getClass().getSimpleName() + " " + this.id ; //+ "\t"+ super.toString() + getListPrey()  + this.pointLocation.toString() + strGetSpeed();
	}
	// AnimalBehavior Method - print the object
	@Override
	public String getAnimalName() {
		return this.getClass().getSimpleName() + " " + this.id;
	}
	// Move the location of the lobster
	public void moveToWorm() {}
	@Override
	public void move() {
		if(x_dir==1) // Lobster swims to right side
		{
			if(location.getX() + horSpeed >= pan.getWidth())
				x_dir = 0;
			else
				location.setX(location.getX() + horSpeed);
		}
		else // Lobster swims to left side
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
	// AnimalBehavior Method - draw the Lobster
	@Override
	public void drawAnimal(Graphics g) {
		  location.setY(pan.getHeight()-size/4-27);
		  g.setColor(col);
		  
		  // Body of lobster
		  g.fillOval(location.getX() - size/2, location.getY() - size/4, size, size/2);
				
		  // Eyes of lobster
		  g.fillOval(location.getX()-size*3/10,location.getY()-size*3/10,size/10,size/10);
		  g.fillOval(location.getX() + size*2/10,location.getY()-size*3/10,size/10, size/10);
	
		  Graphics2D g2 = (Graphics2D) g;
		  int wd = size/20;
		  g2.setStroke(new BasicStroke(wd));
				
		  // Left side feet
		  g.drawLine(location.getX(), location.getY(), location.getX() - size*7/10, location.getY()+size*1/10);
		  g.drawLine((int) (location.getX()-size*0.7),location.getY()+size/10,location.getX()-size*4/5, location.getY()+size/2);
	
		  g.drawLine(location.getX(), location.getY(), location.getX() - size*8/10, location.getY()+size*1/20);
		  g.drawLine(location.getX() - size*8/10, location.getY()+size*1/20, location.getX() - size, location.getY()+size*1/6);
	
		  g.drawLine(location.getX(), location.getY(), (int) (location.getX() - size*0.8),location.getY() - size/30);
		  g.drawLine(location.getX() - size*8/10, location.getY() - size*1/30, location.getX() - size, location.getY()-size*1/8);
	
		  g.drawLine(location.getX(), location.getY(), location.getX() - size*8/10, location.getY()-size*1/10);
		  g.drawLine(location.getX() - size*8/10, location.getY()-size*1/10, location.getX() - size, location.getY()-size*1/4);
	
		  // Right side feet
		  g.drawLine(location.getX(), location.getY(), location.getX() + size*7/10, location.getY()+size*1/10);
		  g.drawLine(location.getX() + size*7/10, location.getY()+size*1/10, location.getX() + size*8/10, location.getY()+size*1/2);
	
		  g.drawLine(location.getX(), location.getY(), location.getX() + size*8/10, location.getY()+size*1/20);
		  g.drawLine(location.getX() + size*8/10, location.getY()+size*1/20, location.getX() + size, location.getY()+size*1/6);
	
		  g.drawLine(location.getX(), location.getY(), location.getX() + size*8/10, location.getY()-size*1/30);
		  g.drawLine(location.getX() + size*8/10, location.getY() - size*1/30, location.getX() + size, location.getY()-size*1/8);
	
		  g.drawLine(location.getX(), location.getY(), location.getX() + size*8/10, location.getY()-size*1/10);
		  g.drawLine(location.getX() + size*8/10, location.getY()-size*1/10, location.getX() + size, location.getY()-size*1/4);
	
		  // Claws
		  g.drawLine(location.getX(), location.getY(), location.getX() - size*8/10, location.getY()-size/5);
		  g.drawLine(location.getX() - size*8/10, location.getY()-size/5, location.getX() - size*6/10, location.getY()-size*1/3);
				
		  g.drawLine(location.getX(), location.getY(), location.getX() + size*8/10, location.getY()-size/5);
		  g.drawLine(location.getX() + size*8/10, location.getY()-size/5, location.getX() + size*6/10, location.getY()-size*1/3);
	
		  g2.setStroke(new BasicStroke(wd*2));
		  g2.drawArc(location.getX()-size*3/5,location.getY()-size/2,size/4,size/4,60,240);	
		  g2.drawArc(location.getX()+size*7/20,location.getY()-size/2,size/4,size/4,240,240);					
		  g2.setStroke(new BasicStroke(1));
		  g2.setColor(new Color(255-col.getRed(),255-col.getGreen(),255-col.getBlue()));
		  g2.drawOval((int)(location.x-size*0.3), (int)(location.y-size*0.3), size/10, size/10);
		  g2.drawOval((int)(location.x+size*0.2), (int)(location.y-size*0.3), size/10, size/10);
	}
}
