package interfaces;

/*
 * animals that move horizontally
 */
public interface Mobile extends Locatable{
	public void setHorSpeed(int speed);
	public void addPrey(Locatable l);
}
