## Improvements
- **Rename controller and classes**: Rename Controller to Control start.
- **Reconstruct and encapsulation**: Refactored the initialBackground method. Method encapsulation:
handleKeyPressed and handleKeyReleased are independent methods to facilitate understanding and maintenance of key processing logic.

## Bug Fixes
- **Prevented Repeated Level Loading**: Fixed a bug in the Control_Start 
and Controller classes where levels were being reloaded unnecessarily.
Now, the goToLevel method checks if the current level matches the requested level to
avoid redundant loading. This reduces potential lag and improves performance.
- **Shield Loading**: Shield will track boss x and y position and show up when it activated.

## Implemented Features

### Controls and Animations

- **Plane Animation**: Uses a `Timeline` to control the continuous movement of the player's plane.
- **Bullet Animation**: Bullets are fired by both the player and enemies; 
they move at a set speed and disappear upon reaching the screen boundary.
- **Enemy and Boss Animations**: The enemy and boss characters follow pre-defined paths using `PathTransition`, 
with the boss having a more complex movement pattern.

### Level

- **LevelOne**: Add ScoreBoard class to show Target Kill and Current Kill(It will update the destroy enemy plane number).
- **LevelTwo**: Add TargetLevelTwo class to show target hint.
- **LevelThree**: Enemies and bosses exist at the same time. The winning condition is to defeat the boss and kill a certain 
number of enemy planes, which both conditions must be met. (To reduce the difficulty of the game. 
When user reach the number of enemy plane killed, no more enemy plane spawn.) Target hint and ScoreBoard will show in left bottom of screen.
- **LevelFour**: 2 Bosses. User should destroy both of them to win the game.

### Sound Effects and Background Music

- **Button Hover Sound**: Each button plays a sound effect (`btnhover.wav`) on hover.
- **Background Music**: Background music (`bg.mp3`) loops on the main screen and during gameplay.
- **Game Sound**: Explosion sound (`explosion.mp3`) when fighter plane destroyed.

### Control Screen

- **Images**: 4 images(UserPlane, UserProjectile, Heart and AmmoBox).
- **Descriptions**: UserPlane: ↑ ↓ ← → or Mouse Dragging. UserProjectile: Space. Heart: Add one health. AmmoBox: Update user projectile.
- **Other Information**: Middle Mouse to pause game. Click continue button or reClick middle mouse to resume game.
- **Close Settings Screen**: Returns to the main screen and removes the blur effect.

### Settings Screen

- **Background Music Control**: The settings screen allows setting the background music via a toggle.
- **Game Sound Control**: The settings screen allows setting the explosion sound via a toggle.
- **Close Settings Screen**: Returns to the main screen and removes the blur effect.

### ActiveActor

- **Common features**: Add width to control image size more flexible. Add hitBox to make the hit range more reasonable.
- **User Plane**: Add left and right to make plane move horizontally. Add mouse control function.
- **User Projectile**: Add xPosition, fired at user plane's location. Add 3 levels projectile.
- **Boss**: Fix shield and limit movement. Add health bar.
- **Boss Projectile**: Add vertical velocity and set velocity method. Add two more fire patterns. Fire 3 balls one time straightly. 
Fire 3 balls one time with 3 different directions(Straight, LeftUp and LeftDown).
- **Enemy**:
- **Enemy Projectile**:
- **Ammo Box**: It is an item spawn randomly in game. When collision with user plane, user projectile will update (max 3 level)
user projectile will be bigger image(bigger hit box) and faster speed.
- **Boundary**: Collision with user and enemy bullets as well as ammo box to clean image.
- **Heart**: It is an item spawn randomly in game. When collision with user plane, user will increase 1 health(No maximum).

## Issues Encountered

- **Sound Playback Delay**: `AudioClip` is used to minimize delay in sound effects.
- **Path Movement Lag**: Adjusted `PathTransition` duration to optimize smooth movement for enemy and boss characters.
- **Boss Health Bar still exists when killed in LevelThree**: Add hideHealthBar function in Boss class. Using it in LevelThree
when boss destroyed.

## Project Structure

- **BattlePlane**: The main entry point of the game application, initializing the main screen and setting up the stage.
- **Control_Start**: Manages game level transitions and prevents redundant loading of levels by checking the current active level.
- **Control_Animation**: Manages animations for all elements (plane, bullets, enemies, and boss).
- **Control_Main**: Controls the main screen, including background music and button actions.
- **Control_Setting**: Manages the settings screen, including background music, game sounds and close functionality.
- **Control_PauseMenu**: When game is playing, user can click middle mouse to open pause menu. Timeline will stop, 
click 'Continue' to resume or click middle mouse again to resume game.
- **Control_EndGameMenu**: When user win or lose, 1 second after win or lose images will show EndGameMenu with 2 buttons(Return to Main, Exit)
'Return to Main' return to main menu. 'Exit' exit program.
- **Control_Control**: User Control description. 4 images(UserPlane, UserProjectile, Heart and AmmoBox). UserPlane: ↑ ↓ ← → or Mouse Dragging. 
UserProjectile: Space. Heart: Add one health. AmmoBox: Update user projectile. 
Middle Mouse to pause game. Click continue button or reClick middle mouse to resume game.

