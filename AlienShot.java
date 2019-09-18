
public class AlienShot extends Alien {
    private int shoty = 1;
    private int shotx = 5;

    public AlienShot() {
    }

    public AlienShot(int x, int y) {

        setX(x + shotx + 20);
        setY(y + shoty);
    }
}
