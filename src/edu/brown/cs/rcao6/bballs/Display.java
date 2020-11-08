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

/**
 * Class representing the user-visible screen/window. Instances of this class contain the loop that "steps" World
 * instances.
 */
public class Display extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private static final Map<String, Image> images = new HashMap<>();

    /**
     * Given a file path to an image file (relative to this class), retrieves the image, caches the image, and returns
     * the image.
     * @param name file path to image file
     * @return image read from given file path
     */
    public static Image getImage(String name) {
        try {
            Image image = images.get(name);
            if (image == null) {
                URL url = Display.class.getResource(name);
                if (url == null)
                    throw new RuntimeException("unable to load image: " + name);
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
    private final Cursor blankCursor;
    private int keyDown;
    private int keyUp;
    private int mouseX;
    private int mouseY;
    private int mouseLocX;
    private int mouseLocY;
    private final World world;
    private boolean terminated;

    /**
     * Constructs a new instance of Display given a World.
     * @param width width of frame
     * @param height height of frame
     * @param w World that Display is associated with
     */
    public Display(final int width, final int height, World w) {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        keyDown = -1;
        keyUp = -1;
        mouseX = -1;
        mouseY = -1;
        mouseLocX = -1;
        mouseLocY = -1;
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

    /**
     * Paints World to Display frame.
     * @param g instance of Graphics
     */
    public void paintComponent(Graphics g) {
        try {
            world.paintComponent(g);
        }
        catch (Exception e) {
            e.printStackTrace();  // show error
            setVisible(false);  // stop drawing so we don't keep getting the same error
        }
    }

    /**
     * Loop that continually calls the step function on World and refreshes the displayed content in frame. Also calls
     * callback functions for World when mouse and key events are detected.
     */
    public void run() {
        while (!terminated) {
            frame.setCursor(blankCursor);

            frame.setTitle(world.getTitle());

            long startTime = System.nanoTime();
            world.step();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            if (world.getTime() % 100 == 0) {
                System.out.println("Step execution time (nanoseconds): " + duration);
            }

            repaint();
            try {
                Thread.sleep(10);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
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

    public void keyPressed(KeyEvent e) {
        keyDown = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e) {
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

    public void close() {
        terminated = true;
        frame.setVisible(false);
        frame.dispose();
    }
}