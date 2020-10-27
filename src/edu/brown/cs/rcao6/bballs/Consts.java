package edu.brown.cs.rcao6.bballs;

import edu.brown.cs.rcao6.bballs.sprites.Sprite;

/**
 * Utility class that contains various constants.
 */
public class Consts {
    public final static boolean enableMultithreading = true;
    public final static int numThreads = 10;

    public final static Sprite stageSelectTextSprite =
            new Sprite(null,30, 30, 990, 115, "assets/selectionText.png");
    public final static Sprite winTextSprite =
            new Sprite(null, 30, 30, 716, 145, "assets/winText.png");
    public final static Sprite loseTextSprite =
            new Sprite(null, 30, 30, 569, 111, "assets/loseText.png");

    public final static String playerImage = "assets/redSquare.png";
    public final static int playerWidth = 5;
    public final static int playerHeight = 5;
    public final static String playerBulletImage = "assets/yellowTriangle.png";
    public final static int playerBulletDamage = 4;
    public final static String playerAuraImage = "assets/greenCircle.png";
    public final static int playerAuraSize = 50;

    public final static int stageImageSize = 100;

    public final static int enemyYCoordinate = 30;
    public final static int enemySize = 50;

    public final static String stageOneImage = "assets/redSquare.png";
    public final static int stageOneHp = 200;

    public final static String stageTwoImage = "assets/cyanSquare.png";
    public final static int stageTwoHp = 1800;

    public final static String circleImage1 = "assets/blueCircle.png";
    public final static String circleImage2 = "assets/purpleCircle.png";
}
