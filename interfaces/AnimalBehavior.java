package interfaces;

public interface AnimalBehavior {	
     public String getAnimalName();
	 public void setSuspended();
	 public void setResumed();
	 public int getSize();
	 public void eatInc();
	 public int getEatCount();
	 public boolean getDirty();
	 public void setDirty(boolean dirtyFlag);
}
