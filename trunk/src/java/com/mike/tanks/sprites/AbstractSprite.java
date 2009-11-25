package com.mike.tanks.sprites;

import com.mike.tanks.movement.Direction;

import java.awt.*;
import java.awt.image.*;


/**
 * The concept and parts of this class was taken from Killer Game Programming in Java (Chapter 11)
 * http://fivedots.coe.psu.ac.th/~ad/jg/
 */
public abstract class AbstractSprite {
    protected Direction spriteDirection;
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
        if (image != null) {
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
        return this.x;
    }

    public int getY() {
        return this.y;
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

    public Direction getSpriteDirection() {
        return this.spriteDirection;
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

    /**
     * Determines where this sprite will be located after the next update
     *
     * @return a Point containing the x,y of the next location of this sprite
     */
    public Point getLookAheadPoint() {
        return new Point(this.x + this.deltaX, this.y + this.deltaY);
    }

    /**
     * Determines what the rectangle of this sprite will be after the next update
     *
     * @return a Rectangle of this next location of this sprite
     */
    public Rectangle getLookAheadRectangle() {
        Point lookAheadPoint = this.getLookAheadPoint();
        return new Rectangle(lookAheadPoint.x, lookAheadPoint.y, this.width, this.height);
    }

    public boolean goneOffScreenX() {
        return this.x <= 0 && this.deltaX < 0 || this.x + this.width >= this.panelWidth && this.deltaX > 0;
    }

    public boolean goneOffScreenY() {
        return this.y <= 0 && this.deltaY < 0 || this.y + this.height >= this.panelHeight && this.deltaY > 0;
    }

    public void reverseX() {
        if (this.deltaX != 0) {
            this.deltaX = -this.deltaX;
        }
    }

    public void reverseY() {
        if (this.deltaY != 0) {
            this.deltaY = -this.deltaY;
        }
    }

    public void drawSprite(Graphics g) {
        if (this.isActive) {
            if (this.image == null) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Color.BLACK);
                g.fillOval(this.x, this.y, this.width, this.height);
            } else {
                g.drawImage(this.image, this.x, this.y, null);
            }
        }
    }

    /**
     * Determines if this sprite intersects any of the sprites in the passed in List of sprites
     *
     * @param sprites a List of sprites to check this sprite for intersection with
     * @return true if any of the sprites in the passed in List intersects this sprite, false otherwise
     */
    public boolean spritesIntersect(java.util.List<? extends AbstractSprite> sprites) {
        boolean intersects = false;
        for (AbstractSprite sprite : sprites) {
            intersects = this.getMyRectangle().intersects(sprite.getMyRectangle());
            if (intersects) {
                break;
            }
        }

        return intersects;
    }

    /**
     * Determines if this sprite intersects any of the sprites in the passed in List of sprites
     *
     * @param sprites a List of sprites to check this sprite for intersection with
     * @return true if any of the sprites in the passed in List intersects this sprite, false otherwise
     */
    public boolean willSpritesIntersect(java.util.List<? extends AbstractSprite> sprites) {
        boolean intersects = false;
        for (AbstractSprite sprite : sprites) {
            intersects = this.getLookAheadRectangle().intersects(sprite.getMyRectangle());
            if (intersects) {
                break;
            }
        }

        return intersects;
    }


    /**
     * Determines if this sprite will intersect the passed in sprite after the next update
     *
     * @param sprite a sprite to check this sprite for intersection with
     * @return true if the passed in sprite will intersect this sprite on the next update, false otherwise
     */
    public boolean spritesIntersect(AbstractSprite sprite) {
        return this.getMyRectangle().intersects(sprite.getMyRectangle());
    }

    /**
     * Determines if this sprite will intersect the passed in sprite after the next update
     *
     * @param sprite a sprite to check this sprite for intersection with
     * @return true if the passed in sprite will intersect this sprite after the next update, false otherwise
     */
    public boolean willSpritesIntersect(AbstractSprite sprite) {
        return this.getLookAheadRectangle().intersects(sprite.getMyRectangle());
    }

    public void setSpriteDirection(Direction direction) {
        switch (direction) {
            case EAST:
                this.spriteDirection = direction;
                this.moveSpriteEast();
                break;
            case NORTH:
                this.spriteDirection = direction;
                this.moveSpriteNorth();
                break;
            case NORTH_EAST:
                this.spriteDirection = direction;
                this.moveSpriteNorth();
                this.moveSpriteEast();
                break;
            case NORTH_WEST:
                this.spriteDirection = direction;
                this.moveSpriteNorth();
                this.moveSpriteWest();
                break;
            case SOUTH:
                this.spriteDirection = direction;
                this.moveSpriteSouth();
                break;
            case SOUTH_EAST:
                this.spriteDirection = direction;
                this.moveSpriteSouth();
                this.moveSpriteEast();
                break;
            case SOUTH_WEST:
                this.spriteDirection = direction;
                this.moveSpriteSouth();
                this.moveSpriteWest();
                break;
            case WEST:
                this.spriteDirection = direction;
                this.moveSpriteWest();
                break;
            case NONE:
            case STOP:
                this.stopSprite();
                break;
        }
    }

    private void moveSpriteSouth() {
        this.deltaY = this.stepSize;
    }

    private void moveSpriteNorth() {
        this.deltaY = -this.stepSize;
    }

    private void moveSpriteWest() {
        this.deltaX = -this.stepSize;
    }

    private void moveSpriteEast() {
        this.deltaX = this.stepSize;
    }

    private void stopSprite() {
        this.stopXMovement();
        this.stopYMovement();
    }

    public double degreesToRadians(int degrees) {
        return (degrees * Math.PI) / 180;
    }

    /**
     * Stops the sprite moving along the X axis
     */
    public void stopXMovement() {
        this.deltaX = 0;
    }

    /**
     * Stops the sprite moving along the Y axis
     */
    public void stopYMovement() {
        this.deltaY = 0;
    }
}
