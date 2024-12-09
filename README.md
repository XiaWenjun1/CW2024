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
5. If Maven not working properly. **Right Click** `pom.xml`. Select `Maven` > `Reimport`.

### Running
1. Using terminal:
    ```
    mvn javafx:run
    ```
2. Or using `Main` class file. In the `src` > `main` > `java` > `com.example.demo` package > `Controller`.

## Implemented and Working Properly

### 1. Power-Ups and Upgrades
- **Weapon Power-Ups**: Collect ammo box which move straight left (Spawn Randomly) will upgrade (Max level 5, default 1). Increase fire rate, bullet speed, bullet image size and bullet hit box.
- **Health Items**: Collect hearts which the movement trajectory is like sin(x) (Spawn Randomly) increase user health by 1. (No Maximum)

### 2. Player Plane Controls
- **Movement**: **Motion 1**: Player can move up, down, left, and right using default arrow keys. **Motion 2**: Press and hold the left mouse button to drag the plane.
- **Fire**: Optimized the situation where bullets cannot be fired when using keyboard control. (Can hold space bar and move plane at same time)

### 3. Sounds and Animations
- **Sounds**: Background music, button hover sound(in main page), explosion sound, heal sound(get object heart), upgrade sound(get object ammo box), 
win game sound, lose game sound, boss actives shield sound, user shoot sound, entering level sound, leaving level sound and user got damage sound.
- **Animations**: Explosion animations for all fighter plane, animations for entering and leaving a level for the player(Spiral).

### 4. Boundaries for cleanup to save memory
- **Clean**: Left boundary check collision with enemy projectiles, heart and ammo box. When they collision with left boundary, remove them from root.
Right boundary check collision with user projectile. When right boundary collision with user projectile, remove them from root.

### 5. Cleanup complete level to save memory
- **Clean**: When user go to next level, stopping timeline and cleaning up all children of the root of the scene to save memory.

### 6. The user is injured and flashes invincibility
- **Invincibility**: When user health decrease 1, user flashes and becomes invincible for two seconds.

### 7. Enemies and Bosses(fire patterns, health bar and shield)
- **Enemies**: Add two new enemies with new images. Speed enemy: Move very fast but cannot fire bullet. Heavy enemy: Move very 
slow but health is 5 and can fire bullet(laser image).
- **Bosses**: Add three mutation bosses each with different images, fire patterns and health. Mutation boss1: 2 fire patterns. One straight fireball.
Two straight purple venom bullet. Mutation boss2: 3 fire patterns. One straight fireball. Two straight purple venom bullets. Three straight blue venom bullets.
Mutation boss3: 4 fire patterns. One straight fireball. Two straight purple venom bullets. Three straight blue venom bullets. 
Three different directions(Upper left, Lower left and Straight) red venom bullets.
- **Bosses Health Bar**: It will track boss position using getLayoutX() + getTranslateX() and getLayoutY() + getTranslateY(). 
Displays a dynamic health bar for each boss. Updates in real-time based on the boss's current health. Can be shown or hidden as needed.
- **Bosses Shield**: Fix the shield image format from .jpg to .png. Tracked the boss positions using getLayoutX() + getTranslateX() and getLayoutY() + getTranslateY().

### 8. Score Board and Target Hint
- **Score Board**: It will show on left bottom in the game. It has two modes. In normal levels: **Kills: currentKills / targetKills**.
And currentKills will update if user kill enemies. TargetKills will update in different levels automatically. In endless level: 
**Kills: currentKills**.
- **TargetHint**: It will show on boss level on left bottom in the game. **Target: Defeat the Boss!**.

