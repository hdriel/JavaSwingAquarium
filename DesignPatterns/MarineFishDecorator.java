package DesignPatterns;


import java.awt.Color;

public class MarineFishDecorator implements MarineFish {
	MarineFish swimObj;

	public MarineFishDecorator(MarineFish swimObj)
	{
		this.swimObj=swimObj;
	}
	
	@Override
	public void PaintFish(Color col){
		swimObj.PaintFish(col);
	}
}
