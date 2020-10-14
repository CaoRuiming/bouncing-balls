package edu.brown.cs.rcao6.bballs.worlds;

import edu.brown.cs.rcao6.bballs.Consts;
import edu.brown.cs.rcao6.bballs.Display;
import edu.brown.cs.rcao6.bballs.sprites.EnemySprite;
import edu.brown.cs.rcao6.bballs.sprites.MobileSprite;
import edu.brown.cs.rcao6.bballs.sprites.PlayerSprite;
import edu.brown.cs.rcao6.bballs.sprites.Sprite;

/**
 * World that manages state for stage two.
 */
public class StageTwoWorld extends World {
    private int angle;

    /**
     * Constructs and instance of StageTwoWorld.
     * @param width width of World
     * @param height height of World
     */
    public StageTwoWorld(int width, int height) {
        super(width, height);
        EnemySprite enemy = new EnemySprite((width / 2) - (Consts.enemySize / 2), Consts.enemyYCoordinate,
                Consts.enemySize, Consts.enemySize, Consts.stageOneImage, Consts.stageTwoHp);
        setEnemy(enemy);
        addSprite(enemy);

        angle = 0;
    }

    @Override
    public void step() {
        int time = getTime();
        PlayerSprite player = getPlayer();
        EnemySprite enemy = getEnemy();

        // handle Stage phases
        double hpPercentage = ((enemy.getHp() + 0.0) / enemy.getMaxHp()) * 100;
        if (hpPercentage > 0)
            spawnBullets();

        // handle player win or loss
        if (!player.isAlive()) {
            World world = new World(getWidth(),getHeight());
            Display worldDisplay = new Display(getWidth(), getHeight(), world);
            world.setDisplay(worldDisplay);
            getDisplay().close();
            worldDisplay.run();
        }
        else if (!enemy.isAlive()) {
            setTime(-400);;
        }

        if (time == -200) {
            addSprite(new Sprite(200, 0, 716, 145, "WinText.png"));
            player.setInvincibility(true);
        }
        else if (time > -100 && time < 0) {
            player.setInvincibility(false);
            World world = new World(getWidth(),getHeight());
            Display worldDisplay = new Display(getWidth(), getHeight(), world);
            world.setDisplay(worldDisplay);
            getDisplay().close();
            worldDisplay.run();
        }

        stepAllSpritesAndIncrementTime();
    }

    public void spawnBullets() {
        int time = getTime();
        int spin = 11;
        double speed = 2;
        int period = 7;
        if (time % period == 0) {
            angle += spin;
            double x = getEnemy().getX();
            double y = getEnemy().getY();
            double enemySize = getEnemy().getWidth();
            MobileSprite bullet = new BulletSprite(
                    x + 0.5*enemySize - 5,y + 0.5*enemySize - 5,10,10,Consts.circleImage1);
            Sprite.shoot(this, bullet, angle, speed);
        }
    }

    /**
     * A Sprite that falls down according to gravity and kills PlayerSprite on impact.
     */
    class BulletSprite extends MobileSprite {
        public BulletSprite(double x, double y, int width, int height, String image) {
            super(x, y, width, height, image);
        }

        @Override
        public void step(World world) {
            super.step(world);

            // kill PlayerSprite if this Sprite is touching PlayerSprite
            if (this.overlaps(world.getPlayer()) && !world.getPlayer().isInvincible()) {
                world.getPlayer().kill();
            }
        }
    }
}

