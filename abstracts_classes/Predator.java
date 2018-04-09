package abstracts_classes;

import driver.AquaPanel;

//Hadriel Benjo 302185061 , campus: Be'er Sheva

public abstract class Predator extends Animal{

	public Predator() {
		super();
	}
	public Predator(int horspeed, int varspeed) {
		super(horspeed, varspeed);
	}
	public Predator(int hor) {
		super(hor);
	}
	public boolean eat(Object food){
			return true;
	}
	
	@Override
	public String toString() {
		return ", Predator: eats ";
	}
	public String getType() {
		return super.toString();
	}
}
