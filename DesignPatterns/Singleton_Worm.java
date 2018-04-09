package DesignPatterns;

import interfaces.Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PaintContext;

import javax.swing.JPanel;

public class Singleton_Worm {
	private static int amountWorms = 0;
	private JPanel panel;
	private static Singleton_Worm instance = null;
	
	private Singleton_Worm(JPanel panel){
		amountWorms++;
		System.out.println("Created " + amountWorms + " worms.");
		this.panel = panel;
	}
	
	public static Singleton_Worm getInstance(JPanel panel){
		if(instance == null)
			instance = new Singleton_Worm(panel);
		return instance;
	}
	
	
}
