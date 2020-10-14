package edu.brown.cs.rcao6.bballs;

/**
 * Location Class that stores x and y coordinates in a World.
 */
public class Location {
    private double x;
    private double y;

    /**
     * Constructs a new Location object.
     * @param x x coordinate of Location
     * @param y y coordinate of Location
     */
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of Location,
     * @return x coordinate of Location
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x coordinate of Location.
     * @param x new x coordinate of Location
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of Location.
     * @return y coordinate of Location
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y coordinate of Location.
     * @param y new y coordinate of Location
     */
    public void setY(double y) {
        this.y = y;
    }
}
