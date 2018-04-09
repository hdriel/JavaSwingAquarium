package factories;

import java.awt.Color;

import javax.swing.JPanel;

import DesignPatterns.Singleton_Lobster;
import abstracts_classes.Animal;
import concrete_classes.Fish;
import concrete_classes.JellyFish;
import concrete_classes.SeaUrchin;
import concrete_classes.Trochus;

public class AnimalFactory implements  AbstractFactory {

	private static AnimalFactory instance = null;
		
	private AnimalFactory(){}
	
	public static AnimalFactory getInstance(){
		if(instance == null){
			instance = new AnimalFactory();
		}
		return instance;
	}
	
	public Animal CreateFactory(String type){
		Animal anim = null;
		if(type.equalsIgnoreCase("Fish")){
			anim = new Fish();
			return anim; 
		}
		else if(type.equalsIgnoreCase("JellyFish")){
			anim = new JellyFish();
			return anim; 
		}
		else if(type.equalsIgnoreCase("Lobster")){
			anim = Singleton_Lobster.getInstance(null, 5);
			return anim; 
		}
		else if(type.equalsIgnoreCase("SeaUrchin")){
			anim = new SeaUrchin();
			return anim; 
		}
		else if(type.equalsIgnoreCase("Trochus")){
			anim = new Trochus();
			return anim; 
		}
		return null;
	}
}
