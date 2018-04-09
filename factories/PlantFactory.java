package factories;

import java.awt.Color;

import concrete_classes.Algae;
import concrete_classes.Plankton;
import abstracts_classes.Animal;
import abstracts_classes.Plant;
import driver.AquaPanel;

public class PlantFactory implements  AbstractFactory{

	static int ids = 0;
	
	public Plant CreateFactory(String type){
		Plant plant = null;
		ids++;
		if(type.equalsIgnoreCase("Plankton")){
			plant = new Plankton(ids);
			return plant; 
		}
		else if(type.equalsIgnoreCase("Algae")){
			plant = new Algae(ids);
			return plant; 
		}
		return null;
	}
	
	
}
