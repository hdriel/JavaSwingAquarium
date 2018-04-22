package abstracts_classes;

import driver.AquaPanel;


public abstract class Herbivore extends Animal{

	public Herbivore() {
		super();
	}
	public Herbivore(int horspeed, int verspeed) {
		super(horspeed, verspeed);
	}
	public Herbivore(int hor) {
		super(hor);
	}
	public boolean eat(Object food){
		return false;
	}
	
	@Override
	public String toString() {
		return ", Herbivore : eats ";
	}
	
	public String getType() {
		return super.toString();
	}

}
