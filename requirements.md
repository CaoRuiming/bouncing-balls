# Bouncing Balls Requirements

- The program will be easility launchable through a desktop shortcut or executable file.
- The program will display a non-negative number of bouncing 2-dimensional balls (circles) of uniform size in a window during play.
  - Other shapes/sprites may also be present.
- The 2D balls may bounce off the sides of the window and/or obey laws of physics, depending on the selected stage/level.
- The color, number, position, and velocity of the balls are dependent on specific patterns that vary depending on the selected stage.
- When a ball bounces, its new velocity will be determined from its old velocity and position of the object that it collided with in a manner somewhat consistent with the laws of physics (perhaps simplified to exclude factors like friction).
- The program will take the form of a game of the ["bullet hell"](https://en.wikipedia.org/wiki/Shoot_%27em_up#Bullet_hell) genre.
- The program will present multiple playable stages/levels for the user to choose to play.
- The user can interact with the program by using their cursor to control the position of a player-controlled circular sprite on-screen.
  - If the center of this player-controlled sprite touches a 2D ball on-screen, a game over state will result.
  - User can easily choose a new stage/level to play if they reach a game over state.
- (More of a soft requirement) Should be relatively light on system resources.
