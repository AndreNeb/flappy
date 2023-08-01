import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Vector;

public class GamePanel extends JPanel implements Runnable, KeyListener, ActionListener, MouseListener {

    boolean game_running = true;
    boolean started = false;
    boolean once = false;
    boolean up = false;
    long delta = 0;
    long last = 0;
    long fps = 0;
    long gameover = 0;
    int probe = 0;
    int zaehl = 0;
    int ka = 1;
    int l = 0;
    int k = 0;
    int sound = 0;
    int s = 0;
    int tod = 0;
    int t = 0;
    int test;
    int speed = 50;
    Timer timer;
    Timer timer1;
    Timer timer2;
    Timer timer3;
    Timer timer4;
    Timer timer5;
    BufferedImage[] boden;
    BufferedImage[] zero;
    BufferedImage[] eins;
    BufferedImage[] zwei;
    BufferedImage[] drei;
    BufferedImage[] vier;
    BufferedImage[] fuenf;
    BufferedImage[] sechs;
    BufferedImage[] sieben;
    BufferedImage[] acht;
    BufferedImage[] neun;
    BufferedImage[] roehre_1;
    BufferedImage[] roehre_1u;
    BufferedImage[] roehre_2;
    BufferedImage[] roehre_2u;
    BufferedImage background;
    BufferedImage boden1;
    BufferedImage todesbild;
    BufferedImage silber;
    BufferedImage bronze;
    BufferedImage platin;
    BufferedImage gold;
    BufferedImage zero1;
    BufferedImage fertig;
    BufferedImage Start;
    BufferedImage Tap_Start;
    BufferedImage getReady;
    BufferedImage Gameover;
    BufferedImage[] Zaehlsystem;


    BufferedImage created;

    SoundLib slib;
    Flappy Flappy1;
    Vector<Sprite> actors;
    private boolean flapp;

    final JButton start;

    public static void main(String[] args) {
        new GamePanel(1000, 750);
    }

