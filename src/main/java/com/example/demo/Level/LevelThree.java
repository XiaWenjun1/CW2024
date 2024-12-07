package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.HeavyEnemy;
import com.example.demo.Actor.Plane.SpeedEnemy;
import com.example.demo.Level.LevelManager.AudioManager;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelThree;
import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Actor.Plane.EnemyPlane;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Represents the third level of the game, where the player faces more enemies and the boss.
 * <p>
 * In this level, the player must defeat a set number of enemies to progress, and once the player reaches the kill target,
 * the boss is spawned. If the player destroys the boss and reaches the kill target, the game moves to the next level.
 * </p>
 * This class extends {@link LevelParent} and handles the specific logic for spawning enemies, managing the boss,
 * and progressing to the next level.
 */
public class LevelThree extends LevelParent {
    /**
     * The background image file path for Level Three.
     * <p>
     * This constant defines the file path of the background image that will be displayed during Level Three. It provides
     * the visual theme for the level and sets the atmosphere for the gameplay.
     * </p>
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";

    /**
     * The class path for the next level to load after the boss is defeated.
     * <p>
     * This constant specifies the fully qualified class path to the next level that will be loaded once the boss
     * in Level Three is defeated. This allows the game to transition smoothly to the next stage of the game.
     * </p>
     */
    private static final String NEXT_LEVEL = "com.example.demo.Level.LevelFour";

    /**
     * The initial health of the player at the start of Level Three.
     * <p>
     * This constant defines how many health points the player has when they start Level Three. This determines the
     * player's health at the beginning of this level.
     * </p>
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The health of the boss in Level Three.
     * <p>
     * This constant defines the amount of health the boss has in Level Three. It specifies how much damage the player
     * needs to inflict to defeat the boss and advance to the next level.
     * </p>
     */
    private static final int BOSS_HEALTH = 20;

    /**
     * The total number of enemies to spawn before the boss appears.
     * <p>
     * This constant defines the maximum number of enemies that will spawn in Level Three before the boss is introduced.
     * It serves as a threshold for how many enemies the player must face before progressing to the boss fight.
     * </p>
     */
    private static final int TOTAL_ENEMIES = 5;

    /**
     * The number of kills required for the player to advance to the next level.
     * <p>
     * This constant defines the threshold of kills the player must achieve in Level Three before the boss spawns.
     * After this kill count is reached, the boss will appear, and defeating the boss will advance the player to the next level.
     * </p>
     */
    private static final int KILLS_TO_ADVANCE = 30;

    /**
     * The probability of spawning a new enemy.
     * <p>
     * This constant defines the likelihood that a new enemy will spawn during Level Three. The value is between 0.0 and 1.0,
     * with a higher value indicating a higher probability of enemy spawn.
     * </p>
     */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    /**
     * The boss in Level Three.
     * <p>
     * This reference holds the instance of the boss that will be spawned in Level Three. The boss is an important
     * part of the level and must be defeated by the player to progress to the next level.
     * </p>
     */
    private final Boss boss;

    /**
     * The view for Level Three, responsible for managing the display and user interface for this level.
     * <p>
     * This reference holds the view layer of Level Three, which includes displaying health, win/loss images, and other
     * relevant UI elements for the level.
     * </p>
     */
    private LevelViewLevelThree levelView;

    /**
     * A flag indicating whether the boss has been added to the level.
     * <p>
     * This boolean flag tracks whether the boss has already been added to the level. It is used to ensure that the boss
     * is added only once when the player reaches the appropriate kill count.
     * </p>
     */
    private boolean bossAdded = false;

    /**
     * A flag indicating whether the boss active shield.
     */
    private boolean shieldAudioPlayed = false;

    /**
     * Constructs the third level with the specified screen height and width.
     * <p>
     * This constructor initializes the level with the given screen size and sets up the boss
     * with the specified health.
     * </p>
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss(BOSS_HEALTH);
    }

    /**
     * Initializes the friendly unit (player) and adds it to the scene.
     * This method ensures that the player is placed into the scene and triggers teleport in audio and the player's
     * spiral portal entry animation.
     */
    @Override
    protected void initializeFriendlyUnits() {
        AudioManager.getInstance().triggerTeleportInAudio();
        getRoot().getChildren().add(getUser());
        super.getUser().spiralPortalEnter();
    }

