package com.mike.tanks.sprites;

/**
 * User: mjparme
 * Date: Nov 20, 2009
 * Time: 10:21:50 AM
 */
public class TankSprite extends Sprite {
    private final static int STARTING_STEP = 0;
    private final static int STEP_SIZE = 3;

    public TankSprite(int panelWidth, int panelHeight) {
        super(0, 0, panelWidth, panelHeight, null);
    }

    @Override
    public void updateSprite() {
        if ((x <= 0 && deltaX < 0) || (x > panelWidth && deltaX > 0)) {
            this.stopXMovement();
        }

        if ((y < 0 && deltaY < 0) || (y > panelHeight && deltaY > 0)) {
            this.stopYMovement();
        }
        
        super.updateSprite();
    }

    public void moveTankDown() {
        deltaY = STEP_SIZE;
    }

    public void moveTankUp() {
        deltaY = -STEP_SIZE;
    }

    public void moveTankLeft() {
        deltaX = -STEP_SIZE;
    }

    public void moveTankRight() {
        deltaX = STEP_SIZE;
    }

    public void stopTank() {
        this.stopXMovement();
        this.stopYMovement();
    }

    public void stopXMovement() {
        deltaX = STARTING_STEP;
    }

    public void stopYMovement() {
        deltaY = STARTING_STEP;
    }
}