    public GamePanel(int w, int h) {
        this.setPreferredSize(new Dimension(w, h));
        JFrame frame = new JFrame("Flappy Birds");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/pics/Logo.png");
        start = new JButton();
        start.setBounds(430, 440, 150, 80);
        start.addActionListener(new MyActionListener());
        frame.setIconImage(icon);
        frame.setLocation(100, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        this.addMouseListener(this);
        frame.add(this);
        start.setVisible(false);
        frame.pack();
        frame.setVisible(true);
        doInitializations();

    }

    private void doInitializations() {

        actors = new Vector<Sprite>();
        BufferedImage[] flappy = this.loadPics("pics/Flappy.png", 3);
        background = loadPics("pics/hintergrund.jpg", 1)[0];
        created = loadPics("pics/Created by.png", 1)[0];
        boden = loadPics("pics/Boden_r.png", 1);
        boden1 = loadPics("pics/Boden_r.png", 1)[0];
        todesbild = loadPics("pics/Todesfenster.png", 1)[0];
        bronze = loadPics("pics/Bronzemedaille.png", 1)[0];
        silber = loadPics("pics/Silbermedaille1.png", 1)[0];
        gold = loadPics("pics/Goldmedaille.png", 1)[0];
        platin = loadPics("pics/Platin.png", 1)[0];
        roehre_1 = loadPics("pics/Roehre_1.png", 1);
        roehre_1u = loadPics("pics/Roehre_1u.png", 1);
        roehre_2 = loadPics("pics/Roehre_2.png", 1);
        roehre_2u = loadPics("pics/Roehre_2u.png", 1);
        fertig = loadPics("pics/Fenster1000.png", 1)[0];
        zero1 = loadPics("pics/Null.png", 1)[0];
        zero = loadPics("pics/Null.png", 1);
        eins = loadPics("pics/Eins.png", 1);
        zwei = loadPics("pics/Zwei.png", 1);
        drei = loadPics("pics/Drei.png", 1);
        vier = loadPics("pics/Vier.png", 1);
        fuenf = loadPics("pics/Fünf.png", 1);
        sechs = loadPics("pics/Sechs.png", 1);
        sieben = loadPics("pics/Sieben.png", 1);
        acht = loadPics("pics/Acht.png", 1);
        neun = loadPics("pics/Neun.png", 1);
        Start = loadPics("pics/Start.png", 1)[0];
        Tap_Start = loadPics("pics/Tap_Start.png", 1)[0];
        getReady = loadPics("pics/GetReady.png", 1)[0];
        Gameover = loadPics("pics/Gameover.png", 1)[0];
        Zaehlsystem = loadPics("pics/Punkte.png", 1);
        last = System.nanoTime();
        gameover = 0;
        sound = 1;

        slib = new SoundLib();
        slib.loadSound("Tod", "sound/Everything/sfx_hit.wav");
        slib.loadSound("point", "sound/Everything/sfx_point.wav");
        slib.loadSound("background", "sound/richtigerhintergrundsound_out.wav");
        slib.loadSound("clicksound", "sound/Everything/sfx_wing.wav");

        Flappy1 = new Flappy(flappy, 480, 340, 100, this);
        actors.add(Flappy1);
        createBoden();

        timer = new Timer(3000, this);
        timer1 = new Timer(890, this);
        timer2 = new Timer(6010, this);
        timer3 = new Timer(3000, this);
        timer4 = new Timer(6000, this);
        timer5 = new Timer(3000, this);


        if (isStarted()) {
            slib.loopSound("background");
            l++;
        }
        if (!once) {
            once = true;
            Thread t = new Thread(this);
            t.start();
        }
    }


    int z = 0;


    public void createZaehl() {
        if (isStarted()) {
            zaehl++;
        }
    }

    public void TodSound() {
        slib.playSound("Tod");
    }

    public void createPunkte() {
        Punkte punkt = new Punkte(Zaehlsystem, 1250, 0, 0, this);
        actors.add(punkt);
    }

    public void createNummer() {
        if (zaehl >= 1 && sound == 1) { // macht nur punkstsound wenn spiel läuft
            slib.playSound("point");
        }
        Score Zeroe = new Score(zero, 473, 40, 0, this);
        Score Einse = new Score(eins, 483, 40, 0, this);
        Score Zweie = new Score(zwei, 473, 40, 0, this);
        Score Dreie = new Score(drei, 473, 40, 0, this);
        Score Viere = new Score(vier, 473, 40, 0, this);
        Score Fuenfe = new Score(fuenf, 473, 40, 0, this);
        Score Sechse = new Score(sechs, 473, 40, 0, this);
        Score Siebene = new Score(sieben, 473, 40, 0, this);
        Score Achte = new Score(acht, 473, 40, 0, this);
        Score Neune = new Score(neun, 473, 40, 0, this);

        Score Zeroz1 = new Score(zero, 505, 40, 0, this);
        Score Einsz1 = new Score(eins, 515, 40, 0, this);
        Score Zweiz1 = new Score(zwei, 505, 40, 0, this);
        Score Dreiz1 = new Score(drei, 505, 40, 0, this);
        Score Vierz1 = new Score(vier, 505, 40, 0, this);
        Score Fuenfz1 = new Score(fuenf, 505, 40, 0, this);
        Score Sechsz1 = new Score(sechs, 505, 40, 0, this);
        Score Siebenz1 = new Score(sieben, 505, 40, 0, this);
        Score Achtz1 = new Score(acht, 505, 40, 0, this);
        Score Neunz1 = new Score(neun, 505, 40, 0, this);

        Score Zeroz2 = new Score(zero, 441, 40, 0, this);
        Score Einsz2 = new Score(eins, 451, 40, 0, this);
        Score Zweiz2 = new Score(zwei, 441, 40, 0, this);
        Score Dreiz2 = new Score(drei, 441, 40, 0, this);
        Score Vierz2 = new Score(vier, 441, 40, 0, this);
        Score Fuenfz2 = new Score(fuenf, 441, 40, 0, this);
        Score Sechsz2 = new Score(sechs, 441, 40, 0, this);
        Score Siebenz2 = new Score(sieben, 441, 40, 0, this);
        Score Achtz2 = new Score(acht, 441, 40, 0, this);
        Score Neunz2 = new Score(neun, 441, 40, 0, this);


        Score[] Einer = {Zeroe, Einse, Zweie, Dreie, Viere, Fuenfe, Sechse, Siebene, Achte, Neune};
        Score[] Zehner1 = {Zeroz1, Einsz1, Zweiz1, Dreiz1, Vierz1, Fuenfz1, Sechsz1, Siebenz1, Achtz1, Neunz1};
        Score[] Zehner2 = {Zeroz2, Einsz2, Zweiz2, Dreiz2, Vierz2, Fuenfz2, Sechsz2, Siebenz2, Achtz2, Neunz2};


        if (zaehl < 10) {
            actors.add(Einer[zaehl]);
            if (zaehl >= 1) {
                actors.remove(Einer[zaehl - 1]);
            }
        }
        if (zaehl >= 10 && zaehl < 20) {
            if (zaehl < 20) {
                t = zaehl;
                t = t - 10;       // Einer herausfinden
                if (t == 0) {
                    actors.remove(Einer[9]); // 9er vom Bereich zw. 0 & 9 removen
                    actors.add(Zehner2[1]); // Zehner 1er wird erzeugt
                }
                actors.remove(Zehner2[1]);
                if (zaehl > 10) {
                    actors.remove(Zehner1[t - 1]);
                }
                actors.add(Zehner1[t]);
                actors.add(Zehner2[1]);
            }
            if (zaehl >= 20 && zaehl < 30) {
                t = zaehl;
                t = t - 20;                 // Einer herausfinden
                if (t == 0) {
                    actors.remove(Zehner1[9]);
                    actors.remove(Zehner2[1]);
                    actors.add(Zehner2[2]);
                }
                actors.remove(Zehner2[2]);
                if (zaehl > 20) {
                    actors.remove(Zehner1[t - 1]);
                }
                actors.add(Zehner1[t]);
                actors.add(Zehner2[2]);
            }
            if (zaehl >= 30 && zaehl < 40) {
                t = zaehl;
                t = t - 30;

                if (t == 0) {
                    actors.remove(Zehner1[9]);
                    actors.remove(Zehner2[2]);
                    actors.add(Zehner2[3]);
                }
                actors.remove(Zehner2[3]);
                if (zaehl > 30) {
                    actors.remove(Zehner1[t - 1]);
                }
                actors.add(Zehner1[t]);
                actors.add(Zehner2[3]);
            }
            if (zaehl >= 40 && zaehl < 50) {
                t = zaehl;
                t = t - 40;
                if (t == 0) {
                    actors.remove(Zehner1[9]);
                    actors.remove(Zehner2[3]);
                    actors.add(Zehner2[4]);
                }
                actors.remove(Zehner2[4]);
                if (zaehl > 40) {
                    actors.remove(Zehner1[t - 1]);
                }
                actors.add(Zehner1[t]);
                actors.add(Zehner2[4]);
            }

        }
        if (zaehl == 50) {
            actors.remove(Zehner1[9]);
            actors.remove(Zehner2[4]);
            actors.add(Zehner2[5]);
            actors.add(Zehner1[0]);
        }
    }


    private void createRoehre() {

        Roehre roehre1 = new Roehre(roehre_2, 1100, 0, 0, this);
        Roehre roehre2 = new Roehre(roehre_2, 1100, 50, 0, this);
        Roehre roehre3 = new Roehre(roehre_2, 1100, 100, 0, this);
        Roehre roehre4 = new Roehre(roehre_2, 1100, 150, 0, this);
        Roehre roehre5 = new Roehre(roehre_2, 1100, 200, 0, this);
        Roehre roehre6 = new Roehre(roehre_2, 1100, 250, 0, this);
        Roehre roehre7 = new Roehre(roehre_2, 1100, 300, 0, this);
        Roehre roehre27 = new Roehre(roehre_2, 1100, 350, 0, this);

        Roehre roehre8 = new Roehre(roehre_2u, 1100, 390, 0, this);
        Roehre roehre9 = new Roehre(roehre_2u, 1100, 440, 0, this);
        Roehre roehre10 = new Roehre(roehre_2u, 1100, 490, 0, this);
        Roehre roehre11 = new Roehre(roehre_2u, 1100, 540, 0, this);
        Roehre roehre12 = new Roehre(roehre_2u, 1100, 590, 0, this);
        Roehre roehre13 = new Roehre(roehre_2u, 1100, 640, 0, this);
        Roehre roehre14 = new Roehre(roehre_2u, 1100, 641, 0, this);
        Roehre roehre28 = new Roehre(roehre_2u, 1100, 340, 0, this);

        Roehre roehre15 = new Roehre(roehre_1, 1100, 100, 0, this);
        Roehre roehre16 = new Roehre(roehre_1, 1100, 150, 0, this);
        Roehre roehre17 = new Roehre(roehre_1, 1100, 200, 0, this);
        Roehre roehre18 = new Roehre(roehre_1, 1100, 250, 0, this);
        Roehre roehre19 = new Roehre(roehre_1, 1100, 300, 0, this);
        Roehre roehre20 = new Roehre(roehre_1, 1100, 350, 0, this);
        Roehre roehre29 = new Roehre(roehre_1, 1100, 50, 0, this);
        Roehre roehre30 = new Roehre(roehre_1, 1100, 400, 0, this);

        Roehre roehre21 = new Roehre(roehre_1u, 1100, 340, 0, this);
        Roehre roehre22 = new Roehre(roehre_1u, 1100, 390, 0, this);
        Roehre roehre23 = new Roehre(roehre_1u, 1100, 440, 0, this);
        Roehre roehre24 = new Roehre(roehre_1u, 1100, 490, 0, this);
        Roehre roehre25 = new Roehre(roehre_1u, 1100, 540, 0, this);
        Roehre roehre26 = new Roehre(roehre_1u, 1100, 590, 0, this);
        Roehre roehre31 = new Roehre(roehre_1u, 1100, 290, 0, this);
        Roehre roehre32 = new Roehre(roehre_1u, 1100, 640, 0, this);

        if (s < 1) {
            actors.add(roehre1);
            actors.add(roehre2);
            actors.add(roehre3);
            actors.add(roehre4);
            actors.add(roehre17);

            actors.add(roehre10);
            actors.add(roehre11);
            actors.add(roehre12);
            actors.add(roehre13);
            actors.add(roehre14);
            actors.add(roehre23);

            s++;
        } else {

            int x = (int) (Math.random() * 7) + 1;

            if (x == 1) {
                actors.addAll(Arrays.asList(roehre1, roehre29, roehre8, roehre9, roehre10, roehre11, roehre12, roehre13, roehre14, roehre28, roehre31));

            }
            if (x == 2) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre15, roehre8, roehre9, roehre10, roehre11, roehre12, roehre13, roehre14, roehre21));

            }
            if (x == 3) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre3, roehre16, roehre9, roehre10, roehre11, roehre12, roehre13, roehre14, roehre22));

            }
            if (x == 4) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre3, roehre4, roehre17, roehre10, roehre11, roehre12, roehre13, roehre14, roehre23));

            }
            if (x == 5) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre3, roehre4, roehre5, roehre18, roehre11, roehre12, roehre13, roehre14, roehre24));

            }
            if (x == 6) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre3, roehre4, roehre5, roehre6, roehre19, roehre12, roehre13, roehre14, roehre25));

            }
            if (x == 7) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre3, roehre4, roehre5, roehre6, roehre7, roehre20, roehre13, roehre14, roehre26));

            }
            if (x == 8) {
                actors.addAll(Arrays.asList(roehre1, roehre2, roehre3, roehre4, roehre5, roehre6, roehre7, roehre27, roehre30, roehre14, roehre32));

            }
        }

    }

    public void createBoden() {
        int y = 700;

        boden = loadPics("pics/Boden_r.png", 1);
        Boden boden1 = new Boden(boden, 0, y, 0, this);
        Boden boden2 = new Boden(boden, 168, y, 0, this);
        Boden boden3 = new Boden(boden, 336, y, 0, this);
        Boden boden4 = new Boden(boden, 504, y, 0, this);
        Boden boden5 = new Boden(boden, 672, y, 0, this);
        Boden boden6 = new Boden(boden, 840, y, 0, this);
        Boden boden7 = new Boden(boden, 1008, y, 0, this);
        Boden boden8 = new Boden(boden, 1176, y, 0, this);
        Boden boden9 = new Boden(boden, 1344, y, 0, this);


        if (z <= 1) {
            actors.add(boden1);
            actors.add(boden2);
            actors.add(boden3);
            actors.add(boden4);
            actors.add(boden5);
            actors.add(boden6);
            actors.add(boden7);
            actors.add(boden8);

            z++;
        }

        if (z > 1) {
            actors.add(boden9);
            z++;
        }
    }


    private void computeDelta() {
        delta = System.nanoTime() - last;
        last = System.nanoTime();
        fps = ((long) 1e9) / delta;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this);
        g.drawImage(created, 5, 5, this);
        remove(start);
        if (isStarted() && zaehl < 1) {
            g.drawImage(zero1, 473, 40, this);
        }
        if (!isStarted()) {
            if (l == 0) {
                g.drawImage(getReady, 372, 218, this);
                g.drawImage(Tap_Start, 408, 310, this);
            }
            g.drawImage(boden1, 0, 700, this);
            g.drawImage(boden1, 168, 700, this);
            g.drawImage(boden1, 336, 700, this);
            g.drawImage(boden1, 504, 700, this);
            g.drawImage(boden1, 672, 700, this);
            g.drawImage(boden1, 840, 700, this);
            g.drawImage(boden1, 1008, 700, this);


            if (tod >= 1) {
                start.setVisible(true);
                int t = 0;
                if (zaehl >= 1) {
                    t = zaehl;
                }
                add(start);
                start.setOpaque(false);
                start.setContentAreaFilled(false);
                start.setBorderPainted(false);
                start.setBounds(420, 430, 177, 100);
                g.drawImage(todesbild, 302, 195, this);
                createNummer();
                g.drawImage(Start, 412, 420, this);
                if (t >= 10 && t < 20) {
                    g.drawImage(bronze, 345, 270, this);
                }
                if (t >= 20 && t < 30) {
                    g.drawImage(silber, 345, 270, this);
                }
                if (t >= 30 && t <= 40) {
                    g.drawImage(gold, 345, 270, this);
                }
                if (t > 40) {
                    g.drawImage(platin, 345, 270, this);
                }
            }
        }
        if (zaehl > 50) {
            timer5.stop();
            g.drawImage(fertig, 150, 140, this);
        }
        if (zaehl > 47) {
            timer.stop();
        }
        if (!isStarted()) {
            return;
        }

        if (actors != null) {
            for (Drawable draw : actors) {
                draw.drawObjects(g);
            }
        }
    }


    @Override
    public void run() {
        while (game_running) {
            computeDelta();


            if (isStarted()) {
                checkKeys();
                doLogic();
                moveObjects();
            }

            repaint();

            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
            }
        }

    }

    public void checkKeys() {
        if ((int) Flappy1.getY() <= 5) {
            test = (int) Flappy1.getY() + 85;
            if ((int) Flappy1.getY() < test) {
                Flappy1.setVerticalSpeed(speed * 4);
            }
        } else {
            if (up) {
                flapp = true;
                up = false;
                test = (int) Flappy1.getY() - 85;
            } else {
                if ((int) Flappy1.getY() < test)
                    flapp = false;
            }
            if (flapp) {
                Flappy1.setVerticalSpeed(-speed * 4);
            } else {
                Flappy1.setVerticalSpeed(speed * 4);
            }
        }
    }

    public void doLogic() {
        Vector<Sprite> trash = new Vector<Sprite>();
        for (Movable mov : actors) {
            mov.doLogic(delta);
            Sprite check = (Sprite) mov;
            if (check.remove) {
                trash.add(check);
            }
        }


        for (int i = 0; i < actors.size(); i++) {
            for (int n = i + 1; n < actors.size(); n++) {
                Sprite s1 = actors.elementAt(i);
                Sprite s2 = actors.elementAt(n);

                s1.collidedWith(s2);
            }
        }
        if (trash.size() > 0) {
            actors.removeAll(trash);
            trash.clear();
        }

        if (gameover > 0) {
            if (System.currentTimeMillis() - gameover > 200) {
                stopGame();
            }
        }
    }

    private void stopGame() {
        timer.stop();
        timer1.stop();
        timer3.stop();
        timer2.stop();
        slib.stopLoopingSound();
        setStarted(false);
        z = 0;
        s = 0;
        probe = 0;
        sound = 0;
        up = false;
        flapp = false;
    }

    public void moveObjects() {
        for (Movable mov : actors) {
            mov.move(delta);
        }
    }

    private BufferedImage[] loadPics(String path, int pics) {

        BufferedImage[] anim = new BufferedImage[pics];
        BufferedImage source = null;
        URL pic_url = getClass().getClassLoader().getResource(path);

        try {
            source = ImageIO.read(pic_url);
        } catch (IOException e) {
        }

        for (int x = 0; x < pics; x++) {
            anim[x] = source.getSubimage(x * source.getWidth() / pics, 0, source.getWidth() / pics, source.getHeight());
        }
        return anim;
    }


    @Override
    public void mousePressed(MouseEvent m) {
        up = true;
        if (isStarted()) {
            slib.playSound("clicksound");
        }
        if (!isStarted() && tod == 0) {
            setStarted(true);
            doInitializations();
            timer.start();
            timer1.start();
            timer2.start();
            timer4.start();
            timer5.start();
            tod = 0;
            tod++;
            k++;
            zaehl = 0;
        }
    }

    public void mouseReleased(MouseEvent m) {

    }

    public void mouseEntered(MouseEvent m) {

    }

    @Override
    public void mouseExited(MouseEvent m) {

    }

    public void mouseClicked(MouseEvent m) {
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (isStarted()) {
                stopGame();
            } else {
                setStarted(false);
                System.exit(0);
            }
        }
    }


    public void keyTyped(KeyEvent e) {

    }


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {

        this.started = started;
    }


    public void actionPerformed(ActionEvent e) {
        if (isStarted() && e.getSource().equals(timer)) {
            createRoehre();
            ka = 1;
        }
        if (isStarted() && e.getSource().equals(timer1)) {
            createBoden();
        }
        if (isStarted() && e.getSource().equals(timer2)) {
            createNummer();
            timer2.stop();
        }
        if (isStarted() && e.getSource().equals(timer3)) {
            timer4.stop();
        }
        if (isStarted() && e.getSource().equals(timer4)) {
            timer3.start();
        }
        if (isStarted() && e.getSource().equals(timer5)) {
            createPunkte();
            ka = 1;
        }
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tod > 0) {
                setStarted(true);
                doInitializations();
                timer.start();
                timer1.start();
                timer2.start();
                timer4.start();
                timer5.start();
                tod++;
                k++;
                zaehl = 0;
            }
        }
    }
}