package com.example.demo.Level;

import com.example.demo.Level.LevelManager.AudioManager;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelTwo;
import com.example.demo.Actor.Plane.Boss.Boss;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Represents the second level of the game, which includes the player, enemies, and the boss.
 * This level is a continuation of the game where the player faces more challenging enemies and a boss.
 * It extends from the {@link LevelParent} class and handles the logic specific to Level Two.
 */
public class LevelTwo extends LevelParent {
	/**
	 * The background image file path for Level Two.
	 * This constant defines the file path of the background image that will be displayed during Level Two. The background
	 * helps set the atmosphere and theme for the gameplay in this particular level.
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";

	/**
	 * The class path for the next level to load after the boss is defeated.
	 * This constant specifies the fully qualified class path to the next level that will be loaded once the boss
	 * in Level Two is defeated. After completing this level, the game transitions to Level Three.
	 */
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelThree";

	/**
	 * The initial health of the player at the start of Level Two.
	 * This constant defines how many health points the player starts with when entering Level Two. This determines the
	 * player's starting health and influences the level of difficulty.
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * The health of the boss in Level Two.
	 * This constant defines how much health the boss has in Level Two. The player needs to inflict this amount of
	 * damage to defeat the boss and advance to the next level.
	 */
	private static final int BOSS_HEALTH = 15;

	/**
	 * A flag indicating whether the boss active shield.
	 */
	private boolean shieldAudioPlayed = false;

	/**
	 * The boss in Level Two.
	 * This reference holds the instance of the boss that will appear in Level Two. The player must defeat this boss
	 * to progress to the next level.
	 */
	private final Boss boss;

	/**
	 * The view for Level Two, responsible for managing the display and user interface for this level.
	 * This reference holds the view layer for Level Two, including health display, win/loss images, and other relevant
	 * UI elements for the level.
	 */
	private LevelViewLevelTwo levelView;

	/**
	 * Constructor to initialize the second level with the specified screen height and width.
	 * This constructor initializes the level with the given screen size and sets up the boss
	 * with the specified health.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(BOSS_HEALTH);
	}

	/**
	 * Initializes friendly units by adding the player to the scene and triggers teleport in audio and the player's spiral portal entry animation.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		AudioManager.getInstance().triggerTeleportInAudio();
		getRoot().getChildren().add(getUser());
		super.getUser().spiralPortalEnter();
	}

	/**
	 * Checks if the game is over by verifying if the player or boss is destroyed.
	 * If the player is destroyed, the game is lost. If the boss is destroyed, the game transitions to the next level.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			levelView.hideBossHealthBar();
			delayToNextLevel();
		}
	}

	/**
	 * Delays the transition to the next level after the game is over.
	 * This method stops the player's movement, trigger teleport out audio, marks the game as over, triggers the exit animation,
	 * and waits for the animation to finish before transitioning to the next level.
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
	 * Spawns the boss enemy unit when there are no current enemies.
	 * This method checks the current number of enemies, and if there are no enemies left,
	 * it spawns the boss and shows the boss health bar.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (!super.isGameOver()) {
			if (getCurrentNumberOfEnemies() == 0) {
				addEnemyUnit(boss);
				levelView.showBossHealthBar();
			}
		}
	}

	/**
	 * Updates the level view, including the boss's health, position, and shield status.
	 * This method is called to update the game’s visual representation, including displaying
	 * the boss’s health and updating its shield status.
	 */
	@Override
	public void updateLevelView() {
		super.updateLevelView();
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
	}

	/**
	 * Instantiates the level view specific to Level Two, which includes the boss's health and shield.
	 * This method sets up the visual elements of Level Two, specifically for the boss, including
	 * the health bar and shield display.
	 *
	 * @return the level view for Level Two
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH);
		return levelView;
	}

	/**
	 * Returns the boss of the current level.
	 *
	 * @return the {@link Boss} object representing the boss of this level.
	 */
	public Boss getBoss() {
		return boss;
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