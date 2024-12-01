package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.HeavyEnemy.HeavyEnemy;
import com.example.demo.Actor.SpeedEnemy.SpeedEnemy;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelOne;
import com.example.demo.Actor.EnemyPlane.EnemyPlane;

/**
 * Represents the first level of the game.
 * <p>
 * This class manages the game logic for the first level, including spawning enemy units,
 * checking if the game is over, and advancing to the next level once the player reaches the required
 * kill target. The game will end if the player is destroyed, and the player will advance to the next level
 * once the kill target is reached.
 * </p>
 */
public class LevelOne extends LevelParent {
	/**
	 * The view for the first level, which is responsible for rendering and managing the level's UI elements.
	 */
	private LevelViewLevelOne levelView;

	/**
	 * The background image used for the first level.
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

	/**
	 * The fully qualified name of the class for the next level (LevelTwo).
	 */
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelTwo";

	/**
	 * The total number of enemies to be spawned in this level.
	 */
	private static final int TOTAL_ENEMIES = 5;

	/**
	 * The number of kills required to advance to the next level.
	 */
	private static final int KILLS_TO_ADVANCE = 10;

	/**
	 * The probability with which new enemies are spawned in the level.
	 * Ranges from 0.0 to 1.0.
	 */
	private static final double ENEMY_SPAWN_PROBABILITY = .20;

	/**
	 * The initial health of the player in this level.
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * Constructs a LevelOne instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth the width of the game screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the game is over.
	 * <p>
	 * The game ends if the player is destroyed or if the player reaches the kill target.
	 * If the player is destroyed, the game will trigger a loss. If the kill target is met,
	 * the next level is loaded.
	 * </p>
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else {
			if (userHasReachedKillTarget()) {
				goToNextLevel(NEXT_LEVEL);
			}
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
		getRoot().getChildren().add(getUser());
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

	/**
	 * Updates the level view.
	 * <p>
	 * This method refreshes the level's UI, such as the kill count displayed on the scoreboard. It is called every frame.
	 * </p>
	 */
	@Override
	public void updateLevelView() {
		super.updateLevelView();
		// Pass the correct currentKills and targetKills to update the scoreboard
		levelView.updateKills(getUser().getNumberOfKills(), KILLS_TO_ADVANCE);
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
		levelView = new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH, 0, KILLS_TO_ADVANCE);
		return levelView;
	}

	/**
	 * Checks if the user has reached the kill target required to advance to the next level.
	 *
	 * @return true if the user has reached the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}