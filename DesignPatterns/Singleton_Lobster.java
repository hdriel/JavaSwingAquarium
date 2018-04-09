package DesignPatterns;

import interfaces.Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PaintContext;

import javax.swing.JPanel;

import concrete_classes.Lobster;

public class Singleton_Lobster {
	private static int border = 5;
	private static int amountLobsters = 0;
	private JPanel panel;
	private static Lobster instance[] = new Lobster[border];
	
	public static Lobster getInstance(JPanel panel, int hor){
		for(int i = 0; i < border; i++)
		{
			if(instance[i] == null)
			{
				instance[i] = new Lobster(hor);
				amountLobsters++;
				System.out.println("Create " + amountLobsters + " Lobsters!");
				instance[i].setPanel(panel);
				return instance[i];
			}
		}
		System.out.println("Create " + amountLobsters + " Lobsters!");
		return instance[border-1];
	}
		
}
