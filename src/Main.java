package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Cay Horstmann
 * @version 1.34 2018-04-10
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            DrawFrame frame = null;
            try {
                frame = new DrawFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert frame != null;
            frame.setTitle("Outer Space with Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

/**
 * A frame that contains a panel with drawings.
 */
class DrawFrame extends JFrame {
    public DrawFrame() throws IOException {
        DrawComponent component = new DrawComponent();
        add(new DrawComponent());
        pack();
    }
}

/**
 * A component that displays rectangles and ellipses.
 */
class DrawComponent extends JPanel implements ActionListener, KeyListener {
    private static final int DEFAULT_WIDTH = 850;
    private static final int DEFAULT_HEIGHT = 850;
    Timer t = new Timer(5, this);
    double x = 100, y = 100, velX = 0, velY = 0;

    BufferedImage spaceship;
    double leftX = 100, topY = 100, width = 600, height = 600;

    public DrawComponent() throws IOException {
        t.start();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        spaceship = ImageIO.read((Objects.requireNonNull(Main.class.getResourceAsStream("download.jpg"))));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // draw a rectangle
        Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
        g2.draw(rect);

        g2.setColor(Color.BLACK);

        g2.drawImage(spaceship, (int)x, (int)y, null);
        g2.drawString("OUTER SPACE", 350, 80);
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (x < 100 || x > 660) {
            velX = -velX;
        }

        if (y < 100 || y > 660) {
            velY = -velY;
        }

        repaint();
        x += velX;
        y += velY;
    }

    public void up() {
        velY = -1.5;
        velX = 0;
    }

    public void down() {
        velY = 1.5;
        velX = 0;
    }

    public void left() {
        velY = 0;
        velX = -1.5;
    }

    public void right() {
        velY = 0;
        velX = 1.5;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            up();
        }

        if (code == KeyEvent.VK_DOWN) {
            down();
        }

        if (code == KeyEvent.VK_LEFT) {
            left();
        }

        if (code == KeyEvent.VK_RIGHT) {
            right();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // this part of the code when commented out lets you move the jpg by just pressing the arrows keys but moves in one continous direction
//        int code = e.getKeyCode();
//        if (code == KeyEvent.VK_UP) {
//            velY = 0;
//        }
//
//        if (code == KeyEvent.VK_DOWN) {
//            velY = 0;
//        }
//
//        if (code == KeyEvent.VK_LEFT) {
//            velX = 0;
//        }
//
//        if (code == KeyEvent.VK_RIGHT) {
//            velX = 0;
//        }
    }
}
