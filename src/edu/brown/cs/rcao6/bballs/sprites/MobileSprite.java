package edu.brown.cs.rcao6.bballs.sprites;

import edu.brown.cs.rcao6.bballs.worlds.World;

/**
 * Class for a kind of Sprite that keep track of x and y velocities.
 */
public class MobileSprite extends Sprite {
    private double vx;  // velocity along x axis
    private double vy;  // velocity along y axis

    /**
     * Constructs a MobileSprite given a specific x and y velocities.
     * @param x x coordinate of MobileSprite
     * @param y y coordinate of MobileSprite
     * @param width width of MobileSprite
     * @param height height of MobileSprite
     * @param image image path of MobileSprite
     * @param vx x velocity of MobileSprite
     * @param vy y velocity of MobileSprite
     */
    public MobileSprite(World world, double x, double y, int width, int height, String image, double vx, double vy) {
        super(world, x, y, width, height, image);
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Constructs a stationary MobileSprite.
     * @param x x coordinate of MobileSprite
     * @param y y coordinate of MobileSprite
     * @param width width of MobileSprite
     * @param height height of MobileSprite
     * @param image image path of MobileSprite
     */
    public MobileSprite(World world, double x, double y, int width, int height, String image) {
        super(world, x, y, width, height, image);
        this.vx = 0;
        this.vy = 0;
    }

    /**
     * Returns the x velocity of MobileSprite.
     * @return x velocity of MobileSprite
     */
    public double getVx() {
        return vx;
    }

    /**
     * Sets the x velocity of MobileSprite.
     * @param vx new x velocity of MobileSprite
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Returns the y velocity of MobileSprite.
     * @return y velocity of MobileSprite
     */
    public double getVy() {
        return vy;
    }

    /**
     * Sets the y velocity of MobileSprite.
     * @param vy new y velocity of MobileSprite
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    @Override
    public void step() {
        World w = getWorld();

        // move the sprite
        setX(getX() + vx);
        setY(getY() + vy);

        // reverse x velocity if sprite will travel past left or right edges
        if (getX() < 0 || getX() > (w.getWidth() - super.getWidth())) {
            vx = vx * -1;
        }

        // reverse y velocity if sprite will travel past top or bottom edges
        if (getY() < 0 || getY() > (w.getHeight() - super.getHeight())) {
            vy = vy * -1;
        }
    }

    /**
     * Checks if a given MobileSprite is equivalent to this instance of MobileSprite.
     * @param s another MobileSprite
     * @return true if and only if the other MobileSprite has equal field values to this instance
     */
    public boolean equals(MobileSprite s) {
        return super.equals(s) && vx == s.getVx() && vy == s.getVy();
    }
}
