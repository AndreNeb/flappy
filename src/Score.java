import java.awt.image.BufferedImage;


public class Score extends Sprite {

    public Score(BufferedImage[] i, double x, double y, long delay, GamePanel p) {
        super(i, x, y, delay, p);
    }

    public void doLogic(long delta) {
        super.doLogic(delta);
    }

    public boolean collidedWith(Sprite s) {
        return false;

    }


}
