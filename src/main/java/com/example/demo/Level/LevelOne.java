package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelOne;
import com.example.demo.Actor.EnemyPlane.EnemyPlane;

/**
 * Represents the first level of the game.
 * This class manages the game logic, including spawning enemies, checking if the game is over,
 * and advancing to the next level once the player reaches the required kill target.
 */
public class LevelOne extends LevelParent {

	private LevelViewLevelOne levelView;
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
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
	 * Checks if the game is over. The game ends if the player is destroyed or if the player reaches the kill target.
	 * If the player is destroyed, the game will trigger a loss. If the kill target is met, the next level is loaded.
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
	 * Initializes the friendly units in the level. This method adds the user (player) plane to the root node.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units in the current level. This method adds new enemies based on the spawn probability
	 * until the total number of enemies reaches the specified limit (TOTAL_ENEMIES).
	 *
	 * Enemies are spawned within a certain vertical range defined by minY and maxY.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double minY = getEnemyMinimumYPosition();
				double maxY = getEnemyMaximumYPosition();
				double newEnemyInitialYPosition = minY + Math.random() * (maxY - minY);
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Updates the level view, such as the kill count displayed on the scoreboard.
	 * This method is called every frame to refresh the UI.
	 */
	@Override
	public void updateLevelView() {
		super.updateLevelView();
		// Pass the correct currentKills and targetKills to update the scoreboard
		levelView.updateKills(getUser().getNumberOfKills(), KILLS_TO_ADVANCE);
	}

	/**
	 * Instantiates the LevelView for this level. It creates an instance of LevelViewLevelOne
	 * and sets it up with the current player's health and other relevant data.
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