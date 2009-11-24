package com.mike.tanks.sprites;

import com.mike.tanks.movement.Direction;

import java.awt.image.BufferedImage;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 3:45:33 PM
 */
public class BulletSprite extends AbstractSprite {
    private int bouncesToLive;
    private int bounces;

    public BulletSprite(int x, int y, int gamePanelWidth, int gamePanelHeight, BufferedImage image, Direction startingDirection) {
        super(x, y, gamePanelWidth, gamePanelHeight, image);
        currentDirection = startingDirection;
        stepSize = 4;
        size = 6;
        moveSprite(startingDirection);
    }

    @Override
    public void updateSprite() {
        if ((x <= 0 && deltaX < 0) || (x + width >= panelWidth && deltaX > 0)) {
            deltaX = -deltaX;
            this.bounces++;
        }

        if ((y <= 0 && deltaY < 0) || (y + height >= panelHeight && deltaY > 0)) {
            deltaY = -deltaY;
            this.bounces++;
        }

        super.updateSprite();
    }

    public boolean isExpired() {
        return this.bounces > this.bouncesToLive;  
    }

    public void setBouncesToLive(int bouncesToLive) {
        this.bouncesToLive = bouncesToLive;
    }
}
