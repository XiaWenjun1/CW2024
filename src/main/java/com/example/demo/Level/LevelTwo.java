package com.example.demo.Level;

import com.example.demo.Display.LevelView;
import com.example.demo.Display.LevelViewLevelTwo;
import com.example.demo.Object.Boss.Boss;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int BOSS_HEALTH = 15;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(BOSS_HEALTH);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

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

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH);
		return levelView;
	}
}