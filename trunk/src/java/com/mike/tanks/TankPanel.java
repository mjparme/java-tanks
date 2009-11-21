package com.mike.tanks;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import com.mike.tanks.sprites.TankSprite;

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
    private static final int NO_DELAYS_PER_YIELD = 16;
    /* Number of frames with a delay of 0 ms before the
       animation thread yields to other running threads. */

    private static int MAX_FRAME_SKIPS = 5;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

    private static final int PWIDTH = 500;
    private static final int PHEIGHT = 400;

    private Thread animator;
    private volatile boolean running;
    private volatile boolean gameOver;
    private volatile boolean isPaused;
    private Graphics dbg;
    private Image dbImage;
    private TankSprite player1Tank;

    public TankPanel() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

        setFocusable(true);
        requestFocus();
        addKeyListener(new TankKeyAdapter());

        this.player1Tank = new TankSprite(PWIDTH, PHEIGHT);
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
                    Thread.sleep(sleepTime / 1000000L);  // nano -> ms
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
                    Thread.yield();   // give another thread a chance to run
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
            int skips = 0;
            while ((excess > PERIOD) && (skips < MAX_FRAME_SKIPS)) {
                excess -= PERIOD;
                this.gameUpdate();      // update state but don't render
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
                System.out.println("dbImage is null");
                return;
            } else {
                this.dbg = this.dbImage.getGraphics();
            }
        }

        // clear the background
        this.dbg.setColor(Color.white);
        this.dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

        this.player1Tank.drawSprite(this.dbg);
        if (this.gameOver) {

        }
    }


    private void gameUpdate() {
        if (!this.isPaused && !this.gameOver) {
            this.player1Tank.updateSprite();
        }
    }

    public void pauseGame() {
        this.isPaused = true;
    }

    public void resumeGame() {
        this.isPaused = false;
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

    /**
     * Controls tank movement. Tanks can move in two directions at once (down/right, up/left, etc) so we have to
     * keep track of keys that have been pressed but not released
     */
    private class TankKeyAdapter extends KeyAdapter {
        private Set<Integer> pressedKeys;

        private TankKeyAdapter() {
            this.pressedKeys = new HashSet<Integer>();
        }

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_ESCAPE:
                    running = false;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    pressedKeys.add(keyCode);
                    break;
            }

            this.setTankMovementState();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressedKeys.remove(e.getKeyCode());
            this.setTankMovementState();
        }

        public void setTankMovementState() {
            if (pressedKeys.isEmpty()) {
                player1Tank.stopTank();
            } else {
                //Stop any delta for directions we aren't moving anymore
                if (!pressedKeys.contains(KeyEvent.VK_LEFT) && !pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                    player1Tank.stopXMovement();
                }

                if (!pressedKeys.contains(KeyEvent.VK_UP) && !pressedKeys.contains(KeyEvent.VK_DOWN)) {
                    player1Tank.stopYMovement();
                }

                //Set movement for any keys that are pressed
                for (Integer pressedKey : pressedKeys) {
                    switch (pressedKey) {
                        case KeyEvent.VK_DOWN:
                            player1Tank.moveTankDown();
                            break;
                        case KeyEvent.VK_UP:
                            player1Tank.moveTankUp();
                            break;
                        case KeyEvent.VK_LEFT:
                            player1Tank.moveTankLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            player1Tank.moveTankRight();
                            break;
                    }
                }
            }
        }
    }
}
