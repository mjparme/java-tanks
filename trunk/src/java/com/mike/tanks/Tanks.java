package com.mike.tanks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

/**
 * User: mjparme
 * Date: Nov 20, 2009
 * Time: 10:26:03 AM
 */
public class Tanks {
    public Tanks() {
        constructGui();
    }

    private void constructGui() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new TankPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
