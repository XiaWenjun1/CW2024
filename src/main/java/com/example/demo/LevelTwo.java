package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss(this);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		// 如果当前没有敌人并且 Boss 还没有被添加，才添加 Boss
		if (getCurrentNumberOfEnemies() == 0 && !isBossAdded()) {
			addEnemyUnit(boss); // 将 Boss 添加到敌人列表
			getRoot().getChildren().add(boss);  // 确保 Boss 被添加到场景中
			setBossAdded(true); // 标记 Boss 已经添加
		}
	}

	// 新增一个方法来标记 Boss 是否已经添加
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
