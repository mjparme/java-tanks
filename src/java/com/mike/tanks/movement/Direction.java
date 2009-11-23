package com.mike.tanks.movement;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 9:45:56 AM
 */
public enum Direction {
    NONE(0), STOP(0),
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270),
    NORTH_WEST(315),
    NORTH_EAST(45),
    SOUTH_WEST(225),
    SOUTH_EAST(135);

    private int degrees;

    Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
