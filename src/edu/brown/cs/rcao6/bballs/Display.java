package edu.brown.cs.rcao6.bballs;

import edu.brown.cs.rcao6.bballs.worlds.World;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.image.*;

public class Display extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private static final Map<String, Image> images = new HashMap<>();

    public static Image getImage(String name) {
        try {
            Image image = images.get(name);
            if (image == null) {
                URL url = Display.class.getResource(name);
                if (url == null)
                    throw new RuntimeException("unable to load image:  " + name);
                image = ImageIO.read(url);
                images.put(name, image);
            }
            return image;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JFrame frame;
    private Cursor blankCursor;
    private int keyDown;
    private int keyUp;
    private int mouseX;
    private int mouseY;
    private int mouseLocX;
    private int mouseLocY;
    private World world;
    private int screen;
    private boolean terminated;

    public Display(final int width, final int height, World w) {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        keyDown = -1;
        keyUp = -1;
        mouseX = -1;
        mouseY = -1;
        mouseLocX = -1;
        mouseLocY = -1;
        screen = 0;
        world = w;
        terminated = false;

        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame();
                frame.setTitle("Display");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                setPreferredSize(new Dimension(width, height));
                setFocusable(true);
                requestFocusInWindow();
                addKeyListener(Display.this);
                addMouseListener(Display.this);
                addMouseMotionListener(Display.this);
                frame.getContentPane().add(Display.this);

                frame.pack();
                frame.setVisible(true);
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void paintComponent(Graphics g) {
        try {
            world.paintComponent(g);
        }
        catch (Exception e) {
            e.printStackTrace();  // show error
            setVisible(false);  // stop drawing so we don't keep getting the same error
        }
    }

    public void run() {
        while (!terminated) {
            frame.setCursor(blankCursor);

            frame.setTitle(world.getTitle());
            world.step();
            repaint();
            try { Thread.sleep(10); } catch(Exception e) { }
            if (keyDown != -1) {
                world.keyPressed(keyDown);
                keyDown = -1;
            }
            if (keyUp != -1) {
                world.keyReleased(keyUp);
                keyUp = -1;
            }
            if (mouseX != -1) {
                world.mouseClicked(mouseX, mouseY);
                mouseX = -1;
                mouseY = -1;
            }
            if (mouseLocX != -1) {
                world.mouseMoved(mouseLocX, mouseLocY);
                mouseLocX = -1;
                mouseLocY = -1;
            }
        }
    }

    public void keyPressed(KeyEvent e)
    {
        keyDown = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e)
    {
        keyUp = e.getKeyCode();
    }

    public void keyTyped(KeyEvent e) {}

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
        mouseLocX = e.getX();
        mouseLocY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public Display getDisplay()
    {
        return this;
    }

    public JFrame getJFrame()
    {
        return frame;
    }

    public void close() {
        terminated = true;
        frame.setVisible(false);
        frame.dispose();
    }
}