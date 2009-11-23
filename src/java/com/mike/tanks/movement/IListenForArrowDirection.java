package com.mike.tanks.movement;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 2:25:12 PM
 */
public interface IListenForArrowDirection {
    public void handleStopXMovement();
    public void handleStopYMovement();
    public void handleMovement(Direction direction);
    public void fire();
}
