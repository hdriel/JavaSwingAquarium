package interfaces;

//Hadriel Benjo 302185061 , campus: Be'er Sheva

/*
 * animals that move horizontally
 */
public interface Mobile extends Locatable{
	public void setHorSpeed(int speed);
	public void addPrey(Locatable l);
}
