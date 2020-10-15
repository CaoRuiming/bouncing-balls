package edu.brown.cs.rcao6.bballs.worlds;

import edu.brown.cs.rcao6.bballs.Consts;
import edu.brown.cs.rcao6.bballs.Display;
import edu.brown.cs.rcao6.bballs.sprites.EnemySprite;
import edu.brown.cs.rcao6.bballs.sprites.MobileSprite;
import edu.brown.cs.rcao6.bballs.sprites.PlayerSprite;
import edu.brown.cs.rcao6.bballs.sprites.Sprite;

/**
 * World that manages state for stage one.
 */
public class StageOneWorld extends World {
    private int angle1;
    private int angle2;

    /**
     * Constructs and instance of StageOneWorld.
     * @param width width of World
     * @param height height of World
     */
    public StageOneWorld(int width, int height) {
        super(width, height);
        EnemySprite enemy = new EnemySprite((width / 2.0) - (Consts.enemySize / 2.0), Consts.enemyYCoordinate,
                Consts.enemySize, Consts.enemySize, Consts.stageOneImage, Consts.stageOneHp);
        setEnemy(enemy);
        addSprite(enemy);

        angle1 = 0;
        angle2 = 0;
    }

    @Override
    public void step() {
        int time = getTime();
        PlayerSprite player = getPlayer();
        EnemySprite enemy = getEnemy();

        // handle Stage phases
        double hpPercentage = ((enemy.getHp() + 0.0) / enemy.getMaxHp()) * 100;
        if (hpPercentage > 60)
            spawnBullets(1);
        else if (hpPercentage > 10)
            spawnBullets(2);
        else if (hpPercentage > 0)
            spawnBullets(3);

        // handle player win or loss
        if (!player.isAlive()) {
            World world = new World(getWidth(),getHeight());
            world.setTime(-200); // displays lose screen
            Display worldDisplay = new Display(getWidth(), getHeight(), world);
            world.setDisplay(worldDisplay);
            getDisplay().close();
            worldDisplay.run();
        }
        else if (!enemy.isAlive() && time > 0) {
            setTime(-400);
        }

        if (time == -200) {
            addSprite(Consts.winTextSprite);
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

    private void spawnBullets(int phaseNumber) {
        int time = getTime();
        int spin;
        double speed;
        int period;

        if (phaseNumber == 1) {
            spin = 11;
            speed = 0.5;
            period = 19;
        } else if (phaseNumber == 2) {
            spin = 11;
            speed = 2;
            period = 21;
        } else {
            spin = 223;
            speed = 3;
            period = 11;
        }

        if (time % period == 0) {
            double x = getEnemy().getX();
            double y = getEnemy().getY();
            angle1 += spin;
            angle2 -= spin;
            String ball1 = Consts.circleImage1;
            String ball2 = Consts.circleImage2;
            Sprite.shoot(this, new BulletSprite(x,y,10,10,ball1), angle1, speed);
            Sprite.shoot(this, new BulletSprite(x,y,10,10,ball2), angle1 + 180, speed);
            Sprite.shoot(this, new BulletSprite(x+45,y,10,10,ball1), angle2, speed);
            Sprite.shoot(this, new BulletSprite(x+45,y,10,10,ball2), angle2 + 180, speed);
            Sprite.shoot(this, new BulletSprite(x,y+45,10,10,ball1), angle2, speed);
            Sprite.shoot(this, new BulletSprite(x,y+45,10,10,ball2), angle2 + 180, speed);
            Sprite.shoot(this, new BulletSprite(x+45,y+45,10,10,ball1), angle1, speed);
            Sprite.shoot(this, new BulletSprite(x+45,y+45,10,10,ball2), angle1 + 180, speed);
        }
    }

    /**
     * A Sprite that falls down according to gravity and kills PlayerSprite on impact.
     */
    private static class BulletSprite extends MobileSprite {
        private final double gravity;

        public BulletSprite(double x, double y, int width, int height, String image) {
            super(x, y, width, height, image);
            gravity = 0.05;
        }

        public void step(World world) {
            super.step(world);
            setVy(getVy() + gravity);

            // kill PlayerSprite if this Sprite is touching PlayerSprite
            if (this.overlaps(world.getPlayer()) && !world.getPlayer().isInvincible()) {
                world.getPlayer().kill();
            }

            // kill Sprite if it reaches left or right edge of window
            if ((getX() < 0) || (getX() > world.getWidth() - getWidth())) {
                kill();
            }
        }
    }
}
