package com.mike.tanks.sprites;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * User: mjparme
 * Date: Nov 25, 2009
 * Time: 10:32:35 AM
 */
public class Wall extends AbstractSprite {
    public Wall(int x, int y, int gamePanelWidth, int gamePanelHeight, BufferedImage image) {
        super(x, y, gamePanelWidth, gamePanelHeight, image);
        stepSize = 0;
        width = 50;
        height = 50;
        deltaX = 0;
        deltaY = 0;
    }

    @Override
    public void drawSprite(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void updateSprite() {
        //Sprite is stationary, don't update
    }

    public Line2D getTopEdgeLine() {
        return new Line2D.Double(x, y, x + width, y);
    }

    public Line2D getBottomEdgeLine() {
        return new Line2D.Double(x, y + height, x + width, y + height);
    }

    public Line2D getLeftEdgeLine() {
        return new Line2D.Double(x, y, x, y + height);
    }

    public Line2D getRightEdgeLine() {
        return new Line2D.Double(x + width, y, x + width, y + height);
    }
}
