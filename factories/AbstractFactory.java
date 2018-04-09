package factories;

import interfaces.Locatable;


public interface AbstractFactory{
	
	public Locatable CreateFactory(String type);
	
}
