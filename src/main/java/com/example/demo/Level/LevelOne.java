package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.HeavyEnemy;
import com.example.demo.Actor.Plane.SpeedEnemy;
import com.example.demo.Level.LevelManager.AudioManager;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelOne;
import com.example.demo.Actor.Plane.EnemyPlane;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

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
	 * The view for the first level, responsible for rendering and managing the level's UI elements.
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
	 * The game ends if the player is destroyed or if the kill target is reached.
	 * If the player is destroyed, the game triggers a loss. If the target is met,
	 * the next level is loaded.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			delayToNextLevel();
		}
	}

	/**
	 * Delays transitioning to the next level, trigger teleport out audio and plays the exit animation.
	 * After the animation finishes, the next level is loaded.
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
	 * Initializes the friendly units in the level.
	 * Adds the user (player) plane to the root node, setting up the player for the game.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		AudioManager.getInstance().triggerTeleportInAudio();
		getRoot().getChildren().add(getUser());
		super.getUser().spiralPortalEnter();
	}

	/**
	 * Spawns enemy units in the current level.
	 * Adds new enemies based on spawn probability until the total number of enemies
	 * reaches the specified limit. The type of enemy is chosen randomly.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (!super.isGameOver()) {
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
	}

	/**
	 * Updates the level view.
	 * This method refreshes the level's UI, such as the kill count displayed on the scoreboard. It is called every frame.
	 */
	@Override
	public void updateLevelView() {
		super.updateLevelView();
		// Pass the correct currentKills and targetKills to update the scoreboard
		levelView.updateKills(getUser().getNumberOfKills(), KILLS_TO_ADVANCE);
	}

	/**
	 * Instantiates the LevelView for this level.
	 * It creates an instance of LevelViewLevelOne and sets it up with the current player's health and other relevant data.
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
	protected boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Returns the fully qualified class name of the next level.
	 *
	 * @return the next level's class name.
	 */
	public String getNextLevel() {
		return NEXT_LEVEL;
	}
}