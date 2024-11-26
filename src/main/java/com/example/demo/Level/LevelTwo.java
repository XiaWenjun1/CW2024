package com.example.demo.Level;

import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelTwo;
import com.example.demo.Actor.Boss.ParentBoss.Boss;

/**
 * Represents the second level of the game, which includes the player, enemies, and the boss.
 * It extends from the LevelParent class and handles the logic specific to Level Two.
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int BOSS_HEALTH = 15;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	/**
	 * Constructor to initialize the second level with specified screen height and width.
	 * It initializes the boss with the specified health.
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
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if the game is over by verifying if the player or the boss is destroyed.
	 * If the player is destroyed, the game is lost.
	 * If the boss is destroyed, the next level is loaded.
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
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Updates the level view, including the boss's health, position, and shield status.
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
	 *
	 * @return the level view for Level Two
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH);
		return levelView;
	}
}