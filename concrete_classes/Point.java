package concrete_classes;
//Hadriel Benjo 302185061 , campus: Be'er Sheva

/*
 * This class describe a point in the surface with the X,y coordinate 
 */
public class Point {
	// two coordinates
	public int x; 
	public int y;
	// Ctor default
	public Point(){
		this.x = 0;
		this.y = 0;
	}
	// Ctor custom
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	// Ctor Copy
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	// getters ---------------
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	// setters ---------------
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	// print object point
	@Override
	public String toString() {
		return " , x = " + this.x +", y = " + this.y +", ";
	}
}
