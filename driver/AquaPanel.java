package driver;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import concrete_classes.Fish;
import concrete_classes.JellyFish;
import DesignPatterns.Observer;
import DesignPatterns.Singleton_Worm;
import abstracts_classes.Animal;

public class AquaPanel extends JPanel implements Runnable, Observer{
	
	private Image image;                     // background image of the panel 
	private ArrayList<Animal> listAnimals;   // list animal thread whos run on panel
	private Singleton_Worm sw;
	private boolean foodFlag;                // flag to call fish and jellyfish to eat
	protected boolean isAlive = true;        // flag to run the animals thread
	protected boolean notfinish = true;      // flag to run the main panel thread
	private int TotalamountWorms = 20;
	private int amountWorms = TotalamountWorms;
	private static boolean hangryState = false;
		
	
	// Ctor 1
	public AquaPanel(ArrayList<Animal> listAnimals, Singleton_Worm sw) {
		super();
		image = null;
		this.sw = sw;
		this.listAnimals = listAnimals;
		for (int i = 0; i < listAnimals.size(); i++) 
		{
			listAnimals.get(i).start();
			repaint();
		}
    }
	// Ctor 2
	public AquaPanel(ArrayList<Animal> listAnimals, Image img) {
		super();
		image = img;
		ScaleImage();
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	    this.listAnimals = listAnimals;
	    for (int i = 0; i < listAnimals.size(); i++) 
		{
	    	listAnimals.get(i).start();
			repaint();
		}
    }
	
	// Place image background to the panel
	public void setImageBackground(Image img){
		image = img;
		if(image != null)
		{
			ScaleImage();
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		    repaint();
		}
	}
	
	// Resize the image dependently panel size
	private void ScaleImage(){
		BufferedImage resizedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		g2.dispose();
		this.image = resizedImage;
	}
	public void setWorm(Singleton_Worm s){
		sw = s;
	}
	public void removeAnimalById(int id){
		for(int i = 0; i < listAnimals.size(); i++){
			System.out.println("Animal " + listAnimals.get(i).getId() +" ...");
			if(listAnimals.get(i).getId() == id){
				System.out.println("FOUND ANIMAL TO DELETE !");
				listAnimals.get(i).setDirty(false);
				listAnimals.get(i).killAmimalThread();
				repaint();
				listAnimals.remove(i);
				break;
			}
		}
	}
	public void ChangeColorById(int id, Color c){
		if(c != null)
			for(int i = 0; i < listAnimals.size(); i++){
				System.out.println("Animal " + listAnimals.get(i).getId() +" ...");
				if(listAnimals.get(i).getId() == id){
					System.out.println("FOUND ANIMAL TO RECOLOR !");
					listAnimals.get(i).setColor(c);
					repaint();
					break;
				}
			}
	}
	//----------- Thread and Swing Methods ------- 
	
