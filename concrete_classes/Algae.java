package concrete_classes;
import java.awt.Graphics;

import abstracts_classes.Plant;


public class Algae extends Plant{
	
	public Algae(int id){
		super(id);
	}
	
	public Algae(int id, int x, int y){
		super(id, x, y);
	}
	
	@Override
	public String toString() {
		
		return  super.toString() + this.getClass().getSimpleName() + " " + id + "\t\t" + strColor() + strLocatad();
	}

	@Override
	public void drawAnimal(Graphics g) {
		// TODO Auto-generated method stub
	}
}
