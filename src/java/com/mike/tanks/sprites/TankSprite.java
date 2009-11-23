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
public class TankSprite extends AbstractSprite {
    private AffineTransform transform;

    public TankSprite(int panelWidth, int panelHeight, BufferedImage image) {
        super(0, 0, panelWidth, panelHeight, image);
        this.currentDirection = Direction.NORTH;
        this.transform = new AffineTransform();
    }

    @Override
    public void updateSprite() {
        if ((x <= 0 && deltaX < 0) || (x + width >= panelWidth && deltaX > 0)) {
            this.stopXMovement();
        }

        if ((y <= 0 && deltaY < 0) || (y + height >= panelHeight && deltaY > 0)) {
            this.stopYMovement();
        }
        
        super.updateSprite();
    }

    @Override
    public void drawSprite(Graphics g) {
        //Rotate the image if necessary
        Graphics2D g2d = (Graphics2D)g;
        //Center of rotation is the center of the tank sprite image
        this.transform.setToRotation(this.degreesToRadians(this.currentDirection.getDegrees()), x + (width / 2), y + (height / 2));
        g2d.setTransform(this.transform);
        
        super.drawSprite(g);
        g2d.setTransform(new AffineTransform());
    }

    private double degreesToRadians(int degress) {
        return (degress * Math.PI) / 180;
    }
}
