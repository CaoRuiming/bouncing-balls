package edu.brown.cs.rcao6.bballs;

import edu.brown.cs.rcao6.bballs.worlds.World;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = (int)(screenSize.getWidth() * 0.75);
        int windowHeight = (int)(screenSize.getHeight() * 0.75);
        World world = new World(windowWidth, windowHeight);
        Display worldDisplay = new Display(windowWidth, windowHeight, world);
        world.setDisplay(worldDisplay);
        worldDisplay.run();
    }
}
