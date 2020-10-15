package edu.brown.cs.rcao6.bballs.sprites;

import edu.brown.cs.rcao6.bballs.worlds.World;

/**
 * General class representing a Sprite that resides in a World.
 */
public class Sprite {
    private double x;
    private double y;
    private final int width;
    private final int height;
    private final String image;
    private boolean alive;

    /**
     * Constructs a new Sprite.
     * @param x x coordinate of Sprite
     * @param y y coordinate of Sprite
     * @param width width of Sprite
     * @param height height of Sprite
     * @param image image path of Sprite
     */
    public Sprite(double x, double y, int width, int height, String image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        alive = true;
    }

    /**
     * Returns the x coordinate of Sprite.
     * @return x coordinate of Sprite
     */
    public double getX() { return x; }

    /**
     * Sets the x coordinate of Sprite.
     * @param x new x coordinate of Sprite
     */
    public void setX(double x) { this.x = x; }

    /**
     * Returns the y coordinate of Sprite
     * @return y coordinate of Sprite
     */
    public double getY() { return y; }

    /**
     * Sets the y coordinate of Sprite.
     * @param y new y coordinate of Sprite
     */
    public void setY(double y) { this.y = y; }

    /**
     * Returns the height of Sprite.
     * @return height of Sprite
     */
    public int getHeight() { return height; }

    /**
     * Returns the width of Sprite.
     * @return width of Sprite
     */
    public int getWidth() { return width; }

    /**
     * Returns the image path of Sprite.
     * @return image path of Sprite
     */
    public String getImage() { return image; }

    /**
     * Returns true if Sprite is alive.
     * @return true if and only if Sprite is alive
     */
    public boolean isAlive() { return alive; }

    /**
     * Sets Sprite to not be alive.
     */
    public void kill() { alive = false; }

    /**
     * Handle Sprite behavior on world step.
     * @param world world that Sprite should interact with during step
     */
    public void step(World world) {}

    /**
     * Given x and y coordinates of a point, determines if point lies within Sprite.
     * @param pointX x coordinate of point
     * @param pointY y coordinate of point
     * @return true if and only if the point at specified coordinates is within Sprite
     */
    public boolean containsPoint(double pointX, double pointY) {
        return (pointX > getX()) && (pointX < getX() + getWidth()) &&
                (pointY > getY()) && (pointY < getY() + getHeight());
    }

    /**
     * Determine if Sprite overlaps with another Sprite.
     * @param s another Sprite
     * @return true if and only if Sprite overlaps with s
     */
    public boolean overlaps(Sprite s) {
        return (getX() < (s.getX() + s.getWidth())) && ((getX() + getWidth()) > s.getX())
                && (getY() < (s.getY() + s.getHeight())) && ((getY() + getHeight()) > s.getY());
    }

    /**
     * Determine if Sprite is identical to another Sprite.
     * @param s another Sprite
     * @return true if and only if the fields of Sprite are the same as s
     */
    public boolean equals(Sprite s) {
        return x == s.getX() && y == s.getY() && height == s.getHeight() && width == s.getWidth()
                && image.equals(s.getImage()) && alive == s.isAlive();
    }

    /**
     * Spawns MobileSprites and sets velocities appropriately given an angle and speed.
     * @param world World object to spawn sprite into
     * @param sprite Sprite to spawn
     * @param angle angle of sprite motion in degrees
     * @param speed speed of sprite motion
     */
    public static void shoot(World world, MobileSprite sprite, double angle, double speed) {
        sprite.setVx(speed * Math.cos(Math.toRadians(angle)));
        sprite.setVy(speed * Math.sin(Math.toRadians(angle)));
        if(!world.hasSprite(sprite)) {
            world.addSprite(sprite);
        }
    }
}
