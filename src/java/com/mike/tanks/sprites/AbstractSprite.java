package com.mike.tanks.sprites;

import java.awt.*;
import java.awt.image.*;


/**
 * This class was taken from Killer Game Programming in Java
 * http://fivedots.coe.psu.ac.th/~ad/jg/
 */
public abstract class AbstractSprite {
    private static final int SIZE = 12;

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

    public AbstractSprite() {
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public AbstractSprite(int x, int y, int width, int height, BufferedImage image) {
        this();
        this.x = x;
        this.y = y;
        this.panelWidth = width;
        this.panelHeight = height;
        this.setImage(image);
    }

    public void setImage(BufferedImage image) {
        if (image == null) {
            this.width = SIZE;
            this.height = SIZE;
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int xDist, int yDist) {
        this.x += xDist;
        this.y += yDist;
    }

    public void setDeltas(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
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
                g.fillOval(this.x, this.y, SIZE, SIZE);
            } else {
                g.drawImage(this.image, this.x, this.y, null);
            }
        }
    }
}