	// paint the Component by call back from the repaint method
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null)
        {
        	ScaleImage();
        	g.drawImage(image, 0, 0, null);
        }
        
        if(isFood())
		{
        	
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(3));
			g2.drawArc(getWidth()/2, getHeight()/2-5, 10, 10, 30, 210);	   
			g2.drawArc(getWidth()/2, getHeight()/2+5, 10, 10, 180, 270);
			g2.setStroke(new BasicStroke(1));
			
		}
        
        for (int i = 0; i < listAnimals.size(); i++) {
			(listAnimals.get(i)).drawAnimal(g);
			//System.out.println("draw object " + listAnimals.get(i) + ", location " + listAnimals.get(i).getLocation());
		}
       
    }
	// re-life the threads
    public void setResumed() {
    	for (int i = 0; i < listAnimals.size(); i++) 
		{
			listAnimals.get(i).setResumed();
			repaint();
		}
    }
	// The run method
	@Override
	public void run() {
		//while(notfinish){
			while(isAlive)
			{
				repaint();
			}
		//}
		
	}
	// kill the animals thread
	public void  setDestroyThread() {
    	System.out.println("Kill the threads");
    	
    	for (int i = 0; i < listAnimals.size(); i++) 
		{
			listAnimals.get(i).setDirty(false);
			listAnimals.get(i).killAmimalThread();
			repaint();
		}
    	
    	try 
		{
			Thread.sleep((long)100);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}  
    	
    	isAlive = false;
    	try 
		{
			Thread.sleep((long)150);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}  
    	listAnimals.clear();
    }
	// re-run the main thread 
	public void  returnLife() {
		if(!isAlive){
			isAlive = true;
			new Thread(this).start();
			for (int i = 0; i < listAnimals.size(); i++) 
			{
				//listAnimals.get(i).setDirty(true);
				listAnimals.get(i).setResumed();
				repaint();
			}
		}
    }
	// suspended the animal threads
	public void  setSuspended() {
    	for (int i = 0; i < listAnimals.size(); i++) 
		{
			listAnimals.get(i).setSuspended();
			repaint();
		}
    }
	
	//----------------- Gets --------------------
	
	// check if the thread steel run
	public boolean isAlive(){
		return isAlive;
	}
	// check if there is food in the screen
	public boolean isFood(){
		return foodFlag;
	}
	
	//------------------ Sets -------------------
	
	// finish the thread
	public void finish(){
		notfinish = false;
	}
	// notify to all fishes that there is a food on the screen
	public void raiseFoodFlag(){
		if(foodFlag != true && amountWorms > 0){
			foodFlag = true;
			sw.getInstance(this);
			amountWorms--;
			AquaFrame.initWormStatus(amountWorms);
		}
		else{
			int num = 0;
			if(amountWorms == 0){
				update();
			}
		}
	}
	// notify to all fishes that there isn't a food on the screen
	public void lowerFoodFlag(){
		if(amountWorms == 0)
			JOptionPane.showMessageDialog(null , "Run out all the worms!","Warning Food",JOptionPane.PLAIN_MESSAGE);
		foodFlag = false;
	}
	
	
	public void updateMassageHungeyAnimal(Animal arg1) {
		// there is oval draw on the fish who hungry now
		//JOptionPane.showMessageDialog(null , arg1 +" wants to eat worm!","Notify Food",JOptionPane.PLAIN_MESSAGE);
	}
	
	// Observer  DP
	@Override
	public void update() {
		int num = 0;
		if(amountWorms <= 0.2*TotalamountWorms){
			num = displayAddWormsInput();
			amountWorms += num;
			AquaFrame.initWormStatus(amountWorms);
		}
		if(amountWorms <= 0.2*TotalamountWorms){
			hangryState = true;
		}
		if(amountWorms > 0.2*TotalamountWorms){
			hangryState = false;
		}
	}
	
	private static int displayAddWormsInput() {
        JTextField tf_numberWorms = new JTextField("0");
        tf_numberWorms.addKeyListener(new KeyListener() {		 
		    @Override
		    public void keyTyped(KeyEvent e) {
		    	char c = e.getKeyChar();
		        if (!(
		        		(c >= '0') && (c <= '9') ||
		        		(c == KeyEvent.VK_BACK_SPACE) ||
		        		(c == KeyEvent.VK_DELETE)
		        	  )) 
		        {
		          e.consume();
		        }
		    }
		    @Override
		    public void keyReleased(KeyEvent event) {
		    	int num;
		    	try{
		    		num = Integer.parseInt(tf_numberWorms.getText());
		    		if(num < 0 || num > 50 ){
		    			tf_numberWorms.setForeground(Color.RED);
			        }
			    	else
			    		tf_numberWorms.setForeground(Color.BLACK);
		    	}
		    	catch(NumberFormatException nfe){
		    		tf_numberWorms.setForeground(Color.RED);
		    	}
		    	
		    }
		    @Override
		    public void keyPressed(KeyEvent event) {
		        
		    }
		});
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Below 20%, You can add worms to the system (0-50): "));
        panel.add(tf_numberWorms);
        int num = 0; 
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            num =  Integer.parseInt(tf_numberWorms.getText());
            if(0 < num && num > 50){
            	num = 0;
            }
        } else {
            System.out.println("Cancelled");
        }
        return num;
    }
	
	public static boolean isHungryStateAquarium(){
		return hangryState;
	}
}
