package com.mike.tanks;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.mike.tanks.sprites.TankSprite;
import com.mike.tanks.sprites.BulletSprite;
import com.mike.tanks.ImageLoader;
import com.mike.tanks.Images;
import com.mike.tanks.movement.Direction;
import com.mike.tanks.movement.ArrowMovementKeyAdapter;
import com.mike.tanks.movement.IListenForArrowDirection;

/**
 * User: mjparme
 * Date: Aug 14, 2009
 * Time: 2:15:38 PM
 * <p/>
 * Almost all of this class was taken from Killer Game Programming in Java
 * http://fivedots.coe.psu.ac.th/~ad/jg/
 */
public class TankPanel extends JPanel implements Runnable {
    private final static Logger logger = Logger.getLogger(TankPanel.class);

    private final static long PERIOD = 41 * 1000000L;

    //Number of frames with a delay of 0 ms before the animation thread yields to other running threads
    private static final int NO_DELAYS_PER_YIELD = 16;

    //Number of frames that can be skipped in any one animation loop i.e the games state is updated but not rendered
    private static int MAX_FRAME_SKIPS = 5;

    private static final int PWIDTH = 500;
    private static final int PHEIGHT = 400;

    private Thread animator;
    private boolean running;
    private boolean gameOver;
    private boolean isPaused;
    private Graphics dbg;
    private Image dbImage;
    private TankSprite player1Tank;
    private ImageLoader imageLoader;
    private List<BulletSprite> bullets;
    private int maxNumOfBullets;

    public TankPanel() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

        setFocusable(true);
        requestFocus();
        final ArrowMovementKeyAdapter movementKeyAdapter = new ArrowMovementKeyAdapter();
        addKeyListener(movementKeyAdapter);
        movementKeyAdapter.addArrowDirectionListener(new TankMovement());

        this.imageLoader = new ImageLoader();
        this.bullets = new ArrayList<BulletSprite>();
        this.player1Tank = new TankSprite(PWIDTH, PHEIGHT, this.imageLoader.getImage(Images.TANK.getImageName()));
        this.maxNumOfBullets = 3;
    }

    public void addNotify() {
        super.addNotify();
        this.startGame();
    }

    private void startGame() {
        if (this.animator == null || !this.running) {
            this.animator = new Thread(this);
            this.animator.start();
        }
    }

    public void stopGame() {
        this.running = false;
    }

    public void run() {
        long beforeTime;
        long afterTime;
        long timeDiff;
        long sleepTime;
        long overSleepTime = 0L;
        long excess = 0L;
        int noDelays = 0;

        beforeTime = System.nanoTime();

        this.running = true;
        while (this.running) {
            this.gameUpdate();
            this.gameRender();
            this.paintScreen();

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (PERIOD - timeDiff) - overSleepTime;

            // some time left in this cycle
            if (sleepTime > 0) {
                try {
                    //Nano -> ms
                    Thread.sleep(sleepTime / 1000000L);
                } catch (InterruptedException ex) {
                    logger.error("Exception: ", ex);
                }
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else {
                // store excess time value
                excess -= sleepTime;

                // sleepTime <= 0; frame took longer than the period
                overSleepTime = 0L;

                if (++noDelays >= NO_DELAYS_PER_YIELD) {
                    //Give another thread a chance to run
                    Thread.yield();
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
            int skips = 0;
            while ((excess > PERIOD) && (skips < MAX_FRAME_SKIPS)) {
                excess -= PERIOD;
                //Update state but don't render
                this.gameUpdate();
                skips++;
            }
        }

        System.exit(0);
    }

    private void gameRender() {
        // draw the current frame to an image buffer
        if (this.dbImage == null) {
            this.dbImage = createImage(PWIDTH, PHEIGHT);
            if (this.dbImage == null) {
                return;
            } else {
                this.dbg = this.dbImage.getGraphics();
            }
        }

        // clear the background
        this.dbg.setColor(Color.white);
        this.dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

        this.player1Tank.drawSprite(this.dbg);
        try {
            for (BulletSprite bullet : bullets) {
                bullet.drawSprite(this.dbg);
            }
        } catch (ConcurrentModificationException e) {
            //too expensive to sync in the animation loop, just ignore
        }

        if (this.gameOver) {

        }
    }


    private void gameUpdate() {
        if (!this.isPaused && !this.gameOver) {
            this.player1Tank.updateSprite();
            try {
                for (Iterator<BulletSprite> it = bullets.iterator(); it.hasNext();) {
                    BulletSprite bullet = it.next();
                    bullet.updateSprite();
                    if (bullet.isExpired()) {
                        it.remove();
                    }
                }
            } catch (ConcurrentModificationException e) {
                //too expensive to sync in the animation loop, just ignore
            }
        }
    }

    public void pauseGame() {
        this.isPaused = true;
    }

    public void resumeGame() {
        this.isPaused = false;
    }

    public void setMaxNumOfBullets(int maxNumOfBullets) {
        this.maxNumOfBullets = maxNumOfBullets;
    }

    private void paintScreen() {
        // actively render the buffer image to the screen
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (this.dbImage != null)) {
                g.drawImage(this.dbImage, 0, 0, null);
            }

            //Sync the display on some systems
            Toolkit.getDefaultToolkit().sync();
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
        }
    }

    private void tankFires() {
        if (!(bullets.size() >= maxNumOfBullets)) {
            BulletSprite bullet = new BulletSprite(player1Tank.getX() + player1Tank.getWidth() / 2, player1Tank.getY(), PWIDTH, PHEIGHT, null, player1Tank.getCurrentDirection());
            bullet.setBouncesToLive(3);
            bullets.add(bullet);
        }
    }

    private class TankMovement implements IListenForArrowDirection {
        public void handleStopXMovement() {
            player1Tank.stopXMovement();
        }

        public void handleStopYMovement() {
            player1Tank.stopYMovement();

        }

        public void handleMovement(Direction direction) {
            player1Tank.moveSprite(direction);
        }

        public void fire() {
            tankFires();
        }
    }
}
