package factories;

import interfaces.Locatable;

import java.awt.Color;

import javax.swing.JPanel;

import abstracts_classes.Animal;
import concrete_classes.Fish;
import concrete_classes.JellyFish;
import concrete_classes.Lobster;
import concrete_classes.SeaUrchin;
import concrete_classes.Trochus;
import driver.AquaPanel;

public class CreateFactoryByType {
	// uses in the AddAnimalDialog.java file
	public Locatable create(String type, String name){
		if("Plant".equals(type))
		{
			System.out.println("Plant Factory...");
			return (new PlantFactory()).CreateFactory(name);
		}
		else if ("Animal".equals(type))
		{
			System.out.println("Animal Factory...");
			return AnimalFactory.getInstance().CreateFactory(name);
		}
		return null;
	}
}

