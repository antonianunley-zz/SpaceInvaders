
public class Shot extends Alien{
	
	    private int shoty = 1;
	    private int shotx = 5;

	    public Shot() {
	    }

	    public Shot(int x, int y) {
	        setX(x + shotx + 20);
	        setY(y - shoty);
	    }
}
