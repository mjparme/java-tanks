package com.mike.tanks.sprites;

import java.awt.image.BufferedImage;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 3:45:33 PM
 */
public class BulletSprite extends AbstractSprite {
    public BulletSprite(int x, int y, int gamePanelWidth, int gamePanelHeight, BufferedImage image) {
        super(x, y, gamePanelWidth, gamePanelHeight, image);
        deltaX = 4;
        deltaY = 4;
    }
}
