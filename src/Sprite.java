import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle2D.Double implements Drawable, Movable {
    long delay;
    long animation = 0;
    GamePanel parent;
    BufferedImage[] pics;
    int currentpic = 0;
    protected double dx;
    protected double dy;

    ;

    int loop_from;
    int loop_to;
    boolean remove = false;


    public Sprite(BufferedImage[] i, double x, double y, long delay, GamePanel p) {
        pics = i;
        this.x = x;
        this.y = y;
        this.delay = delay;
        this.width = pics[0].getWidth();
        this.height = pics[0].getHeight();
        parent = p;
        loop_from = 0;
        loop_to = pics.length - 1;
    }

    public void setX(double i) {
        x = i;
    }

    public void setY(double i) {
        y = i;

    }

    public void drawObjects(Graphics g) {
        g.drawImage(pics[currentpic], (int) x, (int) y, null);
    }

    public void doLogic(long delta) {
        animation += (delta / 1000000);
        if (animation > delay) {
            animation = 0;
            computeAnimation();
        }
    }

    private void computeAnimation() {
        currentpic++;
        if (currentpic > loop_to) {
            currentpic = loop_from;
        }
    }

    public void setVerticalSpeed(double d) {
        dy = d;
    }

    public void setHorizontalSpeed(double d) {
        dx = d;
    }


    public void move(long delta) {

        if (dx != 0) {
            x += dx * (delta / 1e9);
        }
        if (dy != 0) {
            y += dy * (delta / 1e9);
        }
    }

    public abstract boolean collidedWith(Sprite s);

}
