## Improvements
- **Rename controller and classes**: Rename Main to BattlePlane. Controller to Control start. Remov Destructible interface and move to ActiveActor.

## Bug Fixes
- **Prevented Repeated Level Loading**: Fixed a bug in the Control_Start and Controller classes where levels were being reloaded unnecessarily. Now, the goToLevel method checks if the current level matches the requested level to avoid redundant loading. This reduces potential lag and improves performance.

## Implemented Features

### Controls and Animations

- **Plane Animation**: Uses a `Timeline` to control the continuous movement of the player's plane.
- **Bullet Animation**: Bullets are fired by both the player and enemies; they move at a set speed and disappear upon reaching the screen boundary.
- **Enemy and Boss Animations**: The enemy and boss characters follow pre-defined paths using `PathTransition`, with the boss having a more complex movement pattern.

### Sound Effects and Background Music

- **Button Hover Sound**: Each button plays a sound effect (`btnhover.wav`) on hover.
- **Background Music**: Background music (`bg.mp3`) loops on the main screen and during gameplay.

### Settings Screen

- **Volume Control**: The settings screen allows adjusting the background music volume via a slider.
- **Close Settings Screen**: Returns to the main screen and removes the blur effect.

### Scene Switching

- **Return to Main Screen and Scene Switching**: The game supports switching between the main and game screens, automatically pausing or restarting animations as needed.

### ActiveActor

- **Common features**: Add width to control image size more flexible. Add hitbox to make the hit range more reasonable.
- **User Plane**: Add left and right to make plane move horizontally.
- **User Projectile**:
- **Boss**:
- **Boss Projectile**:
- **Enemy**:
- **Enemy Projectile**:

## Issues Encountered

- **Sound Playback Delay**: `AudioClip` is used to minimize delay in sound effects.
- **Path Movement Lag**: Adjusted `PathTransition` duration to optimize smooth movement for enemy and boss characters.

## Project Structure

- **BattlePlane**: The main entry point of the game application, initializing the main screen and setting up the stage.
- **Control_Start**: Manages game level transitions and prevents redundant loading of levels by checking the current active level.
- **Control_Animation**: Manages animations for all elements (plane, bullets, enemies, and boss).
- **Control_Main**: Controls the main screen, including background music and button actions.
- **Control_Setting**: Manages the settings screen, including volume adjustment and close functionality.

## Changelog

- **2024-11-06**: Completed animations for plane, bullets, enemies, and boss. Added sound effects and background music control.
- **2024-11-07**: Fixed bug preventing repeated level loading. Make the plane can move horizontally. Add hitbox to make hit range more reasonable.