### 9. Main Menu
- **Animation Background**: The Control_Animation class manages all visual interactions and animations in the main menu, including:
**Plane Animation**: Moves the user's plane smoothly across the screen. Resets position after reaching the edge of the screen.
**Bullet Management**: Handles firing and movement of user, enemy, and boss bullets. Bullets are removed when off-screen.
**Enemy and Boss Movement**: Uses pre-defined paths(Arc) for enemy and boss animations. Implements smooth transitions with PathTransition.
**Initialization and Resource Management**: Loads images and sets element sizes for the game objects. Frees resources when no longer needed.
- **Tutorial**: The Control_Control class manages the information about how to play game including images and texts.
The control panel can be opened through the button *CONTROL* in the main menu. When you open it, it will make background blur.
To close the panel, click the *CLOSE* button located in the top-right corner, and it will remove blur effect.
- **Setting**: The Control_Setting class manages background music and game sounds. The setting panel can be opened through the button *SETTINGS* in the main menu. When you open it, it will make background blur. 
To close the panel, click the *CLOSE* button located in the top-right corner, and it will remove blur effect. 
There are many toggles: background music, explosion, shoot, get object, user damage, shield and interaction(win, lose and teleport).
User can control all of them by using toggle the checkbox to enable or mute.
- **Start Normal Level**: Click *START GAME* to start normal level.
- **Start Endless level**: Click *START ENDLESS LEVEL* to start endless level.

### 10. Pause Menu
This module manages the pause menu functionality, allowing players to pause the game, resume it, or return to the main menu. 
Below is a detailed explanation of the associated classes and features.
- **Control_PauseMenu**: The Control_PauseMenu class is a controller manages the pause menu UI and user interactions.
UI Components: Pause menu pane, continue button, and return to main button.
1. Show/Hide Pause Menu: Toggles the visibility of the pause menu and controls. 
2. Resume Game: Resumes gameplay and removes the blur effect.
3. Return to Main Menu: Cleans up current level resources and navigates back to the main menu.
- **PauseMenuManager**: The PauseMenuManager class handles pause menu logic and game state control.
1. Pause/Resume Game: Toggles the game's pause state by managing the timeline and inputs. 
2. Blur Effect: Applies or removes a Gaussian blur effect for a polished visual experience. 
3. Cleanup: Releases resources when switching levels or exiting. 

### 11. End Game Menu
This module manages the end-of-game scenarios, including displaying a win/loss result and navigating to the end game menu.
- **Control_EndGameMenu**: Handles the actions on the end game menu, including returning to the main menu or exiting the game. 
1. Return to Main: Navigates back to the main menu and cleans up the current level. 
2. Exit Game: Exits the application.
- **EndGameMenuManager**: Handles the logic for win and loss scenarios, ensuring the game only transitions once, 
avoiding redundant operations or UI conflicts.
1. Win Game: Displays a win image and transitions to the end game menu after a 2-second delay. 
2. Lose Game: Displays a game over image and transitions to the end game menu after a 2-second delay. 
3. Game Over Prevention: Ensures end game logic runs only once. 
4. Stage Management: Retrieves and closes the current stage, transitioning to the end game menu.

### 12. Levels
- **Level 1**: Add Score Board. Next level condition: Kill 10 enemies.
- **Level 2**: Add Boss Health Bar and Target hint. Dynamic health bar for each boss. Updates in real-time based on the boss's current health. 
Next level condition: Defeat boss.
- **Level 3**: Boss and Enemy plane occur at same time, including score board and target hint. After 30 kills enemies plane, no more enemies spawn.
Next level condition: Kill 30 enemies and defeat the boss. Must meet both conditions.
- **Level 4**: Mutation Boss with 3 stages. Each with different mutation boss's images and bullets images. Different fire patterns. 
Win condition: Defeat mutation boss.
- **Level Endless**: No win condition. Every 10 kill increase 1 enemy. Very relaxing level.

## Implemented but Not Working Properly
- **All objects are generated at layout x:0 and layout y:0**: Enemy plane, enemy projectiles, boss and boss projectiles initialize in the upper left corner. 
So when user at left top corner will decrease health. 
**Current Status To Address**: I set top boundary to avoid user go to left top corner. I still don't know why. Maybe because I add hit box rectangle.

