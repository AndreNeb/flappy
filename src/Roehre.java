import java.awt.image.BufferedImage;


public class Roehre extends Sprite {

    final int SPEED = 120;

    public Roehre(BufferedImage[] i, double x, double y, long delay, GamePanel p) {
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
                remove = true;
            }
            return true;
        }
        return false;
    }
}
