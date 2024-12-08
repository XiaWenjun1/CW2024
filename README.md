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