    /**
     * Checks if the game is over by verifying the player's status and boss defeat.
     * If the player is destroyed, the game ends. If the player reaches the kill target
     * and the boss is destroyed, the boss health bar is hidden and the game progresses to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else {
            if (userHasReachedKillTarget() && boss.isDestroyed()) {
                levelView.hideHealthBar();
                delayToNextLevel();
            }
        }
    }

    /**
     * Delays the transition to the next level and plays the exit animation.
     * Stops the player’s movement, triggers the teleport out audio exit animation, and after a delay, moves to the next level.
     */
    private void delayToNextLevel() {
        if (!super.isGameOver()) {
            super.setGameOver(true);
            AudioManager.getInstance().triggerTeleportOutAudio();
            super.getUser().stopHorizontalMovement();
            super.getUser().stopVerticalMovement();
            super.getUserInputManager().setGameIsOver(true);
            super.cleanUpForAnimation();
            // Play the exit animation
            getUser().spiralPortalExit(); // Assuming you have implemented the spiralPortalExit animation method
            // Delay the transition to the next level by waiting for the animation to finish
            PauseTransition delay = new PauseTransition(Duration.seconds(2)); // Delay for 2 seconds (adjust based on animation duration)
            delay.setOnFinished(event -> goToNextLevel(NEXT_LEVEL)); // After the delay, load the next level
            delay.play();
        }
    }

    /**
     * Spawns enemy units and introduces the boss after the kill target is reached.
     * Standard enemies are spawned based on a kill count condition, with random selection
     * of enemy types. Once the kill target is met, the boss is added to the level.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (!super.isGameOver()) {
            int currentNumberOfEnemies = getCurrentNumberOfEnemies();
            if (getUser().getNumberOfKills() < KILLS_TO_ADVANCE) {
                for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
                    if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                        double minY = getEnemyMinimumYPosition();
                        double maxY = getEnemyMaximumYPosition();
                        double newEnemyInitialYPosition = minY + Math.random() * (maxY - minY);
                        ActiveActorDestructible newEnemy;

                        double randomValue = Math.random();
                        if (randomValue < 0.4) {
                            newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                        } else if (randomValue < 0.7) {
                            newEnemy = new SpeedEnemy(getScreenWidth(), newEnemyInitialYPosition);
                        } else {
                            newEnemy = new HeavyEnemy(getScreenWidth(), newEnemyInitialYPosition);
                        }
                        addEnemyUnit(newEnemy);
                    }
                }
            }

            if (!isBossAdded()) {
                addEnemyUnit(boss);
                levelView.showBossHealthBar();
                setBossAdded(true);
            }
        }
    }

    /**
     * Updates the level view, including displaying the number of kills, boss health, position, and shield status.
     * If the boss is destroyed, the health bar is hidden.
     * <p>
     * This method ensures that the graphical interface reflects the state of the game, including the
     * boss's health and whether it is shielded.
     * </p>
     */
    @Override
    public void updateLevelView() {
        super.updateLevelView();
        levelView.updateKills(getUser().getNumberOfKills(), KILLS_TO_ADVANCE);

        if (!boss.isDestroyed()) {
            levelView.updateBossHealth(boss.getHealth());
            levelView.updateBossHealthPosition(boss);
            levelView.updateShieldPosition(boss);
            if (boss.getShielded()) {
                levelView.showShield();
                if (!shieldAudioPlayed) {
                    AudioManager.getInstance().triggerShieldAudio();
                    shieldAudioPlayed = true;
                }
            } else {
                levelView.hideShield();
                shieldAudioPlayed = false;
            }
        } else {
            levelView.hideHealthBar();
        }
    }

    /**
     * Checks whether the boss has been added to the level.
     * <p>
     * This method is used to verify if the boss has already been added to the scene, ensuring it is not
     * added multiple times.
     * </p>
     *
     * @return true if the boss has been added, otherwise false.
     */
    private boolean isBossAdded() {
        return bossAdded;
    }

    /**
     * Sets whether the boss has been added to the level.
     * <p>
     * This method marks the boss as added to the scene to prevent multiple additions.
     * </p>
     *
     * @param added true if the boss has been added, false otherwise.
     */
    private void setBossAdded(boolean added) {
        this.bossAdded = added;
    }

    /**
     * Checks if the player has reached the kill target to advance to the next level.
     * <p>
     * This method checks if the player has achieved the necessary number of kills to spawn the boss
     * and proceed to the next level.
     * </p>
     *
     * @return true if the player has reached the kill target, otherwise false.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Instantiates the level view for Level Three, which includes the player's health, boss's health, and kill target.
     * <p>
     * This method creates and returns the level view specific to Level Three, providing a visual interface for the player.
     * </p>
     *
     * @return the level view for Level Three.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH, 0, KILLS_TO_ADVANCE);
        return levelView;
    }

    /**
     * Returns the boss of the current level.
     *
     * @return the {@link Boss} object representing the boss of this level.
     */
    public Boss getBoss() { return boss; }

    /**
     * Returns the fully qualified class name of the next level.
     *
     * @return the next level's class name.
     */
    public String getNextLevel() {
        return NEXT_LEVEL;
    }
}