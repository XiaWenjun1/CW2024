package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.LevelView;
import com.example.demo.Display.ScoreBoard;
import com.example.demo.Object.EnemyPlane;

public class LevelOne extends LevelParent {

	private ScoreBoard scoreBoard;
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
			scoreBoard.updateCurrentKills(getUser().getNumberOfKills());
			if (userHasReachedKillTarget()) {
				goToNextLevel(NEXT_LEVEL);
			}
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		scoreBoard = new ScoreBoard(10, 730, KILLS_TO_ADVANCE);
		getRoot().getChildren().add(scoreBoard.getContainer());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double minY = 50;
				double maxY = getEnemyMaximumYPosition();
				double newEnemyInitialYPosition = minY + Math.random() * (maxY - minY);
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, LevelOne.this);
				addEnemyUnit(newEnemy);
			}
		}
	}


	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
