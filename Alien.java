public class Alien {
	
	 private boolean visible;
     protected int x;
     protected int y;
     private AlienShot ashot;
     
     public Alien() {
    	 
     }
    
     public Alien(int xval, int yval) {
         x = xval;
         y = yval;
         
         ashot = new AlienShot(x, y);
     }

     public boolean isVisible() {
         return visible;
     }

     public void setVisible(boolean visible) {
         this.visible = visible;
     }
     
     public void disappear() {
         visible = false;
     }

	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public int getX () {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public AlienShot getAlienShot() {
		return ashot;
	}
}
