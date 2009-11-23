package com.mike.tanks.movement;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * User: mjparme
 * Date: Nov 23, 2009
 * Time: 2:19:20 PM
 */
public class ArrowMovementKeyAdapter extends KeyAdapter {
    private Set<Integer> pressedKeys;
    private List<IListenForArrowDirection> listeners;

    public ArrowMovementKeyAdapter() {
        this.pressedKeys = new HashSet<Integer>();
        this.listeners = new ArrayList<IListenForArrowDirection>();
    }

    public void addArrowDirectionListener(IListenForArrowDirection listener) {
        this.listeners.add(listener);
    }

    public void removeArrowDirectionListener(IListenForArrowDirection listener) {
        this.listeners.remove(listener);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                pressedKeys.add(keyCode);
                break;
        }

        this.setTankMovementState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        this.setTankMovementState();
    }

    public void setTankMovementState() {
        Direction direction = null;
        if (pressedKeys.isEmpty()) {
            direction = Direction.STOP;
        } else {
            //Stop any delta for directions we aren't moving anymore
            if (!pressedKeys.contains(KeyEvent.VK_LEFT) && !pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                this.notifyStopXMovement();
            }

            if (!pressedKeys.contains(KeyEvent.VK_UP) && !pressedKeys.contains(KeyEvent.VK_DOWN)) {
                this.notifyStopYMovement();
            }

            //Do a single pass through the Set to find which keys are pressed
            boolean downPressed = false;
            boolean upPressed = false;
            boolean leftPressed = false;
            boolean rightPressed = false;
            for (Integer pressedKey : pressedKeys) {
                if (pressedKey == KeyEvent.VK_DOWN) {
                    downPressed = true;
                } else if (pressedKey == KeyEvent.VK_UP) {
                    upPressed = true;
                } else if (pressedKey == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                } else if (pressedKey == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                }
            }

            if (upPressed && leftPressed) {
                direction = Direction.NORTH_WEST;
            } else if (upPressed && rightPressed) {
                direction = Direction.NORTH_EAST;
            } else if (downPressed && leftPressed) {
                direction = Direction.SOUTH_WEST;
            } else if (downPressed && rightPressed) {
                direction = Direction.SOUTH_EAST;
            } else if (downPressed) {
                direction = Direction.SOUTH;
            } else if (upPressed) {
                direction = Direction.NORTH;
            } else if (leftPressed) {
                direction = Direction.WEST;
            } else if (rightPressed) {
                direction = Direction.EAST;
            }
        }

        this.notifyDirectionChange(direction == null ? Direction.STOP : direction);
    }

    private void notifyStopXMovement() {
        for (IListenForArrowDirection listener : listeners) {
            listener.handleStopXMovement();
        }
    }

    private void notifyStopYMovement() {
        for (IListenForArrowDirection listener : listeners) {
            listener.handleStopYMovement();
        }
    }

    private void notifyDirectionChange(Direction direction) {
        for (IListenForArrowDirection listener : listeners) {
            listener.handleMovement(direction);
        }
    }
}

