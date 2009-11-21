package com.mike.tanks;

import javax.swing.*;
import java.awt.*;

/**
 * User: mjparme
 * Date: Nov 20, 2009
 * Time: 10:26:03 AM
 */
public class Tanks {
    private JFrame frame;

    public Tanks() {
        constructGui();
    }

    private void constructGui() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new TankPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                new Tanks();
            }
        };

        EventQueue.invokeLater(runnable);
    }
}
