package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.LevelView;
import com.example.demo.Display.LevelViewLevelOne;
import com.example.demo.Object.EnemyPlane.EnemyPlane;

public class LevelOne extends LevelParent {

	private LevelViewLevelOne levelView;
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

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

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

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

	@Override
	public void updateLevelView() {
		super.updateLevelView();
		// Pass the correct currentKills and targetKills to update the scoreboard
		levelView.updateKills(getUser().getNumberOfKills(), KILLS_TO_ADVANCE);
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH, 0, KILLS_TO_ADVANCE);
		return levelView;
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}