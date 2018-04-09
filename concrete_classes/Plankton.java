package concrete_classes;
import java.awt.Graphics;

import abstracts_classes.Plant;

//Hadriel Benjo 302185061 , campus: Be'er Sheva

public class Plankton extends Plant{

	public Plankton(int id){
		super(id);
	}
	
	public Plankton(int id, int x, int y){
		super(id, x, y);
	}
	
	@Override
	public String toString() {
		return super.toString() + this.getClass().getSimpleName() + " " + id + "\t"+ strColor() + strLocatad();
	}

	@Override
	public void drawAnimal(Graphics g) {
		// TODO Auto-generated method stub
	}
}
