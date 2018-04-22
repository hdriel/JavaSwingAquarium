package abstracts_classes;
import interfaces.AnimalBehavior;
import interfaces.Drawable;
import interfaces.Locatable;

import java.awt.Color;
import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.swing.JPanel;

import DesignPatterns.Observer;
import concrete_classes.Point;
import driver.AquaPanel;
import factories.AbstractFactory;


public abstract class Animal extends Thread implements Locatable, Drawable, AnimalBehavior{
		                                                             // the color of the animal 
	protected Color col = new Color((int)(Math.random() * 255 + 1), (int)(Math.random() * 255 + 1), (int)(Math.random() * 255 + 1));             
	protected int size = 80;                                     // the size of the animal in pixels
	protected int eatCount = 0;                                  // the number of food that the animal have ate
	protected int horSpeed = (int) (Math.random()*10 + 1);       // the horizontal speed the animal 
	protected int verSpeed = (int) (Math.random()*10 + 1);       // the horizontal speed the animal if necessary - (for Fish / JellyFish)
	protected static AquaPanel panel;                            // the panel who contain the animal to calculate by him size 
	protected Point location = new Point(0,0);                   // the location of the animal
	protected boolean isSuspended = false;                       // the flag that indicate if the animal should be moved or sleep/wait
	protected static JPanel pan;                                 // the panel who contain the animal to calculate by him size 
	protected int id;                                            // The ID of the animal
	protected static int amount = 0;                             // The Amount of the animal that created
	protected int x_dir = (int) (Math.random()*2);               // the direction of the animal on the axis x
	protected int y_dir = (int) (Math.random()*2);               // the direction of the animal on the axis y
	protected boolean dirty = true;                              // that is mean is the thread is not Suspended
	protected boolean isAliveAnim = true;
	protected boolean hungry = false, CounterFlag = false, hangryStateAqua = false;
	protected int freq = -1, CounterFreq = 0;
	private static final int MAX_FISH = 5;
	protected static Semaphore sema = new Semaphore((int)(MAX_FISH/2));
	protected boolean enterSemaphor = false;
	
	protected Observer controllPanelObserver;
	//---------------------- Ctors --------------------------------------
	public Animal() {
		super();
		
		int temp = ++amount;
		while(true){
			this.id = temp++;
			if(!ids.contains(id)){
				this.ids.add(id);
				break;
			}
		}
	}
	public Animal(int hor) {
		super();
		
		int temp = ++amount;
		while(true){
			this.id = temp++;
			if(!ids.contains(id)){
				this.ids.add(id);
				break;
			}
		}
		horSpeed = hor;
		location.setX((int) (Math.random()*400));
		location.setY((int) (Math.random()*400));
	}
	public Animal(int hor,int ver) {
		super();
		int temp = ++amount;
		while(true){
			this.id = temp++;
			if(!ids.contains(id)){
				this.ids.add(id);
				break;
			}
		}
		horSpeed = hor;
		verSpeed = ver;
		location.setX((int) (Math.random()*400));
		location.setY((int) (Math.random()*400));
	}
	
	//----------------------- getter-------------------------------------
	@Override
	public String  getColor() {
		return col.toString();
	}
	@Override
	public Color   getColorRGB() {
		return col;
	}
	public int     getHorSpeed() {
		return horSpeed;
	}
	public int     getVerSpeed() {
		return verSpeed;
	}
	public Point   getLocation() {
		return location;
	}
	public boolean getSuspended() {
		return isSuspended;
	}
	public boolean getHungry() {
		return hungry;
	}
	@Override
	public int getSize() {
		return size;
	}
	public static int getAmount(){
		return amount;
	}
	@Override
	public int getEatCount() {
		return eatCount;
	}
	synchronized public boolean getDirty(){
		return dirty;
	}
	public boolean getIsAliveAnimal(){
		return isAliveAnim;
	}
	public Semaphore getSemaphore(){
		return sema;
	}
	//----------------------- setter-------------------------------------
	public void setPanel(JPanel p){
		pan = p;
	}
	public void setLocation(Point p) {
		location = new Point(p);
	}
	public void setLocation(int x, int y) {
		location = new Point(x,y);
	}
	public void setSize(int s){
		size = s;
	}
	public void setFrequency(int f){
		CounterFlag = true;
		freq = f*1000;
	}
	public void returnLifeAnimal(){
		isAliveAnim = true;
	}
	public void killAmimalThread(){
		isAliveAnim = false;
	}
	public void setColor(Color c){
		col = c;
	}
	public void setVerSpeed(int speed) {
		this.verSpeed = speed;
	}
	public void setHorSpeed(int speed) {
		this.horSpeed = speed;
	}
	public void setSemaphore(Semaphore s){
		sema = s;
	}
	@Override
	public void eatInc() {
		eatCount++;		
	}
	synchronized public void setDirty(boolean state){
		dirty = state;
	}
	//-------------------- Observer -------------------------------------
	public void attach(Observer o){
		controllPanelObserver = o;
	}
	public void notifyObserver(){
		controllPanelObserver.update();
	}
	//-------------------- runnable method ------------------------------
	@Override
	public void setSuspended() {
		this.dirty = false;
	}
	@Override
	public void setResumed() {
		this.dirty = true;
	}
	public void startThread(){
		start();
	}
	@Override
	public void run() {	
		while(isAliveAnim)
		{
			try 
			{
				Thread.sleep((long)50);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			} 
			
			if(getDirty()){
				move();
			}
		}
		isAliveAnim = true;
	}
	
	//------------------ General ----------------------------------------
	public String toString(){
		return "Animal: ";
	}
	public void finalized(){
		amount--;
	}
	
	public void update(){
		
	}
	//----------------- ABSTRACT METHOD ---------------------------------
	public abstract boolean eat(Object food); 
	public abstract void move();
	//------------------ static -----------------------------------------
	public static void setAquaPanel(AquaPanel p){
		panel = p;
	}
	public static void clearIds(){
		ids.clear();
	}
	
}
