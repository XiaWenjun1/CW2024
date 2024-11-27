package com.example.demo.Level;

import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelTwo;
import com.example.demo.Actor.Boss.ParentBoss.Boss;

/**
 * Represents the second level of the game, which includes the player, enemies, and the boss.
 * <p>
 * This level is a continuation of the game where the player faces more challenging enemies and a boss.
 * It extends from the {@link LevelParent} class and handles the logic specific to Level Two.
 * </p>
 */
public class LevelTwo extends LevelParent {
	/**
	 * The background image file path for Level Two.
	 * <p>
	 * This constant defines the file path of the background image that will be displayed during Level Two. The background
	 * helps set the atmosphere and theme for the gameplay in this particular level.
	 * </p>
	 */
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";

	/**
	 * The class path for the next level to load after the boss is defeated.
	 * <p>
	 * This constant specifies the fully qualified class path to the next level that will be loaded once the boss
	 * in Level Two is defeated. After completing this level, the game transitions to Level Three.
	 * </p>
	 */
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelThree";

	/**
	 * The initial health of the player at the start of Level Two.
	 * <p>
	 * This constant defines how many health points the player starts with when entering Level Two. This determines the
	 * player's starting health and influences the level of difficulty.
	 * </p>
	 */
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * The health of the boss in Level Two.
	 * <p>
	 * This constant defines how much health the boss has in Level Two. The player needs to inflict this amount of
	 * damage to defeat the boss and advance to the next level.
	 * </p>
	 */
	private static final int BOSS_HEALTH = 15;

	/**
	 * The boss in Level Two.
	 * <p>
	 * This reference holds the instance of the boss that will appear in Level Two. The player must defeat this boss
	 * to progress to the next level.
	 * </p>
	 */
	private final Boss boss;

	/**
	 * The view for Level Two, responsible for managing the display and user interface for this level.
	 * <p>
	 * This reference holds the view layer for Level Two, including health display, win/loss images, and other relevant
	 * UI elements for the level.
	 * </p>
	 */
	private LevelViewLevelTwo levelView;

	/**
	 * Constructor to initialize the second level with the specified screen height and width.
	 * <p>
	 * This constructor initializes the level with the given screen size and sets up the boss
	 * with the specified health.
	 * </p>
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(BOSS_HEALTH);
	}

	/**
	 * Initializes friendly units (in this case, the player) and adds them to the root of the scene.
	 * <p>
	 * This method ensures that the player (user) is placed into the scene for interaction.
	 * </p>
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if the game is over by verifying if the player or the boss is destroyed.
	 * <p>
	 * If the player is destroyed, the game is lost and the game over sequence is triggered.
	 * If the boss is destroyed, the game transitions to the next level.
	 * </p>
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units. In this level, the boss is spawned when there are no current enemies.
	 * <p>
	 * This method checks the number of enemies currently present in the level and spawns the boss
	 * if there are no enemies left. The boss is the main enemy of Level Two.
	 * </p>
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Updates the level view, including the boss's health, position, and shield status.
	 * <p>
	 * This method is called to update the game’s visual representation, including displaying
	 * the boss’s health and updating its shield status.
	 * </p>
	 */
	@Override
	public void updateLevelView() {
		super.updateLevelView();
		levelView.updateBossHealth(boss.getHealth());
		levelView.updateBossHealthPosition(boss);
		levelView.updateShieldPosition(boss);

		if (boss.getShielded()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}

	/**
	 * Instantiates the level view specific to Level Two, which includes the boss's health and shield.
	 * <p>
	 * This method sets up the visual elements of Level Two, specifically for the boss, including
	 * the health bar and shield display.
	 * </p>
	 *
	 * @return the level view for Level Two
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH);
		return levelView;
	}
}