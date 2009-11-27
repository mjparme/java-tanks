package com.mike.tanks.sprites;

import com.mike.tanks.movement.Direction;

import java.awt.image.BufferedImage;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 3:45:33 PM
 */
public class Bullet extends AbstractSprite {
    private int bouncesToLive;
    private int bounces;

    public Bullet(int x, int y, int gamePanelWidth, int gamePanelHeight, BufferedImage image, Direction startingDirection) {
        super(x, y, gamePanelWidth, gamePanelHeight, image);
        spriteDirection = startingDirection;
        stepSize = 4;
        height = 6;
        width = 6;
        setSpriteDirection(startingDirection);
    }

    @Override
    public void updateSprite() {
        if (goneOffScreenX()) {
            reverseX();
            this.bounces++;
        }

        if (goneOffScreenY()) {
            reverseY();
            this.bounces++;
        }

        super.updateSprite();
    }

    public boolean isExpired() {
        return this.bounces > this.bouncesToLive;  
    }

    public void incrementBounces() {
        System.out.println("incrementing bounce: " + this.bounces);
        this.bounces++;
    }

    public void setBouncesToLive(int bouncesToLive) {
        this.bouncesToLive = bouncesToLive;
    }
}
