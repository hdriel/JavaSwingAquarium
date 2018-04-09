package concrete_classes;
import interfaces.Locatable;
import interfaces.Swimmable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.BrokenBarrierException;

import driver.AquaPanel;
import DesignPatterns.MarineFish;
import abstracts_classes.Herbivore;

//Hadriel Benjo 302185061 , campus: Be'er Sheva

public class JellyFish extends Herbivore implements Swimmable, MarineFish{
	
	// Ctor with default value
	public JellyFish(){
		super();
	}
	// Ctor with custom value of speeds
	public JellyFish(int hor, int ver){
		super(hor,ver);
	}
	// Object Method - print the object
	@Override
	public String toString() {
		return getType() + this.getClass().getSimpleName() + " "  + this.id ; 
	}
	// AnimalBehavior Method - print the object
	@Override
	public String getAnimalName() {
		return this.getClass().getSimpleName() + " " + this.id;
	}
	// Move the location of the jelly fish
	@Override
	public void move() {
		// if exist food on the middle of the screen and the jellyfish hungry
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
						synchronized(this)
						{
							notifyObserver();
						}
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
	// AnimalBehavior Method - draw the jelly fish
	@Override
	public void drawAnimal(Graphics g) {
	   // define the number of legs
	   int numLegs;
	   if(size<40)    	numLegs = 5;
	   else if(size<80)	numLegs = 9;
	   else   	        numLegs = 12;
	   
	   Graphics2D gh = (Graphics2D) g;
	   if(hungry){
			// notify on hungry state
			gh.setColor(Color.red);
			gh.setStroke(new BasicStroke(3));
			gh.drawOval(location.getX() - (int)(size)+ (int)(size)/3, location.getY() - size/2 + 10, (int)(size*1.5) , size - 10 );	
			g.setColor(col);
			gh.setStroke(new BasicStroke(0));
		}
	   
	   // Body of jelly fish
	   g.setColor(col);
	   g.fillArc(location.getX()-size/2 , location.getY()-size/4 , size,size/2 , 0 , 180);
			
	   // legs of jelly fish
	   for(int i=0; i<numLegs; i++)
		g.drawLine(location.getX() - size/2 + size/numLegs + size*i/(numLegs+1), 
				   location.getY(), 
				   location.getX() - size/2 + size/numLegs + size*i/(numLegs+1), 
				   location.getY()+size/3);			
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
