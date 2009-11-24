package com.mike.tanks.sprites;

import com.mike.tanks.movement.Direction;

import java.awt.*;
import java.awt.image.*;


/**
 * The concept and parts of this class was taken from Killer Game Programming in Java (Chapter 11)
 * http://fivedots.coe.psu.ac.th/~ad/jg/
 */
public abstract class AbstractSprite {
    protected Direction currentDirection;
    protected int stepSize;

    private BufferedImage image;
    protected int width;
    protected int height;

    protected int panelWidth;
    protected int panelHeight;

    private boolean isActive = true;

    protected int x;
    protected int y;
    protected int deltaX;
    protected int deltaY;
    protected int size;

    public AbstractSprite() {
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public AbstractSprite(int x, int y, int gamePanelWidth, int gamePanelHeight, BufferedImage image) {
        this();
        this.x = x;
        this.y = y;
        this.panelWidth = gamePanelWidth;
        this.panelHeight = gamePanelHeight;
        this.setImage(image);
    }

    public void setImage(BufferedImage image) {
        if (image == null) {
            this.width = size;
            this.height = size;
        } else {
            this.image = image;
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPanelWidth() {
        return this.panelWidth;
    }

    public int getPanelHeight() {
        return this.panelHeight;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setPosition(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPosition() {
        return new Point(this.x, this.y);
    }

    public void translate(int xDist, int yDist) {
        this.x += xDist;
        this.y += yDist;
    }

    public void setDeltas(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Rectangle getMyRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void updateSprite() {
        if (this.isActive) {
            this.x += this.deltaX;
            this.y += this.deltaY;
        }
    }

    public void drawSprite(Graphics g) {
        if (this.isActive) {
            if (this.image == null) {
                g.setColor(Color.BLACK);
                g.fillOval(this.x, this.y, size, size);
            } else {
                g.drawImage(this.image, this.x, this.y, null);
            }
        }
    }

    public void moveSprite(Direction direction) {
        switch (direction) {
            case EAST:
                this.currentDirection = direction;
                this.moveSpriteEast();
                break;
            case NORTH:
                this.currentDirection = direction;
                this.moveSpriteNorth();
                break;
            case NORTH_EAST:
                this.currentDirection = direction;
                this.moveSpriteNorth();
                this.moveSpriteEast();
                break;
            case NORTH_WEST:
                this.currentDirection = direction;
                this.moveSpriteNorth();
                this.moveSpriteWest();
                break;
            case SOUTH:
                this.currentDirection = direction;
                this.moveSpriteSouth();
                break;
            case SOUTH_EAST:
                this.currentDirection = direction;
                this.moveSpriteSouth();
                this.moveSpriteEast();
                break;
            case SOUTH_WEST:
                this.currentDirection = direction;
                this.moveSpriteSouth();
                this.moveSpriteWest();
                break;
            case WEST:
                this.currentDirection = direction;
                this.moveSpriteWest();
                break;
            case NONE:
            case STOP:
                this.stopSprite();
                break;
        }
    }

    private void moveSpriteSouth() {
        deltaY = stepSize;
    }

    private void moveSpriteNorth() {
        deltaY = -stepSize;
    }

    private void moveSpriteWest() {
        deltaX = -stepSize;
    }

    private void moveSpriteEast() {
        deltaX = stepSize;
    }

    private void stopSprite() {
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