## Features Not Implemented
1. **Add two Players mode**: Two players mode. Allow your friend to play with you. 
2. **Enemy and Boss movement pattern**: Actually, I used to add horizontal movement to boss, just adding horizontal movement part to 
existing movement method. But I found the boss movement was so weird and hard to hit it. So I remove it. 
3. **Slide Bar To Control Volume**: I used to add slide bar to control volume. But I don't know why it cannot save the adjusting volume when 
switching back from main to setting.

## New Java Classes
- **1.AmmoBox** (in **com.example.demo.Actor.Object** package) 
The AmmoBox class represents an ammo box object in the game, which moves horizontally across the screen. 
It can be collected by the player to provide power-ups for weapon. The class defines properties like image size, horizontal velocity, 
and spawn probability, along with methods for updating its position. 
- **2.Heart** (in **com.example.demo.Actor.Object** package) 
The Heart class represents a collectible heart object that moves horizontally while oscillating vertically in a sinusoidal pattern. 
The heart can be collected by the player to gain one health. It defines properties such as image size, horizontal velocity, spawn probability, and vertical oscillation amplitude. Methods include position updates. 
- **3.BossFirePattern** (in **com.example.demo.Actor.Plane.Boss** package) 
The BossFirePattern class controls the firing patterns of the boss in the game. It manages the creation of projectiles based on different attack types, including straight shots, 
scatter shots, and directional shots. The class retrieves the current position of the boss and generates a list of projectiles accordingly. Methods include random attack selection and projectile creation with varying velocities and trajectories. 
- **4.HeavyEnemy** (in **com.example.demo.Actor.Plane** package) 
The HeavyEnemy class represents a slow-moving, high-health enemy plane in the game. 
This class extends the FighterPlane and defines a heavy enemy with 5 health points. 
It fires projectiles at a low rate, using a random probability determined by its FIRE_RATE. 
The projectiles are created with an offset from the enemy’s position. This enemy moves slowly across the screen from right to left, and its position is updated every game tick. 
- **5.SpeedEnemy** (in **com.example.demo.Actor.Plane** package) 
The SpeedEnemy class represents a fast-moving enemy plane in the game.
This class extends the FighterPlane and defines a lightweight, high-speed enemy with minimal health (1 health point).
It moves quickly across the screen, from right to left, with a high horizontal velocity.
Unlike other enemies, the SpeedEnemy does not fire projectiles. It is designed to be a fragile but swift adversary that poses a unique challenge due to its speed. 
- **6.MutationBoss1** (in **com.example.demo.Actor.Plane** package) 
The MutationBoss1 class represents a mutated boss in the game, extending the Boss class. This class manages the mutated boss's behavior, 
including firing projectiles in various patterns. It utilizes a BossFirePattern manager to determine the type of projectiles to fire, 
such as one straight fireball or two straight purple venom attacks. The boss has a customizable fire rate, influencing how often it fires projectiles. 
The boss's image and size are set upon initialization. 
- **7.MutationBoss2** (in **com.example.demo.Actor.Plane** package) 
Almost same with Mutation Boss1. But changing images and add one fire pattern.(three straight blue venom) (It has three fire patterns). 
- **8.MutationBoss3** (in **com.example.demo.Actor.Plane** package) 
Almost same with Mutation Boss2. But changing images and add one fire pattern.(three scatter red venom) (It has four fire patterns). 
- **9.HeavyEnemyProjectile** (in **com.example.demo.Actor.Projectile** package) 
The HeavyEnemyProjectile class represents a projectile fired by a HeavyEnemy plane. 
It extends the Projectile class and defines the behavior of projectiles shot by heavy enemy planes. 
These projectiles move horizontally across the screen at a fixed speed and have a specific image and size. 
The class includes methods to update the projectile's position and hit box, allowing for proper movement. 
- **10.MutationBossProjectile1** (in **com.example.demo.Actor.Projectile** package) 
The MutationBossProjectile1 class represents a projectile fired by the MutationBoss1. 
It extends the BossProjectile class and is tailored for the specific appearance and behavior of the mutation boss's projectiles. 
- **11.MutationBossProjectile2** (in **com.example.demo.Actor.Projectile** package) 
The MutationBossProjectile2 class represents a projectile fired by the MutationBoss2. 
It extends the BossProjectile class and is tailored for the specific appearance and behavior of the mutation boss's projectiles. 
- **12.MutationBossProjectile3** (in **com.example.demo.Actor.Projectile** package) 
The MutationBossProjectile3 class represents a projectile fired by the MutationBoss3. 
It extends the BossProjectile class and is tailored for the specific appearance and behavior of the mutation boss's projectiles. 
- **13.Boundary** (in **com.example.demo.Actor** package) 
The Boundary class represents the left and right boundary markers in the game. It extends the Rectangle class and is used to clean objects, user and enemy projectiles. 
The class provides methods for creating static left and right boundaries at predefined positions, with a width of 1px (making it invisible) and a height that spans the game area. 
Although the class doesn't currently support dynamic updates, it includes an updatePosition method for potential future extensions. 
- **14.Control_StartEndLess** (in **com.example.demo.Controller** package) 
It almost same with the previous controller, it is just the entrance for endless level in main menu. 
- **15.BossHealthBar** (in **com.example.demo.Display** package) 
The BossHealthBar class is responsible for visually displaying the health of the boss character in the game. 
It extends the HBox layout and contains a ProgressBar to represent the boss's health as a percentage.
The health bar is styled with a red color for the active portion and gray for the background.
The class provides methods for updating the health bar's progress as the boss's health changes, as well as for setting its position on the screen.
It also includes functionality to show or hide the health bar from view, ensuring that the health bar is only visible when necessary. 
- **16.Explosion** (in **com.example.demo.Display** package)
The Explosion class is responsible for handling explosion effects in the game, which includes both visual animations and sound effects.
It triggers an explosion animation at a specified location, where fighter plane destroyed.
The explosion consists of a sequence of 20 animation frames, each displayed for 50 milliseconds.
The class also plays an explosion sound effect when the animation starts. After the animation completes, the explosion image is removed from the screen.
The explosion animation is displayed using ImageView objects, and the frames are loaded from a specific path template (/com/example/demo/images/Explosion/explosion%d.png), where each frame is numbered sequentially. 
- **17.ScoreBoard** (in **com.example.demo.Display** package)
The ScoreBoard class manages the display of the player's kill count in the game. It has two modes: normal mode and endless mode.
In normal mode, it shows both the current kills and the target kills required for level progression. In endless mode, it only displays the current number of kills.
The scoreboard is represented as a horizontal box (HBox) and includes a Label to display the kill information.
The scoreboard’s position on the screen is fixed, and the kills are updated dynamically as the player progresses through the game.
The class also provides methods for showing or hiding the scoreboard and for updating the kill count. 
- **18.TargetLevel** (in **com.example.demo.Display** package) 
The TargetLevel class displays a hint message to guide the player during the game. This message typically provides the player with objectives, 
such as the target to defeat a boss or complete a specific task in the level. The class uses a Label to present the hint message, which is styled and positioned on the screen. 
By default, the hint text is "Target: Defeat the Boss!", but it can be customized with different messages and positions through a constructor. 
Initially, the hint is hidden, and it can be shown using the show() method, which brings the hint to the front of the interface. 
- **19.ActiveActorManager** (in **com.example.demo.Level.LevelManager** package)
The ActiveActorManager class manages all active game actors, including friendly units, enemy units, projectiles, ammo boxes, and hearts. It updates the state of these actors and provides methods to access them by category, ensuring efficient management during gameplay.
- **20.ActorSpawnerManager** (in **com.example.demo.Level.LevelManager** package)
The ActorSpawnerManager class is responsible for spawning various game actors, including enemies, projectiles, ammo boxes, and hearts.
It interacts with the ActiveActorManager to update and manage the active actors in the game, ensuring that the player's plane hitbox is correctly added to the scene.
The class also handles the generation of enemy fire and the random spawning of collectible items.
- **21.AudioManager** (in **com.example.demo.Level.LevelManager** package)
The AudioManager class manages all audio-related functions in the game, including background music and sound effects. 
It allows playing, stopping, and controlling audio settings such as volume and enabling/disabling sound effects. The class ensures centralized and efficient audio management during gameplay.
- **22.CleanDestroyedManager** (in **com.example.demo.Level.LevelManager** package)
The CleanDestroyedManager class handles the cleanup of game actors, such as projectiles, ammo boxes, and hearts, when they are out of bounds or destroyed.
It uses boundaries to check for out-of-bounds actors and removes them from the scene.
The class also interacts with the ActiveActorManager to efficiently manage the removal of destroyed or out-of-bounds actors, ensuring minimal memory usage during gameplay.
- **23.CollisionManager** (in **com.example.demo.Level.LevelManager** package)
The CollisionManager class is responsible for detecting and handling collisions between various game actors, such as projectiles, enemy units, the user plane, and collectible items.
It applies damage to colliding actors or triggers actions like upgrading projectiles or increasing health when the user plane collides with ammo boxes or hearts.
The class provides methods for handling different types of collisions, ensuring efficient interaction between game entities.
- **24.EndGameMenuManager** (in **com.example.demo.Level.LevelManager** package)
The EndGameMenuManager handles the end game process, displaying win or game over images and transitioning to the end game menu.
It ensures that the end game logic is executed only once and manages scene cleanup and the opening of the end game menu.
- **25.PauseMenuManager** (in **com.example.demo.Level.LevelManager** package)
The PauseMenuManager class handles the game pause functionality, including pausing, resuming, and managing the blur effect on UI elements. 
It controls the pause menu, manages the timeline and user input, and ensures proper cleanup of game elements when the pause menu is used.
- **26.UserInputManager** (in **com.example.demo.Level.LevelManager** package)
**Movement and Shooting**: Tracks key presses for user movement (up, down, left, right) and shooting projectiles when the spacebar is pressed.
**Mouse Interaction**: Allows the user to drag the plane with the mouse and changes cursor style when interacting with the plane.
**Game Pause**: Manages game pause and unpause states(Enter or Middle mouse), disabling/enabling user interactions accordingly.
**Cooldown Mechanism**: Implements a cooldown period for shooting projectiles, preventing continuous firing.
**Input Delay**: Introduces an initial input delay before accepting user inputs(for animation entering level).
**Game Over Handling**: Disables user inputs when the game is over to prevent further interactions(for animation leaving level).
- **27.LevelViewThree** (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to Level three.
- **28.LevelViewFour** (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to Level four.
- **29.LevelEndLess** (in **com.example.demo.Level.LevelView** package)
Manages the visual representation and UI elements specific to Level endless.
- **30.LevelThree** (in **com.example.demo.Level** package)
LevelThree boss will summon enemies. Total 30 enemies.
- **31.LevelFour** (in **com.example.demo.Level** package)
LevelFour featuring a multi-stage boss with changing images and fire patterns.
- **32.Control_Animation** (in **com.example.demo.Ui** package)
Control the animation for user plane, boss, enemy and their projectiles in Main Menu.
- **33.Control_Control** (in **com.example.demo.Ui** package)
It is a tutorial for user to know how to play the game.
- **34.Control_EndGameMenu** (in **com.example.demo.Ui** package)
It is the UI for EndGameMenu. For logic is implemented in EndGameMenuManager.
- **35.Control_Main** (in **com.example.demo.Ui** package)
It is the UI for Main Menu, with 4 buttons(START, START ENDLESSLEVEL, CONTROL and SETTINGS). And animations apply here.
- **36.Control_PauseMenu** (in **com.example.demo.Ui** package)
It is the UI for PauseMenu. For logic is implemented in PauseMenuManager.
- **37.Control_Setting** (in **com.example.demo.Ui** package)
It contains UI and logic. User can control all the audio here by ticking checkboxes.

## Modified Java Classes

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