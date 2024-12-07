package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.HeavyEnemy;
import com.example.demo.Actor.Plane.SpeedEnemy;
import com.example.demo.Level.LevelManager.AudioManager;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewEndLess;
import com.example.demo.Actor.Plane.EnemyPlane;

/**
 * Represents the first level of the game.
 * <p>
 * This class manages the game logic for the first level, including spawning enemy units,
 * checking if the game is over, and advancing to the next level once the player reaches the required
 * kill target. The game will end if the player is destroyed, and the player will advance to the next level
 * once the kill target is reached.
 * </p>
 */
public class LevelEndLess extends LevelParent {

    /**
     * The view for the first level, responsible for rendering and managing the level's UI elements.
     */
    private LevelViewEndLess levelView;

    /**
     * The background image used for the first level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/levelEndLess.jpg";

    /**
     * The probability with which new enemies are spawned in the level.
     * Ranges from 0.0 to 1.0.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = .20;

    /**
     * The initial health of the player in this level.
     */
    private static final int PLAYER_INITIAL_HEALTH = 20;

    /**
     * The dynamic limit for the total number of enemies to spawn.
     * This value increases as the player's kill count grows.
     */
    private int dynamicEnemyLimit;

    /**
     * Constructs a LevelOne instance with the specified screen dimensions.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth the width of the game screen.
     */
    public LevelEndLess(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        this.dynamicEnemyLimit = 5;
    }

    /**
     * Checks if the game is over.
     * If the player is destroyed, the game will trigger a loss.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

    /**
     * Initializes the friendly units in the level.
     * <p>
     * This method adds the user (player) plane to the root node, setting up the player for the game.
     * </p>
     */
    @Override
    protected void initializeFriendlyUnits() {
        AudioManager.getInstance().triggerTeleportInAudio();
        getRoot().getChildren().add(getUser());
        super.getUser().spiralPortalEnter();
    }

    /**
     * Spawns enemy units in the current level.
     * <p>
     * This method adds new enemies to the level based on the spawn probability until the total number of enemies
     * reaches the specified limit (TOTAL_ENEMIES). Enemies are spawned within a vertical range defined by minY and maxY.
     * The type of enemy spawned is determined randomly:
     * </p>
     * <ul>
     *     <li>40% chance to spawn a {@link EnemyPlane}, a basic enemy plane with standard speed and health.</li>
     *     <li>30% chance to spawn a {@link SpeedEnemy}, a fast-moving enemy plane with lower health.</li>
     *     <li>30% chance to spawn a {@link HeavyEnemy}, a slow-moving but heavily armored enemy plane.</li>
     * </ul>
     * <p>
     * The position of each enemy is determined randomly within the vertical range [minY, maxY].
     * </p>
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < dynamicEnemyLimit - currentNumberOfEnemies; i++) {
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

    /**
     * Updates the level view.
     * <p>
     * This method refreshes the level's UI, such as the kill count displayed on the scoreboard. It is called every frame.
     * </p>
     */
    @Override
    public void updateLevelView() {
        super.updateLevelView();
        int currentKills = getUser().getNumberOfKills();
        this.dynamicEnemyLimit = 5 + (currentKills / 10) * 1;
        levelView.updateKills(currentKills);
    }

    /**
     * Instantiates the LevelView for this level.
     * <p>
     * It creates an instance of LevelViewLevelOne and sets it up with the current player's health and other relevant data.
     * </p>
     *
     * @return the instantiated LevelView for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewEndLess(getRoot(), PLAYER_INITIAL_HEALTH, 0);
        return levelView;
    }
}