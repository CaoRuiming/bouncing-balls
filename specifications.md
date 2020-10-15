# Bouncing Balls Specifications

- Launch
  - The program can be launched using one of two methods:
    - Desktop shortcut that launches the program on double click.
    - Executable file that launches the program on double click (e.g. jar file).
- Initialization
  - The program will open a new window that will not exceed the size of the user's display.
    - It could be scaled to 75% of max height and 75% of max width.
  - The first displayed screen of the program will be a stage/level select. The user can click on a sprite on this screen to choose the corresponding stage/level.
  - A circular user-controlled sprite will appear on the screen where the user's cursor is located.
- While Running
  - Once a stage/level is chosen, an "enemy" sprite will appear at the top of the screen and start spawning 2D balls/sprites in various patterns.
  - If the center of the user-controlled sprite intersects with an enemy-spawned sprite, the user loses, and the program displays a game over screen. Then the program will show the stage/level selection screen.
  - The player sprite will spawn its own sprites in a set pattern that will "damage" the enemy sprite.
  - Once enough player-spawned sprites touch the enemy sprite, the user wins, and the program shows a victory screen. Then the program will show the stage/level selection screen.
- Interaction
  - The user-controlled player sprite will center itself on the user's cursor position at all times.
  - On the stage/level select screen, the player can click on the sprites on screen that correspond to stages/levels to select the stage/level that they wish to play.
- Closing/Termination
  - The program can be terminated by clicking the close button (X) on the program window.
- Non-functional/Other
  - The program will have low resource usage.
    - Not sure how best to measure this yet, we can clarify/update this in the future.
    - Should maybe use less than 5% of CPU of target system.


