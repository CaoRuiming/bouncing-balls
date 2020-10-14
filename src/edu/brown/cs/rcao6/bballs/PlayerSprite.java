package edu.brown.cs.rcao6.bballs;

import edu.brown.cs.rcao6.bballs.Sprite;
import edu.brown.cs.rcao6.bballs.World;

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
    public PlayerSprite(double x, double y, int width, int height, String image) {
        super(x, y, width, height, image);
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

    @Override
    public void step(World world) {
        int time = world.getTime();
        setLocation(world.getMouseLocX(), world.getMouseLocY());
        setX(centerX - getWidth());
        setY(centerY - getHeight());
        String bullet = Consts.playerBulletSprite;
        if (time % 15 == 0) {
            Sprite.shoot(world, new PlayerBulletSprite(getX(), getY()-5, 10, 10, bullet), 270, 2);
            Sprite.shoot(world, new PlayerBulletSprite(getX()+2, getY()-5, 10, 10, bullet), 280, 2);
            Sprite.shoot(world, new PlayerBulletSprite(getX()+4, getY()-5, 10, 10, bullet), 265, 2);
            Sprite.shoot(world, new PlayerBulletSprite(getX()-2, getY()-5, 10, 10, bullet), 260, 2);
            Sprite.shoot(world, new PlayerBulletSprite(getX()-4, getY()-5, 10, 10, bullet), 275, 2);
        }

        if (time % 3 == 0) {
//            System.out.println("before: " + world.getSprites().size());
            Sprite.shoot(world, new PlayerBulletSprite(getX()-10, getY(), 10, 10, bullet), time * 5 - 90, 2);
            Sprite.shoot(world, new PlayerBulletSprite(getX()+5, getY(), 10, 10, bullet), -time * 5 - 90, 2);
//            System.out.println("after: " + world.getSprites().size());
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
     * Sprites that are fired from PlayerSprite and deal damage to EnemySprite.
     */
    public class PlayerBulletSprite extends MobileSprite {
        private int damage;

        public PlayerBulletSprite(double x, double y, int width, int height, String image) {
            super(x, y, width, height, image);
            damage = Consts.playerBulletDamage;
        }

        @Override
        public void step(World world) {
            super.step(world);
            EnemySprite enemy = world.getEnemy();
            if (enemy != null && this.overlaps(enemy)) {
                enemy.setHp(enemy.getHp() - damage);
                System.out.println("hp: " + enemy.getHp());
                this.kill();
            }
        }
    }
}