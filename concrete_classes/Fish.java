package concrete_classes;

import interfaces.Locatable;
import interfaces.Swimmable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import driver.AquaPanel;
import DesignPatterns.MarineFish;
import DesignPatterns.Observer;
import abstracts_classes.Predator;

//Hadriel Benjo 302185061 , campus: Be'er Sheva
// close all code blocks by: Ctrl + Shift + / 
// open  all code blocks by: Ctrl + Shift + * 

public class Fish extends Predator implements Swimmable, MarineFish{
	
	// Ctor with default value
	public Fish(){
		super();
	}
	// Ctor with custom value of speeds
	public Fish(int hor, int ver){
		super(hor,ver);
	}
	// Object Method - print the object
	@Override
	public String toString() {
		return getType() + this.getClass().getSimpleName() + " " + this.id ;
	}
	// AnimalBehavior Method - print the object
	@Override
	public String getAnimalName() {
		return this.getClass().getSimpleName() + " " + this.id;
	}
	// Move the location of the fish
	@Override
	public void move() {
		// if exist food on the middle of the screen and the fish hungry
		
		if (panel.isFood() == true && hungry == true)
		{
			// calculate the distance between the Fish and the Food
			double Total_speed = Math.pow(Math.pow(verSpeed,2) + Math.pow(horSpeed,2),0.5);
			double ver_distance = panel.getWidth()/2 - location.x;
			double hor_distance = panel.getHeight()/2 - location.y;
			double Total_distance = Math.pow(Math.pow(ver_distance,2) + Math.pow(hor_distance,2),0.5);
			
			if ((Total_distance < 5) && (panel.isFood() == true))
			{
				synchronized(panel)
				{
						eatInc();
						hungry = false;
						CounterFlag = true;
						panel.lowerFoodFlag();
						notifyObserver();
				}
			}
			else
			{
				x_dir = ver_distance < 0 ? -1 : 1;
				y_dir = hor_distance < 0 ? -1 : 1;

				double new_ver_speed = Total_speed * (ver_distance/Total_distance);
				double new_hor_speed = Total_speed * (hor_distance/Total_distance);

				location.y = (int) (location.y + new_hor_speed);
				location.x = (int) (location.x + new_ver_speed);
			}
		}
		else // if NOT exist food on the middle of the screen or not hungry
		{
			if(hungry == false)
			{
				if(CounterFlag == true){
					CounterFreq += 90;
					//System.out.println("curr freq : " + CounterFreq + " (" + freq +")");
				}
				if(CounterFreq > 0 && CounterFreq >= freq){
					CounterFlag=false;
					hungry = true;
					panel.updateMassageHungeyAnimal(this);
					CounterFreq = 0;
				}
			}
			if(x_dir==1) // fish swims to right side
			{
				if(location.getX() + verSpeed >= pan.getWidth() - size / 2)
					x_dir = 0;
				else
					location.setX(location.getX() + verSpeed);
			}
			else // fish swims to left side
			{
				if(location.getX() - verSpeed < 0  + size / 2)
					x_dir = 1;
				else
					location.setX(location.getX() - verSpeed);
			}
			if(y_dir==1) // fish swims to up side
			{
				if(location.getY() + horSpeed < 0 + size / 2)
					y_dir = 0;
				else
					location.setY(location.getY() - horSpeed);
			}
			else // fish swims to down side
			{
				if(location.getY() + horSpeed > pan.getHeight()-size)
					y_dir = 1;
				else
					location.setY(location.getY() + horSpeed);
			}
		}
	}
	// AnimalBehavior Method - draw the fish
	@Override
	public void drawAnimal(Graphics g) {
	   g.setColor(col);
	   Graphics2D gh = (Graphics2D) g;
	   if(x_dir==1) // fish swims to right side
	   {
			if(hungry){
				// notify on hungry state
				gh.setColor(Color.red);
				gh.setStroke(new BasicStroke(3));
				gh.drawOval(location.getX() - (int)(size*1.5) , location.getY() - size/2, (int)(size*1.5 + size / 4) , size );	
				g.setColor(col);
			}
			
			// Body of fish
			g.fillOval( location.getX() - size, location.getY() - size/4 , size , size/2 );
	
			// Tail of fish
			int [] x_t = {location.getX() - size-size/4 , location.getX() - size-size/4 , location.getX() - size};
			int [] y_t = {location.getY() - size/4      , location.getY() + size/4      , location.getY()       };
			Polygon t = new Polygon(x_t , y_t , 3);		
			g.fillPolygon(t);
	
			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255-col.getRed() , 255-col.getGreen() , 255- col.getBlue()));
			g2.fillOval(location.getX()-size/5 , location.getY()-size/10 , size/10 , size/10);
					
			// Mouth of fish
			if(size>70)
				g2.setStroke(new BasicStroke(3));
			else if(size>30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
		      g2.drawLine(location.getX() , location.getY() , location.getX()-size/10 , location.getY()+size/10);
		      g2.setStroke(new BasicStroke(1));
	   }
	   else // fish swims to left side
	   {
		   if(hungry){
			// notify on hungry state
			   	gh.setColor(Color.red);
			   	gh.setStroke(new BasicStroke(3));
				gh.drawOval(location.getX() - size/4 , location.getY() - size/2, (int)(size*1.8) , size );
				g.setColor(col);
			}
			// Body of fish
			g.fillOval(location.getX() , location.getY() - size/4 , size , size/2);
	
			// Tail of fish
			int [] x_t={location.getX() + size+size/4 , location.getX()+size+size/4, location.getX()+size};
			int [] y_t = {location.getY() - size/4    , location.getY() + size/4   , location.getY()};
			Polygon t = new Polygon(x_t , y_t , 3);		
			g.fillPolygon(t);
	
			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255-col.getRed() , 255-col.getGreen() , 255-col.getBlue()));
			g2.fillOval(location.getX()+size/10 , location.getY()-size/10 , size/10 , size/10);
					
			// Mouth of fish
			if(size>70)
				g2.setStroke(new BasicStroke(3));
			else if(size>30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
		      g2.drawLine(location.getX() , location.getY() , location.getX()+size/10 , location.getY()+size/10);
		      g2.setStroke(new BasicStroke(1));
		   }

	}
	public void setColor(Color c) {
		PaintFish(c);
	};
	@Override
	public void PaintFish(Color col) {
		this.col = col;
	}
	
	
	@Override
	public void run(){
		while(isAliveAnim)
		{
			synchronized(this){
				if(AquaPanel.isHungryStateAquarium() && enterSemaphor == false){
					try {
						sema.acquire();
						enterSemaphor = true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(!AquaPanel.isHungryStateAquarium() && enterSemaphor == true){
					sema.release();
					enterSemaphor = false;
				}	
			}
			try 
			{
				Thread.sleep((long)50);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			} 
			
			if(getDirty()){
				move();
			}
		}
		if(enterSemaphor){
			sema.release();
		}
		isAliveAnim = true;
	}
	
}
