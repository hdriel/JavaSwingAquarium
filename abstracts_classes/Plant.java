package abstracts_classes;
import concrete_classes.Point;
import interfaces.Locatable;


public abstract class Plant implements Locatable{
	protected int id;
	protected Point pointLocation;
	protected String color;
	
	public Plant(int id){
		this.id = id;
		pointLocation = new Point();
	}
	public Plant(int id, int x, int y){
		this.id = id;
		pointLocation = new Point(x,y);
	}
	public Plant(int id, String color){
		this.id = id;
		pointLocation = new Point();
		this.color = color;
	}
	public Plant(int id, int x, int y, String color){
		this.id = id;
		pointLocation = new Point(x,y);
		this.color = color;
	}
	
	@Override
	public Point getLocation() {
		return pointLocation;
	}

	@Override
	public void setLocation(Point newPoint) {
		pointLocation = new Point(newPoint);
	}
	
	public String strLocatad(){
		return ", located in " + pointLocation.toString();
	}
	public String toString(){
		return "Plant : ";
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String strColor(){
		if(color != null){
			return ", color is " + this.color;
		}
		else 
			return "";
	}
}
