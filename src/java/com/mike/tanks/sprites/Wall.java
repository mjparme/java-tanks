package com.mike.tanks.sprites;

import java.awt.image.BufferedImage;
import java.awt.*;

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
}
