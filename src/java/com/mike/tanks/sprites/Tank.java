package com.mike.tanks.sprites;

import com.mike.tanks.movement.Direction;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * User: mjparme
 * Date: Nov 20, 2009
 * Time: 10:21:50 AM
 */
public class Tank extends AbstractSprite {
    private AffineTransform transform;

    public Tank(int panelWidth, int panelHeight, BufferedImage image) {
        //want the tank to be facing east halfway down the game area
        super(3, panelHeight / 2, panelWidth, panelHeight, image);
        this.spriteDirection = Direction.EAST;
        this.transform = new AffineTransform();
        stepSize = 3;
    }

    @Override
    public void updateSprite() {
        if (goneOffScreenX()) {
            this.stopXMovement();
        }

        if (goneOffScreenY()) {
            this.stopYMovement();
        }
        
        super.updateSprite();
    }

    @Override
    public void drawSprite(Graphics g) {
        //Rotate the image if necessary
        Graphics2D g2d = (Graphics2D)g;
        //Center of rotation is the center of the tank sprite image
        this.transform.setToRotation(degreesToRadians(this.spriteDirection.getDegrees()), x + (width / 2), y + (height / 2));
        g2d.setTransform(this.transform);
        
        super.drawSprite(g);
        g2d.setTransform(new AffineTransform());
    }

    public Point getCannonLocation() {
        return new Point(x + (width / 2), y);
    }
}
