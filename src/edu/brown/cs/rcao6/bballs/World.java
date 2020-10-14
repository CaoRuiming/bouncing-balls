package edu.brown.cs.rcao6.bballs;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class that manages the overall state of the Bouncing Balls application.
 */
public class World {
    private Display display;
    private List<Sprite> sprites;
    private PlayerSprite player;
    private EnemySprite enemy;
    private final int width;
    private final int height;
    private int mouseLocX;
    private int mouseLocY;
    private int mouseClickX;
    private int mouseClickY;
    private int time;  // counts how many steps have occurred in World

    /**
     * Constructs a new instance of World given a window width and height.
     * @param width width of World
     * @param height height of World
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        sprites = new ArrayList<>();
        mouseLocX = 0;
        mouseLocY = 0;
        mouseClickX = 0;
        mouseClickY = 0;
        time = 0;

        player = new PlayerSprite(0, 0, Consts.playerWidth, Consts.playerHeight, Consts.playerImage);
        sprites.add(player);
        enemy = null;  // is set when a stage is active
    }

    /**
     * Resets World to initial state.
     */
    public void reset() {
        display = new Display(width, height, this);
        sprites = new ArrayList<>();
        mouseLocX = 0;
        mouseLocY = 0;
        mouseClickX = 0;
        mouseClickY = 0;
        time = 0;

        player = new PlayerSprite(0, 0, Consts.playerWidth, Consts.playerHeight, Consts.playerImage);
        sprites.add(player);
    }

    /**
     * Starts loop that runs World.
     */
    public void run() {
        display.run();
    }

    /**
     * Closes Display of World, World must be reset() or recreated to be visible again.
     */
    public void close() {
        display.close();
//        display = null;
    }

    /**
     * Steps World through one unit of time.
     */
    public void step() {
        // sprites for stage selection
        Sprite stageOneSprite = new Sprite(
                200, 200, Consts.stageImageSize, Consts.stageImageSize, Consts.stageOneImage);
        Sprite stageTwoSprite = new Sprite(
                400, 200, Consts.stageImageSize, Consts.stageImageSize, Consts.stageTwoImage);

        // place to initialize stages or display certain screens
        if (time == 0) {
            sprites.add(0, stageOneSprite);
            sprites.add(0, stageTwoSprite);
        }

        // handle stage pne selection
        if (stageOneSprite.containsPoint(mouseClickX, mouseClickY)) {
            StageOneWorld world = new StageOneWorld(width, height);
            Display worldDisplay = new Display(width, height, world);
            world.setDisplay(worldDisplay);
            display.close();
            worldDisplay.run();
        }
        // handle stage two selection
        if (stageTwoSprite.containsPoint(mouseClickX, mouseClickY)) {
            System.out.println("stage 2 selected");
        }

        stepAllSpritesAndIncrementTime();
    }

    /**
     * Calls the step() function of every Sprite, removes dead Sprites, and increments time.
     */
    protected void stepAllSpritesAndIncrementTime() {
        // call step() functions of Sprites and removes dead Sprites from World
        for (int i = 0; i < sprites.size(); i++) {
            if (!sprites.get(i).isAlive())
                sprites.remove(i);
            else
                sprites.get(i).step(this);
        }

        // increment time
        time++;
    }

    /**
     * Checks if given Sprite is present in World.
     * @param s Sprite to check if in World
     * @return true if and only if there exists a Sprite in World that has the same properties as given Sprite
     */
    public boolean hasSprite(Sprite s) {
        for(Sprite sprite : sprites) {
            if (sprite.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a Sprite to World.
     * @param s Sprite to add to World
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Returns list of Sprites in World.
     * @return list of Sprites in World
     */
    protected List<Sprite> getSprites() {
        return sprites;
    }

    /**
     * Returns the width of World.
     * @return width of World
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of World.
     * @return height of World
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the time of World. In other words, returns the number steps have occurred in World.
     * @return time of World
     */
    public int getTime() {
        return time;
    }

    /**
     * Set the time of World. Can be used to display game over screen.
     * @param time new time of World
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Returns the active Display of World.
     * @return active Display of World
     */
    public Display getDisplay() {
        return display;
    }

    /**
     * Sets the active Display of World.
     * @param display new active Display of World
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * Returns the PlayerSprite of World.
     * @return PlayerSprite of World
     */
    public PlayerSprite getPlayer() {
        return player;
    }

    /**
     * Sets the PlayerSprite of World.
     * @param player new PlayerSprite of World
     */
    public void setPlayer(PlayerSprite player) {
        this.player = player;
    }

    /**
     * Returns the EnemySprite of World.
     * @return EnemySprite of World
     */
    public EnemySprite getEnemy() {
        return enemy;
    }

    /**
     * Sets the EnemySprite of World.
     * @param enemy new EnemySprite of World
     */
    public void setEnemy(EnemySprite enemy) {
        this.enemy = enemy;
    }

    /**
     * Returns x coordinate of the last known mouse location in World.
     * @return x coordinate of the last known mouse location in World
     */
    public int getMouseLocX() {
        return mouseLocX;
    }

    /**
     * Returns y coordinate of the last known mouse location in World.
     * @return y coordinate of the last known mouse location in World
     */
    public int getMouseLocY() {
        return mouseLocY;
    }

    /**
     * Returns String for title of World window.
     * @return String for title of World window
     */
    public String getTitle() {
        return "Bouncing Balls!";
    }

    /**
     * Paints World and World's sprites.
     * @param g Graphics instance
     */
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            g.drawImage(Display.getImage(sprite.getImage()),
                    (int)sprite.getX(), (int)sprite.getY(),
                    sprite.getWidth(), sprite.getHeight(), null);
        }
    }

    /**
     * Callback function for when mouse is clicked on World's Display.
     * @param x x coordinate of clicked location
     * @param y y coordinate of clicked location
     */
    public void mouseClicked(int x, int y) {
        mouseClickX = x;
        mouseClickY = y;
    }

    /**
     * Callback function for when mouse is moved on World's Display.
     * @param x x coordinate of where mouse was moved to
     * @param y y coordinate of where mouse was moved to
     */
    public void mouseMoved(int x, int y) {
        // System.out.println("mouseMoved:  " + x + ", " + y);
        mouseLocX = x;
        mouseLocY = y;
    }

    /**
     * Callback function for when a key is pressed down.
     * @param keyDown key code of pressed key
     */
    public void keyPressed(int keyDown) {
        // System.out.println("keyPressed:  " + keyDown);
    }

    /**
     * Callback function for when a key is released.
     * @param keyUp key code of released key
     */
    public void keyReleased(int keyUp) {
        // System.out.println("keyPressed:  " + keyUp);
    }
}