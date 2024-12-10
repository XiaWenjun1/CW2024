# COMP2042 Coursework

## Table of Contents
- [GitHub Repository](#github-repository)
- [Compilation Instructions](#compilation-instructions)
- [Implemented and Working Properly](#implemented-and-working-properly)
- [Implemented but Not Working Properly](#implemented-but-not-working-properly)
- [Features Not Implemented](#features-not-implemented)
- [New Java Classes](#new-java-classes)
- [Modified Java Classes](#modified-java-classes)
- [Unexpected Problems](#unexpected-problems)

## GitHub Repository
- GitHub Link: [Repository Link](https://github.com/XiaWenjun1/CW2024)

## Compilation Instructions

### Prerequisites
1. Clone the repository:
    ```bash
    git clone https://github.com/XiaWenjun1/CW2024.git
    ```
2. Open the cloned repository in IntelliJ IDEA.
3. **Java Development Kit (JDK)**: Version **19.0.2**.
4. **JavaFX SDK**: Set SDK to 19. (**Ctrl + Alt + Shift + S**)
5. If Maven not working properly, **Right Click** `pom.xml`. Select `Maven` > `Reimport`.

### Running
1. Using terminal:
    ```
    mvn javafx:run
    ```
2. Or using `Main` class file. In the `src` > `main` > `java` > `com.example.demo` package > `Controller`.

## Implemented and Working Properly

### 1. Power-Ups and Upgrades
- **Weapon Power-Ups**: Collect ammo box which move straight left (Spawn Randomly) will upgrade (Max level 5, default 1). Increase fire rate, bullet speed, bullet image size, and bullet hit box.
- **Health Items**: Collect hearts which the movement trajectory is like sin(x) (Spawn Randomly) increase user health by 1. (No Maximum)

### 2. Player Plane Controls
- **Movement**:  
  **Motion 1**: Player can move up, down, left, and right using default arrow keys.  
  **Motion 2**: Press and hold the left mouse button to drag the plane.
- **Fire**: Optimized the situation where bullets cannot be fired when using keyboard control. (Can hold space bar and move plane at same time)

### 3. Sounds and Animations
- **Sounds**: Background music, button hover sound (in main page), explosion sound, heal sound (get object heart), upgrade sound (get object ammo box), win game sound, lose game sound, boss actives shield sound, user shoot sound, entering level sound, leaving level sound, and user got damage sound.
- **Animations**: Explosion animations for all fighter plane, animations for entering and leaving a level for the player (Spiral).

### 4. Boundaries for Cleanup to Save Memory
- **Clean**: Left boundary check collision with enemy projectiles, heart, and ammo box. When they collision with left boundary, remove them from root.  
  Right boundary check collision with user projectile. When right boundary collision with user projectile, remove them from root.

### 5. Cleanup Complete Level to Save Memory
- **Clean**: When user go to next level, stopping timeline and cleaning up all children of the root of the scene to save memory.

### 6. The User is Injured and Flashes Invincibility
- **Invincibility**: When user health decreases by 1, user flashes and becomes invincible for two seconds.

### 7. Enemies and Bosses (Fire Patterns, Health Bar, and Shield)
- **Enemies**: Add two new enemies with new images.  
  Speed enemy: Move very fast but cannot fire bullet.  
  Heavy enemy: Move very slow but health is 5 and can fire bullet (laser image).
- **Bosses**: Add three mutation bosses each with different images, fire patterns, and health.  
  Mutation boss1: 2 fire patterns. One straight fireball. Two straight purple venom bullets.  
  Mutation boss2: 3 fire patterns. One straight fireball. Two straight purple venom bullets. Three straight blue venom bullets.  
  Mutation boss3: 4 fire patterns. One straight fireball. Two straight purple venom bullets. Three straight blue venom bullets.  
  Three different directions (Upper left, Lower left, and Straight) red venom bullets.
- **Bosses Health Bar**: It will track boss position using `getLayoutX() + getTranslateX()` and `getLayoutY() + getTranslateY()`.  
  Displays a dynamic health bar for each boss. Updates in real-time based on the boss's current health. Can be shown or hidden as needed.
- **Bosses Shield**: Fix the shield image format from `.jpg` to `.png`. Tracked the boss positions using `getLayoutX() + getTranslateX()` and `getLayoutY() + getTranslateY()`.

### 8. Score Board and Target Hint
- **Score Board**: It will show on left bottom in the game. It has two modes.  
  In normal levels: **Kills: currentKills / targetKills**.  
  And currentKills will update if user kills enemies. TargetKills will update in different levels automatically.  
  In endless level: **Kills: currentKills**.
- **Target Hint**: It will show on boss level on left bottom in the game. **Target: Defeat the Boss!**

### 9. Main Menu
- **Animation Background**: The `Control_Animation` class manages all visual interactions and animations in the main menu, including:
   - **Plane Animation**: Moves the user's plane smoothly across the screen. Resets position after reaching the edge of the screen.
   - **Bullet Management**: Handles firing and movement of user, enemy, and boss bullets. Bullets are removed when off-screen.
   - **Enemy and Boss Movement**: Uses pre-defined paths (Arc) for enemy and boss animations. Implements smooth transitions with `PathTransition`.
   - **Initialization and Resource Management**: Loads images and sets element sizes for the game objects. Frees resources when no longer needed.
- **Tutorial**: The `Control_Control` class manages the information about how to play the game, including images and texts.  
  The control panel can be opened through the button *CONTROL* in the main menu. When you open it, it will make the background blur.  
  To close the panel, click the *CLOSE* button located in the top-right corner, and it will remove the blur effect.
- **Setting**: The `Control_Setting` class manages background music and game sounds. The setting panel can be opened through the button *SETTINGS* in the main menu.  
  When you open it, it will make the background blur. To close the panel, click the *CLOSE* button located in the top-right corner, and it will remove the blur effect.  
  There are many toggles: background music, explosion, shoot, get object, user damage, shield, and interaction (win, lose, and teleport).  
  Users can control all of them by toggling the checkbox to enable or mute.
- **Start Normal Level**: Click *START GAME* to start the normal level.
- **Start Endless Level**: Click *START ENDLESS LEVEL* to start the endless level.

### 10. Pause Menu
This module manages the pause menu functionality, allowing players to pause the game, resume it, or return to the main menu.  
Below is a detailed explanation of the associated classes and features:
- **Control_PauseMenu**: The `Control_PauseMenu` class is a controller that manages the pause menu UI and user interactions.
   - **UI Components**: Pause menu pane, continue button, and return to main button.
   1. **Show/Hide Pause Menu**: Toggles the visibility of the pause menu and controls.
   2. **Resume Game**: Resumes gameplay and removes the blur effect.
   3. **Return to Main Menu**: Cleans up current level resources and navigates back to the main menu.
- **PauseMenuManager**: The `PauseMenuManager` class handles pause menu logic and game state control.
   1. **Pause/Resume Game**: Toggles the game's pause state by managing the timeline and inputs.
   2. **Blur Effect**: Applies or removes a Gaussian blur effect for a polished visual experience.
   3. **Cleanup**: Releases resources when switching levels or exiting.

### 11. End Game Menu
This module manages the end-of-game scenarios, including displaying a win/loss result and navigating to the end game menu.
- **Control_EndGameMenu**: Handles the actions on the end game menu, including returning to the main menu or exiting the game.
   1. **Return to Main**: Navigates back to the main menu and cleans up the current level.
   2. **Exit Game**: Exits the application.
- **EndGameMenuManager**: Handles the logic for win and loss scenarios, ensuring the game only transitions once, avoiding redundant operations or UI conflicts.
   1. **Win Game**: Displays a win image and transitions to the end game menu after a 2-second delay.
   2. **Lose Game**: Displays a game over image and transitions to the end game menu after a 2-second delay.
   3. **Game Over Prevention**: Ensures end game logic runs only once.
   4. **Stage Management**: Retrieves and closes the current stage, transitioning to the end game menu.

### 12. Levels
- **Level 1**: Add Score Board.  
  Next level condition: Kill 10 enemies.
- **Level 2**: Add Boss Health Bar and Target hint.  
  Dynamic health bar for each boss. Updates in real-time based on the boss's current health.  
  Next level condition: Defeat boss.
- **Level 3**: Boss and Enemy plane occur at the same time, including Score Board and Target hint.  
  After 30 kills of enemy planes, no more enemies spawn.  
  Next level condition: Kill 30 enemies and defeat the boss. Must meet both conditions.
- **Level 4**: Mutation Boss with 3 stages. Each with different mutation boss images and bullet images.  
  Different fire patterns.  
  Win condition: Defeat mutation boss.
- **Level Endless**: No win condition. Every 10 kills increase 1 enemy. Very relaxing level.

## Implemented but Not Working Properly
- **All objects are generated at layout x:0 and layout y:0**: Enemy plane, enemy projectiles, boss, and boss projectiles initialize in the upper-left corner.  
  As a result, when the user moves to the top-left corner, their health decreases.  
  **Current Status To Address**: I set a top boundary to avoid the user going to the top-left corner. I still don't know the exact reason.  
  This issue may be related to the hitbox rectangle I added.

## Features Not Implemented
1. **Add Two-Players Mode**: Add a mode allowing two players to play together.
2. **Enemy and Boss Movement Pattern**: Initially, I added horizontal movement to the boss by modifying its movement method.  
   However, I found that the boss's movement became too erratic, making it challenging to hit. As a result, I removed this feature.
3. **Slide Bar to Control Volume**: I attempted to add a slider for volume control.  
   However, I couldn't retain the adjusted volume when switching back from the main menu to the settings.

## New Java Classes
### 1. AmmoBox (in **com.example.demo.Actor.Object** package)
The `AmmoBox` class represents an ammo box object in the game, which moves horizontally across the screen.  
It can be collected by the player to provide power-ups for weapons.  
The class defines properties like image size, horizontal velocity, and spawn probability, along with methods for updating its position.

### 2. Heart (in **com.example.demo.Actor.Object** package)
The `Heart` class represents a collectible heart object that moves horizontally while oscillating vertically in a sinusoidal pattern.  
The heart can be collected by the player to gain one health.  
It defines properties such as image size, horizontal velocity, spawn probability, and vertical oscillation amplitude.  
Methods include position updates.

### 3. BossFirePattern (in **com.example.demo.Actor.Plane.Boss** package)
The `BossFirePattern` class controls the firing patterns of the boss in the game.  
It manages the creation of projectiles based on different attack types, including straight shots, scatter shots, and directional shots.  
The class retrieves the current position of the boss and generates a list of projectiles accordingly.  
Methods include random attack selection and projectile creation with varying velocities and trajectories.

### 4. HeavyEnemy (in **com.example.demo.Actor.Plane** package)
The `HeavyEnemy` class represents a slow-moving, high-health enemy plane in the game.  
This class extends the `FighterPlane` and defines a heavy enemy with 5 health points.  
It fires projectiles at a low rate, using a random probability determined by its `FIRE_RATE`.  
The projectiles are created with an offset from the enemy’s position.  
This enemy moves slowly across the screen from right to left, and its position is updated every game tick.

### 5. SpeedEnemy (in **com.example.demo.Actor.Plane** package)
The `SpeedEnemy` class represents a fast-moving enemy plane in the game.  
This class extends the `FighterPlane` and defines a lightweight, high-speed enemy with minimal health (1 health point).  
It moves quickly across the screen, from right to left, with a high horizontal velocity.  
Unlike other enemies, the `SpeedEnemy` does not fire projectiles.  
It is designed to be a fragile but swift adversary that poses a unique challenge due to its speed.

### 6. MutationBoss1 (in **com.example.demo.Actor.Plane** package)
The `MutationBoss1` class represents a mutated boss in the game, extending the `Boss` class.  
This class manages the mutated boss's behavior, including firing projectiles in various patterns.  
It utilizes a `BossFirePattern` manager to determine the type of projectiles to fire, such as one straight fireball or two straight purple venom attacks.  
The boss has a customizable fire rate, influencing how often it fires projectiles. The boss's image and size are set upon initialization.

### 7. MutationBoss2 (in **com.example.demo.Actor.Plane** package)
This class is almost identical to `MutationBoss1`, but it uses different images and includes an additional fire pattern (three straight blue venom shots).  
It has three fire patterns.

### 8. MutationBoss3 (in **com.example.demo.Actor.Plane** package)
This class is almost identical to `MutationBoss2`, but it uses different images and includes an additional fire pattern (three scattered red venom shots).  
It has four fire patterns.

### 9. HeavyEnemyProjectile (in **com.example.demo.Actor.Projectile** package)
The `HeavyEnemyProjectile` class represents a projectile fired by a `HeavyEnemy` plane.  
It extends the `Projectile` class and defines the behavior of projectiles shot by heavy enemy planes.  
These projectiles move horizontally across the screen at a fixed speed and have a specific image and size.  
The class includes methods to update the projectile's position and hitbox, ensuring proper movement.

### 10. MutationBossProjectile1 (in **com.example.demo.Actor.Projectile** package)
The `MutationBossProjectile1` class represents a projectile fired by `MutationBoss1`.  
It extends the `BossProjectile` class and is tailored for the specific appearance and behavior of the mutation boss's projectiles.

### 11. MutationBossProjectile2 (in **com.example.demo.Actor.Projectile** package)
The `MutationBossProjectile2` class represents a projectile fired by `MutationBoss2`.  
It extends the `BossProjectile` class and is tailored for the specific appearance and behavior of the mutation boss's projectiles.

### 12. MutationBossProjectile3 (in **com.example.demo.Actor.Projectile** package)
The `MutationBossProjectile3` class represents a projectile fired by `MutationBoss3`.  
It extends the `BossProjectile` class and is tailored for the specific appearance and behavior of the mutation boss's projectiles.

### 13. Boundary (in **com.example.demo.Actor** package)
The `Boundary` class represents the left and right boundary markers in the game. It extends the `Rectangle` class and is used to clean up objects, user and enemy projectiles.  
The class provides methods for creating static left and right boundaries at predefined positions, with a width of 1px (making it invisible) and a height that spans the game area.  
Though the class doesn't currently support dynamic updates, it includes an `updatePosition` method for potential future extensions.

### 14. Control_StartEndLess (in **com.example.demo.Controller** package)
This class is similar to the previous controller, but it serves as the entrance for the endless level in the main menu.

### 15. BossHealthBar (in **com.example.demo.Display** package)
The `BossHealthBar` class is responsible for visually displaying the health of the boss character in the game.  
It extends the `HBox` layout and contains a `ProgressBar` to represent the boss's health as a percentage.  
The health bar is styled with a red color for the active portion and gray for the background.  
The class provides methods for updating the health bar's progress, positioning it on the screen, and showing or hiding it when necessary.

### 16. Explosion (in **com.example.demo.Display** package)
The `Explosion` class handles explosion effects, including visual animations and sound effects.  
It triggers an explosion animation at a specified location when a fighter plane is destroyed.  
The explosion consists of 20 animation frames, each displayed for 50 milliseconds.  
The class also plays an explosion sound effect when the animation starts. After the animation completes, the explosion image is removed from the screen.  
The explosion animation is displayed using `ImageView` objects, with frames loaded from a specific path template (`/com/example/demo/images/Explosion/explosion%d.png`).

### 17. ScoreBoard (in **com.example.demo.Display** package)
The `ScoreBoard` class manages the display of the player's kill count in the game. It has two modes: normal mode and endless mode.  
In normal mode, it shows both the current kills and the target kills required for level progression. In endless mode, it only displays the current number of kills.  
The scoreboard is represented as a horizontal box (`HBox`) with a label displaying the kill information.  
The scoreboard’s position on the screen is fixed, and the kills are updated dynamically as the player progresses.

### 18. TargetLevel (in **com.example.demo.Display** package)
The `TargetLevel` class displays a hint message to guide the player during the game.  
It provides objectives like "Target: Defeat the Boss!". The message is displayed using a `Label` that is styled and positioned on the screen.  
The hint is initially hidden but can be shown using the `show()` method.

### 19. ActiveActorManager (in **com.example.demo.Level.LevelManager** package)
The `ActiveActorManager` class manages all active game actors, including friendly units, enemy units, projectiles, ammo boxes, and hearts.  
It updates their states and provides methods to access actors by category for efficient management during gameplay.

### 20. ActorSpawnerManager (in **com.example.demo.Level.LevelManager** package)
The `ActorSpawnerManager` class is responsible for spawning various game actors, including enemies, projectiles, ammo boxes, and hearts.  
It interacts with the `ActiveActorManager` to update and manage active actors in the game, ensuring proper handling of the player's plane hitbox.

### 21. AudioManager (in **com.example.demo.Level.LevelManager** package)
The `AudioManager` class manages all audio-related functions in the game, including background music and sound effects.  
It provides methods for playing, stopping, and controlling audio settings such as volume and enabling/disabling sound effects.

### 22. CleanDestroyedManager (in **com.example.demo.Level.LevelManager** package)
The `CleanDestroyedManager` class handles cleanup of game actors, such as projectiles, ammo boxes, and hearts, when they are out of bounds or destroyed.  
It interacts with the `ActiveActorManager` to efficiently remove these actors from the scene.

### 23. CollisionManager (in **com.example.demo.Level.LevelManager** package)
The `CollisionManager` class is responsible for detecting and handling collisions between game actors like projectiles, enemy units, the user plane, and collectible items.  
It applies damage to colliding actors and triggers actions such as upgrading projectiles or increasing health when the user plane collides with ammo boxes or hearts.

### 24. EndGameMenuManager (in **com.example.demo.Level.LevelManager** package)
The `EndGameMenuManager` handles the end-game process, displaying win or game-over images and transitioning to the end game menu.  
It ensures the end-game logic is executed only once and manages scene cleanup and the opening of the end game menu.

### 25. PauseMenuManager (in **com.example.demo.Level.LevelManager** package)
The `PauseMenuManager` class manages the game pause functionality, including pausing, resuming, and managing the blur effect on UI elements.  
It controls the pause menu and ensures proper cleanup of game elements when the pause menu is used.

### 26. UserInputManager (in **com.example.demo.Level.LevelManager** package)
The `UserInputManager` class tracks key presses for user movement (up, down, left, right) and shooting projectiles when the spacebar is pressed.  
It allows the user to drag the plane with the mouse, changes the cursor style, and manages the game pause/unpause states (Enter or Middle mouse).  
The class also implements a cooldown mechanism for shooting projectiles and manages input delays.

### 27. LevelViewLevelOne (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to Level Three.

### 28. LevelViewLevelThree (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to Level Three.

### 29. LevelViewLevelFour (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to Level Four.

### 30. LevelViewLevelEndLess (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to the Endless Level.

### 31. LevelThree (in **com.example.demo.Level** package)
Level Three features a boss that summons enemies, total 30 enemies. Specific in implement features part.

### 32. LevelFour (in **com.example.demo.Level** package)
Level Four features a multi-stage boss with changing images and fire patterns. Specific in implement features part.

### 33. LevelEndLess (in **com.example.demo.Level** package)
Level Endless features endless enemies, every 10 kills increase 1 total enemy on the screen. Specific in implement features part.

### 34. Control_Animation (in **com.example.demo.Ui** package)
Controls the animation for the user plane, boss, enemy, and their projectiles in the Main Menu. Specific in implement features part.

### 35. Control_Control (in **com.example.demo.Ui** package)
A tutorial for the user to understand how to play the game. Specific in implement features. Specific in implement features part.

### 36. Control_EndGameMenu (in **com.example.demo.Ui** package)
The UI for the End Game Menu, with logic implemented in `EndGameMenuManager`. Specific in implement features part.

### 37. Control_Main (in **com.example.demo.Ui** package)
The UI for the Main Menu, with 4 buttons (START, START ENDLESSLEVEL, CONTROL, and SETTINGS). Animations are applied here. Specific in implement features part.

### 38. Control_PauseMenu (in **com.example.demo.Ui** package)
The UI for the Pause Menu, with logic implemented in `PauseMenuManager`. Specific in implement features part.

### 39. Control_Setting (in **com.example.demo.Ui** package)
Contains UI and logic for controlling audio settings via checkboxes. Specific in implement features part.

## Modified Java Classes

### 1. ActiveActor
1. Constructor Parameters: Add `imageWidth`. Add `setFitWidth(imageWidth)` to constructor.
2. `setImage(String imageName)`: Add method for updating the image dynamically.

### 2. ActiveActorDestructible
1. Add a Rectangle hitbox for collision detection, with methods to manage its position, size, and visibility (`createAndAddHitbox`, `updateHitbox`, `setHitboxSize`, and `getHitbox`).

### 3. FighterPlane
1. Constructor Parameters: Add `imageWidth`. Add `setFitWidth(imageWidth)` to constructor.
2. `fireProjectiles()`: Return a list of `ActiveActorDestructible` object.
3. `increaseHealth()`: It increments the health by 1.

### 4. Projectile
1. Constructor Parameters: Add `imageWidth`.

### 5. Boss
1. Constructor Parameters: Allows setting the initial health value via a parameter (`initialHealth`). And `setHitboxSize()` used to set hitbox size. And initialize BossFirePattern.
2. `fireProjectiles()`: Dynamically selects the attack type using `firePattern.selectAttackType()` and returns the corresponding projectile by introducing BossFirePattern.
3. `updateActor()`: Add a `updateHitbox()` method and slightly modifies the hit area.
4. `getShielded()`: Add a method to get the shield status.

### 6. BossProjectile
1. Constructor Parameters: Add `initialXPos`. Add `setHitboxSize()` to constructor.
2. `setVelocity()`: Customize projectile horizontal and vertical speed.
3. `updatePosition()`: Add `moveVertically()`.
4. `updateActor()`: Add a `updateHitbox()` method and slightly modifies the hit area.

### 7. EnemyPlane
1. Constructor: `setHitboxSize()` used to set hitbox size.
2. `updatePosition()`: Add a `updateHitbox()` method and slightly modifies the hit area.

### 8. EnemyProjectile
1. Constructor : `setHitboxSize()` used to set hitbox size.
2. `updatePosition()`: Add a `updateHitbox()` method and slightly modifies the hit area.

### 9. UserPlane
1. Invincibility System: A flag `isInvincible` and a `Timeline` object `invincibilityTimer` were added to handle invincibility after taking damage.  
   Methods like `triggerInvincibility()`, `endInvincibility()`, `startFlashing()`, and `stopFlashing()` were added to manage invincibility effects (e.g., flashing the plane).
2. Projectile Upgrades: A method `upgradeProjectile()` was added to upgrade the user's projectile power level.
3. New Movement Handling: Added methods `moveUserPlane(double deltaX, double deltaY)` to move the plane based on mouse movement with boundary checks.
4. Spiral Portal Animation: Methods `spiralPortalEnter()` and `spiralPortalExit()` were added for animations where the plane enters and exits the scene with rotation, scaling, and fading effects.
5. Improved Bounds Checking: A new method `applyBoundsCheck()` was added to handle boundary checks for both horizontal and vertical plane movements.
6. Enhanced Projectile Handling: The `fireProjectiles()` method now returns a list of projectiles with the appropriate size and power level.
7. New Movement Methods: Additional movement methods like `moveLeft()`, `moveRight()`, and stopping movement with `stopHorizontalMovement()`.

### 10. UserProjectile
1. Power Level System: Introduced a `powerLevel` variable (ranging from 1 to 5) to control the size, velocity, hitbox dimensions, and image of the projectile.
2. Projectile Properties: Added a `ProjectileProperties` class to define different properties for each power level, including image size, velocity, and hitbox dimensions.
3. State Management: Implemented the `updateProjectileState()` method to update the projectile's state (velocity, hitbox, image) based on the current power level.
4. ImageView: Added an `ImageView` for displaying the projectile's image, ensuring the aspect ratio is preserved.
5. Setters and Getters: Added methods like `setPowerLevel()` and `getPowerLevel()` to manage and retrieve the projectile’s power level.
6. `updateActor()`: Add a `updateHitbox()` method and slightly modifies the hit area.

### 11. Controller
1. Class Renaming: The class `Controller` has been renamed to `Control_Start`.
2. Level Management: Updated level transition logic to use reflection for dynamic level loading.  
   Introduced `currentLevel` to track and prevent redundant level reloads.
3. Level Transition Handling: Added a listener to `currentLevelName` for automatic level transitions.  
   Refined goToLevel() method to dynamically load and set levels.
4. Error Handling: Added `showError()` method to handle exceptions during level loading with alert messages.

### 12. Main
1. FXML Integration: Integrated FXML for the layout in the new `Main` class.  
   Replaced programmatically created scenes with FXML-based layout (`Main.fxml`).
2. Controller Usage: Removed the direct `Controller` reference from the `start()` method in favor of FXML-based scene management.

### 13. GameOverImage
1. Positioning: Set fixed `X_POSITION` and `Y_POSITION` for the image in the new class.
2. Resizing: Added resizing for the image with fixed `WIDTH` and `HEIGHT`.
3. Simplified Constructor: Removed the parameters (`xPosition`, `yPosition`) from the constructor and used predefined constants for position and size.

### 14. WinImage
1. Positioning: Set fixed `X_POSITION` and `Y_POSITION` for the image in the new class.
2. Resizing: Added resizing for the image with fixed `WIDTH` and `HEIGHT`.
3. Simplified Constructor: Removed the parameters (`xPosition`, `yPosition`) from the constructor and used predefined constants for position and size.
4. Remove `showWinImage()` Method: It will add to root after win the game.

### 15. HeartDisplay
1. Positioning: Defined constants `DISPLAY_X_POSITION` and `DISPLAY_Y_POSITION` for heart container position.
2. Constructor: Simplified constructor to use fixed display positions and only accept `heartsToDisplay` parameter.
3. `addHeart()` Method: Added `addHeart()` method to dynamically add hearts (lives) to the container.
4. Refactored `initializeHearts()`: Modified to use `addHeart()` for initializing hearts, instead of directly creating hearts in a loop.

### 16. ShieldImage
1. Image Path: Corrected image path to `/com/example/demo/images/shield.png`.
2. Constructor: Removed `xPosition` and `yPosition` parameters from the constructor. Moved positioning to `setLayout()` method.
3. Visibility Control: Updated method names to `show()` and `hide()` for clarity.
4. `toFront()` Method: Used toFront() in show() to ensure shield appears on top.

### 17. LevelView
1. Class Renaming: Renamed `heartDisplay` to a new constructor `HeartDisplay(heartsToDisplay)` (removed positions).  
   Updated `winImage` and `gameOverImage` constructors to use default constructors (removed position arguments).
2. Method Addition: Added `addHearts()` method to add hearts when health increases.
3. Method Addition for test: `getHeartDisplay()`, `getWinImage()` and `getGameOverImage()` methods for test.

### 18. LevelViewLevelTwo
1. Constructor Parameter: Add `bossHealth` for health bar.
2. New UI Elements: Added `BossHealthBar` for displaying the boss's health.  
   Added `TargetLevel` for displaying a hint or target information.  
   Added `ShieldImage` for the boss's shield visuals (no constructors).
3. New Features: Methods to update the shield's position (`updateShieldPosition`) and show/hide it (`showShield`, `hideShield`).  
   Methods to update the boss health bar's position (`updateBossHealthPosition`), show/hide it (`showBossHealthBar`, `hideBossHealthBar`), and update health (`updateBossHealth`).
4. Initialization Improvements: Added all UI elements (shield, health bar, hint) to the root and initialized their visibility using `Platform.runLater`.
5. Layout Adjustments: Dynamically positioned shield and health bar based on the boss's location.

### 19. LevelOne
1. Enhancements to Transition Logic: Added `delayToNextLevel` method to include an animation and sound effect before transitioning to the next level with a delay.  
   Added animations for the player's entry (`spiralPortalEnter`) and exit (`spiralPortalExit`) using audio triggers (`triggerTeleportInAudio` and `triggerTeleportOutAudio`).
2. Enhanced Enemy Spawn Logic: Introduced multiple enemy types (`SpeedEnemy`, `HeavyEnemy`) in addition to `EnemyPlane`. Randomized enemy type selection based on probabilities.  
   Check if game is over from `levelParent.isGameOver()`, if true then not spawn enemies.
3. Level View Update: Override `updateLevelView` method to synchronize the UI kill count with the game state by calling `levelView.updateKills`.
4. Modified Instantiation: Changed `instantiateLevelView` to create and use `LevelViewLevelOne`, accommodating additional level-specific UI elements.

### 20. LevelTwo
1. Enhanced Boss Behavior: Added boss-specific logic including health tracking, shield mechanics (`getShielded`), and related updates in `updateLevelView` (Override).  
   Introduced a shield visual/audio toggle (`showShield`, `hideShield`, and `triggerShieldAudio`).
2. Improved Game Transitions: Introduced `delayToNextLevel` with animation and sound effects before transitioning to the next level (`spiralPortalExit`, `triggerTeleportOutAudio`).
3. Visual Enhancements: Updated `LevelViewLevelTwo` to manage boss-specific elements such as health bar (`updateBossHealth`, `hideBossHealthBar`) and shield position.
4. Initialization Updates: Player entry animations and teleport sound effects added (`spiralPortalEnter`, `triggerTeleportInAudio`).
5. Spawn Logic: Check if game is over from `levelParent.isGameOver()`, if true then not spawn boss.
6. Additional Health Management: Incorporated `BOSS_HEALTH` constant to dynamically set the boss's initial health.
7. Method Addition for test: `getBoss()` and `getNextLevel()` methods for test.

### 21. LevelParent
1. Additional Fields:  
   `enemyMinimumYPosition`: Added to set the minimum Y position for enemy units to spawn.  
   `previousNumberOfEnemies`: Tracks the previous number of enemies for kill count calculations.  
   `pauseMenuManager`, `endGameMenuManager`, `userInputManager`, `activeActorManager`, `cleanDestroyedManager`, `actorSpawnerManager`: New manager objects added to handle different aspects like input, pause menu, actor management, cleanup, and spawning.  
   `gameOver`: A boolean to track whether the game is over.  
   `currentLevelName`: A StringProperty for binding the current level's name to the UI.
2. New Constructors & Setup:  
   The constructor initializes the new managers (`userInputManager`, `pauseMenuManager`, `endGameMenuManager`, etc.).
   `activeActorManager.getFriendlyUnits().add(user)` is used to add the user plane to the friendly units in the `activeActorManager`.
3. Deleted Fields:  
   `SCREEN_HEIGHT_ADJUSTMENT`: Removed from the new class, along with the related logic to adjust the enemy spawn position.
   `List<ActiveActorDestructible> friendlyUnits`, `List<ActiveActorDestructible> enemyUnits`, `List<ActiveActorDestructible> userProjectiles` and `List<ActiveActorDestructible> enemyProjectiles`: Remove to `ActiveActorManager` to control.
4. Constructors Simplified:  
   Remove `enemyMaximumYPosition`.
5. Modified Methods:  
   `initializeScene()`: initializeScene() now integrates handling mouse clicks and key events through the `userInputManager`. It also adds a `pauseMenuManager` for pause functionality.  
   `updateScene()`: The `updateScene()` method is now more streamlined, calling an `actorSpawnerManager` to update actors. `handleCollisionsAndPenetration()` to handle collision and penetration. `cleanUpDestroyedActors()` to remove destroy actors. `updateStatus()` to update levelview, kill count and check if game over.  
   `initializeTimeline()`: This method now uses a `PauseTransition` to create a delay before starting the game loop.  
   `initializeBackground()`: Refactored to delegate key press and release handling to `userInputManager`.  
   `handleCollisions()`: Refactored to handle a wider variety of collisions, including collisions between user plane and ammo boxes, as well as collisions with hearts.  
   `updateLevelView()`: Modified to add and remove hearts based on the player's health.  
   `winGame()`: The logic for playing a "win" audio and displaying the win screen has been added to this method.  
   `loseGame()`: The logic for playing a "lose" audio and displaying the game over screen has been added to this method.  
   `goToNextLevel(String levelName)`: Calling `cleanUp()` and using `setCurrentLevelName(levelName)` instead of `notifyObservers(levelName)`.  
   `addEnemyUnit(ActiveActorDestructible enemy)`: Adds an enemy unit to the level using the `actorSpawnerManager`.
6. Added Methods:  
   `updateGameStatus()`: This method was introduced to update the status by updating the number of enemies, kill count, and level view.  
   `handleCollisionsAndPenetration()`: A new method that calls both `handleEnemyPenetration()` and `handleCollisions()` together.  
   `cleanUpForAnimation()`: A new method that specifically cleans up all active destructible actors in preparation for an animation sequence.  
   `cleanUp()`: Now cleans up the game by stopping the timeline and clearing active actors, integrating multiple cleanup processes into one method.  
   `setCurrentLevelName(String levelName)`: Sets the name of the current level, which will update the `currentLevelName` property.  
   `currentLevelNameProperty()`: Returns a `StringProperty` for the current level name, allowing for data binding or observation.  
   `setGameOver(boolean gameOver)`: Sets the game over status.  
   `isGameOver()`: Returns the current game over status (whether the game is over or not).  
   `getUserInputManager()`: Returns the UserInputManager instance used to handle user input.

## Unexpected Problems

### Issue: Boss Health Bar

- **Problem**: In LevelThree, there are two condition to go to next level. 1.Defeat boss. 2.Kill 30 enemies.
If boss die first. Health Bar will still show in the game.
- **Solution**: Add a method in BossHealthBar `hide()` and in LevelViewLevelThree called this method naming `hideHealthBar()`.
I override `updateLevelView()` to check boss is destroyed or not. If destroyed, called method `hideHealthBar()`.


- **Problem**: In LevelFour, health bar will show before multi-stage boss 2 and 3 added.
- **Solution**: In LevelViewLevelFour, initialize three health bars but hide them. Check the stage, if in first stage, then show mutation boss1 health bar.
Add boolean to check mutation boss 2 and 3 are added. If first mutation boss die, then add mutation boss 2 and show boss2 health bar.
Set boss2Added = true to prevent initialize boss2 again.

### Issue: Animation Entering And Leaving Level

- **Problem**: Enemy and objects will spawn during user animation entering.
- **Solution**: I set 2 seconds delay for Timeline in LevelParent class to prevent enemy spawn during animation.


- **Problem**: Enemy and objects will spawn during user animation leaving.
- **Solution**: I add boolean to check whether game is over `gameOver`. In each level, if user finish current level target, `setGameOver(true)`. 
`spawnEnemyUnits()` will check whether game over or not. If game over then stop spawn. For objects, I add levelParent to ActorSpawnerManager.
And `spawnRandomHeart()` and `spawnRandomAmmoBox()` will check game is over or not.


- **Problem**: User can move, shoot and open pause menu during animation entering and leaving.
- **Solution**: I set two boolean in UserInputManager. `canAcceptInput` and `gameIsOver`. After 2 seconds, `canAcceptInput` will be set to be true, then user can move and shoot.
Current level completed, `gameIsOver` will be set to be true, user cannot move and shoot. `handleKeyPressed` and `handleKeyReleased` will check boolean.


- **Problem**: No animation for leaving.
- **Solution**: In each level, I write a new method `delayToNextLevel()`, and I set a 2 seconds delay before `goToNextLevel()`.


- **Problem**: Leaving level audio overlapping.
- **Solution**: Because `checkIfGameOver()` will be called continuously. So when complete level, `delayToNextLevel()` will be called continuously.
Using LevelParent boolean `gameOver` to ensure call `delayToNextLevel()` only once.

### Issue: Shoot Sound

- **Problem**: When the level ends, if the user continues to hold down the spacebar, the next level will trigger shoot sound even if the user does not press the spacebar.
- **Solution**: I add a method `clearActiveKeys()`. If game is over, this method is called in LevelParent `cleanUp()` to clear active keys.

### Issue: User, projectile, enemy plane and boss hit boxes

- **Problem**: The bullet will cause the player or enemy to lose health, but visually it does not hit.
- **Solution**: I used two method. 1.Remove white space from the images. 2.Add hit box rectangle.