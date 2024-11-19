package com.example.demo.Level;

import com.example.demo.Display.LevelView;
import com.example.demo.Display.LevelViewLevelTwo;
import com.example.demo.Display.TargetLevelTwo;
import com.example.demo.Object.Boss;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private TargetLevelTwo targetLevelTwo;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(this);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		targetLevelTwo = new TargetLevelTwo(getRoot());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			targetLevelTwo.hideHint();
			goToNextLevel(NEXT_LEVEL);
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !isBossAdded()) {
			addEnemyUnit(boss);
			if (!getRoot().getChildren().contains(boss)) {
				getRoot().getChildren().add(boss);
			}
			setBossAdded(true);
			targetLevelTwo.showHint();
		}
	}

	private boolean bossAdded = false;

	private boolean isBossAdded() {
		return bossAdded;
	}

	private void setBossAdded(boolean added) {
		this.bossAdded = added;
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}
}
