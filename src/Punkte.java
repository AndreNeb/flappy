
import java.awt.image.BufferedImage;

public class Punkte extends Sprite {

    final int SPEED = 120;

    public Punkte(BufferedImage[] i, double x, double y, long delay, GamePanel p) {
        super(i, x, y, delay, p);
        setHorizontalSpeed(-SPEED);
    }

    public void doLogic(long delta) {
        super.doLogic(delta);

    }

    public boolean collidedWith(Sprite s) {
        if (remove) {
            return false;
        }
        if (this.intersects(s)) {
            if (s instanceof Flappy) {
            }
            return true;
        }
        return false;
    }
}