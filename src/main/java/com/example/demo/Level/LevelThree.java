package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Display.LevelView;
import com.example.demo.Display.LevelViewLevelTwo;
import com.example.demo.Display.ScoreBoard;
import com.example.demo.Display.TargetLevelTwo;
import com.example.demo.Object.Boss;
import com.example.demo.Object.EnemyPlane;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 50;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private final Boss boss;
    private LevelViewLevelTwo levelView;
    private ScoreBoard scoreBoard;
    private TargetLevelTwo targetLevelTwo;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss(this);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
        scoreBoard = ScoreBoard.createScoreBoard(KILLS_TO_ADVANCE);
        getRoot().getChildren().add(scoreBoard.getContainer());
        targetLevelTwo = new TargetLevelTwo(getRoot());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else {
            scoreBoard.updateCurrentKills(getUser().getNumberOfKills());
            if (userHasReachedKillTarget() && boss.isDestroyed()) {
                targetLevelTwo.hideHint();
                winGame();
            }
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        if (getUser().getNumberOfKills() < KILLS_TO_ADVANCE) {
            for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
                if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                    double minY = getEnemyMinimumYPosition();
                    double maxY = getEnemyMaximumYPosition();
                    double newEnemyInitialYPosition = minY + Math.random() * (maxY - minY);
                    ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, LevelThree.this);
                    addEnemyUnit(newEnemy);
                }
            }
        }

        if (!isBossAdded()) {
            addEnemyUnit(boss);
            if (!getRoot().getChildren().contains(boss)) {
                getRoot().getChildren().add(boss);
            }
            setBossAdded(true);
            targetLevelTwo.showHint();
        }

        checkBossDeathAndHideHealthBar();
    }

    private void checkBossDeathAndHideHealthBar() {
        if (boss.isDestroyed()) {
            boss.hideHealthBar();
        }
    }

    private boolean bossAdded = false;

    private boolean isBossAdded() {
        return bossAdded;
    }

    private void setBossAdded(boolean added) {
        this.bossAdded = added;
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }
}
