package com.example.demo.Level;

import com.example.demo.Display.LevelView;
import com.example.demo.Display.TargetLevelTwo;
import com.example.demo.Object.Boss.Boss;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss1;
    private final Boss boss2;
    private TargetLevelTwo targetLevelTwo;

    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss1 = new Boss(this);
        boss2 = new Boss(this);
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
        else if (boss1.isDestroyed() && boss2.isDestroyed()) {
            targetLevelTwo.hideHint();
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            if (!isBoss1Added()) {
                addEnemyUnit(boss1);
                if (!getRoot().getChildren().contains(boss1)) {
                    getRoot().getChildren().add(boss1);
                }
                setBoss1Added(true);
            }
            if (!isBoss2Added()) {
                addEnemyUnit(boss2);
                if (!getRoot().getChildren().contains(boss2)) {
                    getRoot().getChildren().add(boss2);
                }
                setBoss2Added(true);
            }
            targetLevelTwo.showHint();
        }
    }

    private boolean boss1Added = false;
    private boolean boss2Added = false;

    private boolean isBoss1Added() {
        return boss1Added;
    }

    private boolean isBoss2Added() {
        return boss2Added;
    }

    private void setBoss1Added(boolean added) {
        this.boss1Added = added;
    }

    private void setBoss2Added(boolean added) {
        this.boss2Added = added;
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }
}