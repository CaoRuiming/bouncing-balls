package edu.brown.cs.rcao6.bballs.sprites;

import edu.brown.cs.rcao6.bballs.Consts;
import edu.brown.cs.rcao6.bballs.worlds.World;

/**
 * User-controlled Sprite. Instances of this class will follow the location of the mouse cursor.
 */
public class PlayerSprite extends Sprite {
    private double centerX;
    private double centerY;
    private boolean invincible;

    /**
     * Constructs a PlayerSprite instance. Represents the Sprite that is user-controllable.
     * @param x x coordinate of PlayerSprite
     * @param y y coordinate of PlayerSprite
     * @param width width of PlayerSprite
     * @param height height of PlayerSprite
     * @param image image path of PlayerSprite
     */
    public PlayerSprite(World world, double x, double y, int width, int height, String image) {
        super(world, x, y, width, height, image);
        centerX = x + (0.5 * width);
        centerY = y + (0.5 * height);
        invincible = false;
    }

    /**
     * Specialized function to set the x and y coordinates to account for PlayerSprite center.
     * @param x new x coordinate
     * @param y new y coordinate
     */
    private void setLocation(double x, double y) {
        centerX = x + (0.5 * getWidth());
        centerY = y + (0.5 * getHeight());
    }

    /**
     * Creates a Sprite that outlines and follows PlayerSprite. Helps with PlayerSprite visibility on screen.
     * @return a Sprite that is larger than PlayerSprite and follows it around
     */
    public Sprite createAura() {
        return new PlayerAuraSprite(
                getWorld(),0, 0, Consts.playerAuraSize, Consts.playerAuraSize, Consts.playerAuraImage);
    }

    @Override
    public void step() {
        World w = getWorld();
        int time = w.getTime();
        setLocation(w.getMouseLocX(), w.getMouseLocY());
        setX(centerX - getWidth());
        setY(centerY - getHeight());
        String bullet = Consts.playerBulletImage;
        if (time % 15 == 0) {
            Sprite.shoot(new PlayerBulletSprite(w, getX(), getY()-5, 10, 10, bullet), 270, 2);
            Sprite.shoot(new PlayerBulletSprite(w, getX()+2, getY()-5, 10, 10, bullet), 280, 2);
            Sprite.shoot(new PlayerBulletSprite(w, getX()+4, getY()-5, 10, 10, bullet), 265, 2);
            Sprite.shoot(new PlayerBulletSprite(w, getX()-2, getY()-5, 10, 10, bullet), 260, 2);
            Sprite.shoot(new PlayerBulletSprite(w, getX()-4, getY()-5, 10, 10, bullet), 275, 2);
        }

        if (time % 3 == 0) {
            Sprite.shoot(new PlayerBulletSprite(w, getX()-10, getY(), 10, 10, bullet), time * 5 - 90, 2);
            Sprite.shoot(new PlayerBulletSprite(w, getX()+5, getY(), 10, 10, bullet), -time * 5 - 90, 2);
        }
    }

    /**
     * Returns invincibility state of PlayerSprite.
     * @return invincibility of PlayerSprite
     */
    public boolean isInvincible(){
        return invincible;
    }

    /**
     * Sets the invincibility of PlayerSprite. If set to true, PlayerSprite cannot be killed.
     * @param invincibility true if and only if PlayerSprite should be invincible
     */
    public void setInvincibility(boolean invincibility){
        invincible = invincibility;
    }

    /**
     * A sprite that always follows PlayerSprite. Helps with visibility of PlayerSprite, especially in peripheral
     * vision.
     */
    private static class PlayerAuraSprite extends Sprite {
        public PlayerAuraSprite(World world, double x, double y, int width, int height, String image) {
            super(world, x, y, width, height, image);
        }

        @Override
        public void step() {
            // center self on mouse location, which also happens to center this Sprite on PlayerSprite
            World w = getWorld();
            setX(w.getMouseLocX() - (getWidth() / 2.0));
            setY(w.getMouseLocY() - (getHeight() / 2.0));
        }
    }

    /**
     * Sprites that are fired from PlayerSprite and deal damage to EnemySprite.
     */
    private static class PlayerBulletSprite extends MobileSprite {
        private final int damage;

        public PlayerBulletSprite(World world, double x, double y, int width, int height, String image) {
            super(world, x, y, width, height, image);
            damage = Consts.playerBulletDamage;
        }

        @Override
        public void step() {
            super.step();
            World w = getWorld();
            EnemySprite enemy = w.getEnemy();

            // deal damage to EnemySprite and despawn
            if (enemy != null && this.overlaps(enemy)) {
                enemy.setHp(enemy.getHp() - damage);
                this.kill();
            }

            // despawn if touching the left or right edges of world
            if (getX() < 0 || getX() > (w.getWidth() - getWidth())) {
                this.kill();
            }

            // despawn if touching the top or bottom edges of world
            if (getY() < 0 || getY() > (w.getHeight() - getHeight())) {
                this.kill();
            }
        }
    }
}
