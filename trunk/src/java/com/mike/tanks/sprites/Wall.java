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
    private Line2D.Double topEdgeLine;
    private Line2D.Double bottomEdgeLine;
    private Line2D.Double leftEdgeLine;
    private Line2D.Double rightEdgeLine;

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
        if (this.topEdgeLine == null) {
            this.topEdgeLine = new Line2D.Double(x, y, x + width, y);
        }

        return this.topEdgeLine;
    }

    public Line2D getBottomEdgeLine() {
        if (this.bottomEdgeLine == null) {
            this.bottomEdgeLine = new Line2D.Double(x, y + height, x + width, y + height);
        }

        return this.bottomEdgeLine;
    }

    public Line2D getLeftEdgeLine() {
        if (leftEdgeLine == null) {
            leftEdgeLine = new Line2D.Double(x, y, x, y + height);
        }

        return leftEdgeLine;
    }

    public Line2D getRightEdgeLine() {
        if (rightEdgeLine == null) {
            rightEdgeLine = new Line2D.Double(x + width, y, x + width, y + height);
        }
        
        return rightEdgeLine;
    }
}