## Changelog

- **2024-11-06**: Completed **animations** for plane, bullets, enemies, and boss for **main menu**. Added **sound effects** and **background music** control.
- **2024-11-07**: Fixed bug preventing repeated level loading. Make the plane can **move horizontally**. 
Add **hitBox** to make hit range more reasonable.
- **2024-11-08**: Users cannot long press the space button to fire bullets. Each press can fire one bullet. 
The **mouse** has been added to move the user plane. When the mouse moves to the user plane, 
it will be displayed as a finger, and elsewhere as a pointer. 
Users can **drag the plane** by holding down the left mouse button, and stop when they release it. 
At the same time, the **original movement** method is also retained.
- **2024-11-09**: Using toggle (**checkbox**) to control background sound and game sound. Fix bugs:
boss cannot move out of the border. And **shield image** will show when it activated.
- **2024-11-10**: Add **health bar** to boss. Reconstruct initialBackground method. Method encapsulation:
handleKeyPressed and handleKeyReleased are independent methods to facilitate understanding and maintenance of key processing logic.
- **2024-11-11**: Add fighter plane **destroyed sound**(explosion sound), and user can use **settings to turn off** (background and explosion sounds).
- **2024-11-12**: Add fighter plane **destroyed image**(explosion image added). Changing to 60 FPS.
- **2024-11-13**: Add **pause menu** when game playing. **Middle mouse** to open pause menu, it will stop timeline and make **background blur**.
Click middle mouse to continue or click continue button to resume game.
- **2024-11-14**: Add **EndGameMenu**. When game win or lose, it will show a game over pane after **1 second** of win or lose images, with two buttons(Return to Main, Exit)
when click 'Return to Main', user will return to main page and the **settings follow your previous settings**. Click 'Exit', exit program.
- **2024-11-15**: Add folders for better handling class.
- **2024-11-16**: Add **ammo box**, spawn randomly in game. When collision with user plane, it will **update user projectile**(bigger image, hit box and faster speed), 
maximum user projectile level is 3, default is 1.
- **2024-11-17**: Add **boundaries** for collision with user and enemy projectile and object(ammo box) to **clean images** projectile and object(ammo box).
Reconstruct FighterPlane class **fireprojectiles method to make it a list**. Add boss one more **fire pattern(fire 3 balls one time)** and it will **change fire pattern automatically**.
Add **"CollisionManager, EndGameMenuManager, ExplosionEffectManager and PauseMenuManager"** to short LevelParent code.
- **2024-11-18**: Add **vertical velocity for boss projectile and set velocity method**. Add one more boss **fire pattern. Fire 3 balls with 3 different directions. 
(Straight, LeftUp and LeftDown)**. Add Two classes in display folder(**ScoreBoard and TargetLevelTwo**). ScoreBoard is used in LevelOne to
**show Target Kill and Current Kill**(It will update the destroy enemy plane number). TargetLevelTwo is used in LevelTwo to show **target hint**.
- **2024-11-19**: Add **LevelThree**. Enemies and bosses exist at the same time. The winning condition is to defeat the boss and kill a certain 
number of enemy planes, which both conditions must be met. (To reduce the difficulty of the game. When user reach the number of enemy plane killed, no more enemy plane spawn.)
Boss Health Bar will hide when it got destroyed. Winning condition will show on left bottom of screen. Add object **heart**. It is an item spawn randomly in game. 
When collision with user plane, user will **increase 1 health(No maximum)**. Add one method in **HeartDisplay class (addHeart)** to show change in display heart. 
Add one method in **LevelView class(addHearts)** to calculate number to add health. Add **UserInputManager** to handle Key and Mouse inputs to short LevelParent and UserPlane Code.
- **2024-11-20**: Add **ActiveActorManager** to handle list of friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, hearts and ammoBoxes.
Add **CleanDestroyedManager** to remove destroyed actors, and move boundaries codes into it to short LevelParent codes.
- **2024-11-21**: Add **Control Descriptions**. In Main Menu, click control to open control screen and show control descriptions.
- **2024-11-22**: Add **ActorSpawnerManager**(Used for items and enemies spawning, enemy projectiles generating) and 
**GameStateManager**(Used for Update number of enemies and kill count as well as display user health) to short LevelParent codes.
Add folders in object folder. Add **BossFirePattern** class to short Boss class codes. Add **LevelFour** 2 bosses. User should destroy both of them to win the game.