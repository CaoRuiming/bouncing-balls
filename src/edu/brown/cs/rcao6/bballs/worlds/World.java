package edu.brown.cs.rcao6.bballs.worlds;

import edu.brown.cs.rcao6.bballs.Consts;
import edu.brown.cs.rcao6.bballs.Display;
import edu.brown.cs.rcao6.bballs.sprites.EnemySprite;
import edu.brown.cs.rcao6.bballs.sprites.PlayerSprite;
import edu.brown.cs.rcao6.bballs.sprites.Sprite;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class that manages the overall state of the Bouncing Balls application.
 */
public class World {
    private Display display;
    private final List<Sprite> sprites;
    private final PlayerSprite player;
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

        // spawn the player sprite along with aura sprite (helps with visibility)
        player = new PlayerSprite(0, 0, Consts.playerWidth, Consts.playerHeight, Consts.playerImage);
        sprites.add(player.createAura());
        sprites.add(player);
        enemy = null;  // is set when a stage is active
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
            removeSprite(Consts.loseTextSprite); // remove if exists
            addSprite(Consts.stageSelectTextSprite);
            addSprite(stageOneSprite, 0);
            addSprite(stageTwoSprite, 0);
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
            StageTwoWorld world = new StageTwoWorld(width, height);
            Display worldDisplay = new Display(width, height, world);
            world.setDisplay(worldDisplay);
            display.close();
            worldDisplay.run();
        }

        // display lose screen
        if (time < 0 && !hasSprite(Consts.loseTextSprite)) {
            addSprite(Consts.loseTextSprite);
        }

        stepAllSpritesAndIncrementTime();
    }

    /**
     * Calls the step() function of every Sprite, removes dead Sprites, and increments time.
     */
    protected void stepAllSpritesAndIncrementTime() {
        // call step() functions of Sprites and removes dead Sprites from World
        for (int i = 0; i < sprites.size(); i++) {
            if (!sprites.get(i).isAlive()) {
                sprites.remove(i);
                i--;
            }
            else {
                sprites.get(i).step(this);
            }
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
     * Adds a Sprite to World with a given index (low indices are drawn first). Adds Sprite to last index if index bad.
     * @param s Sprite to add to World
     * @param index index to add Sprite into World (lower indices are drawn first)
     */
    public void addSprite(Sprite s, int index) {
        if (index >= 0 && index < sprites.size()) {
            sprites.add(index, s);
        } else {
            sprites.add(s);
        }
    }

    /**
     * Removes given Sprite from World. Uses equals() method to compare Sprites. Removes first found that matches.
     * @param s Sprite to remove.
     */
    public void removeSprite(Sprite s) {
        for (int i = 0; i < sprites.size(); i ++) {
            if (sprites.get(i).equals(s)) {
                sprites.remove(i);
                return;
            }
        }
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
        if (enemy != null) {
            return "Enemy Health: " + Math.max(enemy.getHp(), 0);
        }
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