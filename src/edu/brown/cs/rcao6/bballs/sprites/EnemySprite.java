package edu.brown.cs.rcao6.bballs.sprites;

import edu.brown.cs.rcao6.bballs.worlds.World;

public class EnemySprite extends Sprite {
    private int hp;  // hit/health points of EnemySprite
    private final int maxHp;

    public EnemySprite(double x, double y, int width, int height, String image, int hp) {
        super(x, y, width, height, image);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * Returns the hit/health points of EnemySprite.
     * @return hit/health points of EnemySprite
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the hit/health points of EnemySprite.
     * @param hp new hit/health points of EnemySprite
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Returns the max HP of EnemySprite.
     * @return max HP of EnemySprite
     */
    public int getMaxHp() {
        return maxHp;
    }

    public void step(World world) {
        // enemy is no longer alive once hp is less than 0
        if (hp < 0) {
            this.kill();
        }
    }
}
