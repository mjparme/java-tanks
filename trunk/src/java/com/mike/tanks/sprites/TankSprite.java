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
    private final static int STEP_SIZE = 3;

    private Direction currentDirection;
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
    }

    private double degreesToRadians(int degress) {
        return (degress * Math.PI) / 180;
    }

    public void moveTank(Direction direction) {
        switch (direction) {
            case EAST:
                this.currentDirection = direction;
                this.moveTankEast();
                break;
            case NORTH:
                this.currentDirection = direction;
                this.moveTankNorth();
                break;
            case NORTH_EAST:
                this.currentDirection = direction;
                this.moveTankNorth();
                this.moveTankEast();
                break;
            case NORTH_WEST:
                this.currentDirection = direction;
                this.moveTankNorth();
                this.moveTankWest();
                break;
            case SOUTH:
                this.currentDirection = direction;
                this.moveTankSouth();
                break;
            case SOUTH_EAST:
                this.currentDirection = direction;
                this.moveTankSouth();
                this.moveTankEast();
                break;
            case SOUTH_WEST:
                this.currentDirection = direction;
                this.moveTankSouth();
                this.moveTankWest();
                break;
            case WEST:
                this.currentDirection = direction;
                this.moveTankWest();
                break;
            case NONE:
            case STOP:
                this.stopTank();
                break;
        }
    }

    private void moveTankSouth() {
        deltaY = STEP_SIZE;
    }

    private void moveTankNorth() {
        deltaY = -STEP_SIZE;
    }

    private void moveTankWest() {
        deltaX = -STEP_SIZE;
    }

    private void moveTankEast() {
        deltaX = STEP_SIZE;
    }

    private void stopTank() {
        this.stopXMovement();
        this.stopYMovement();
    }

    public void stopXMovement() {
        deltaX = 0;
    }

    public void stopYMovement() {
        deltaY = 0;
    }
}
