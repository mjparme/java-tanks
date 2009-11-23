package com.mike.tanks.sprites;

import com.mike.tanks.movement.Direction;

import java.awt.image.BufferedImage;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 3:45:33 PM
 */
public class BulletSprite extends AbstractSprite {
    public BulletSprite(int x, int y, int gamePanelWidth, int gamePanelHeight, BufferedImage image, Direction startingDirection) {
        super(x, y, gamePanelWidth, gamePanelHeight, image);
        currentDirection = startingDirection;
        stepSize = 4;
        moveSprite(startingDirection);
    }

    @Override
    public void updateSprite() {
        System.out.println("deltaX = " + deltaX + " deltaY = " + deltaY);
        super.updateSprite();
    }
}
