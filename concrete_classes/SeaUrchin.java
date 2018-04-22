package concrete_classes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import abstracts_classes.Herbivore;


public class SeaUrchin extends Herbivore{
	// Ctor with default value
	public SeaUrchin(){
		super();
	}
	// Ctor with custom value of speed
	public SeaUrchin(int hor){
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
		return this.getClass().getSimpleName()+ " " + this.id;
	}
	// Move the location of the sea urchin
	public void moveToWorm() {}
	@Override
	public void move() {
		if(x_dir==1) // sea urchin swims to right side
		{
			if(location.getX() + horSpeed >= pan.getWidth())
				x_dir = 0;
			else
				location.setX(location.getX() + horSpeed);
		}
		else // sea urchin swims to left side
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
	// AnimalBehavior Method - draw the sea urchin
	@Override
	public void drawAnimal(Graphics g) {
	   location.setY(pan.getHeight()-size/4-27);

	   // body 
	   g.setColor(col);
	   g.fillOval(location.getX(), location.getY() - size*3/8, size/2, size/2);
			
	   Graphics2D g2 = (Graphics2D) g;
	   int wd = size/40;
	   g2.setStroke(new BasicStroke(wd));
	   
	   // line around the body  
	   g2.drawLine(location.getX() - size*5/16, location.getY() - size/8, location.getX() + size*13/16, location.getY() - size/8);
	   g2.drawLine(location.getX() + size/4, location.getY() - size*5/8, location.getX() + size/4, location.getY() + size*3/8);
	   g2.drawLine(location.getX() - size/4, location.getY() - size*3/8, location.getX() + size*3/4, location.getY() + size/8);
	   g2.drawLine(location.getX() - size/4, location.getY() + size/8, location.getX() + size*3/4, location.getY() - size*3/8);
	   g2.drawLine(location.getX() - size*1/16, location.getY() - size*9/16, location.getX() + size*9/16, location.getY() + size*3/8);
	   g2.drawLine(location.getX() - size*1/16, location.getY() + size*3/8, location.getX() + size*9/16, location.getY() - size*9/16);

	   g2.setStroke(new BasicStroke(1));
	}
}
