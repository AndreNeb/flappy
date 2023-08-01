import java.awt.image.BufferedImage;

public class Flappy extends Sprite {
    public Flappy(BufferedImage[] i, double x, double y, long delay, GamePanel p) {
        super(i, x, y, delay, p);
    }

    @Override
    public void doLogic(long delta) {
        super.doLogic(delta);
    }

    public boolean collidedWith(Sprite s) {
        if (remove) {
            return false;
        }
        if (this.intersects(s)) {

            if (s instanceof Roehre) {
                remove = true;
                parent.TodSound();
            }
            if (s instanceof Boden) {
                remove = true;
                parent.TodSound();
            }
            if (s instanceof Punkte) {
                if (parent.ka == 1) {
                    parent.ka--;
                    parent.createZaehl();
                    parent.createNummer();
                }
            }
            if (remove) {
                parent.gameover = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }

}
