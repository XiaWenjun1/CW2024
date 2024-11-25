package com.example.demo.Level;

import com.example.demo.Display.*;
import com.example.demo.Object.Boss.MutationBoss1;
import com.example.demo.Object.Boss.MutationBoss2;
import com.example.demo.Object.Boss.MutationBoss3;

public class LevelFour extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int BOSS1_HEALTH = 25;
    private static final int BOSS2_HEALTH = 35;
    private static final int BOSS3_HEALTH = 45;

    private MutationBoss1 boss1;
    private MutationBoss2 boss2;
    private MutationBoss3 boss3;
    private LevelViewLevelFour levelView;

    private boolean boss2Added = false;
    private boolean boss3Added = false;

    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss1 = new MutationBoss1(BOSS1_HEALTH);
        boss2 = new MutationBoss2(BOSS2_HEALTH);
        boss3 = new MutationBoss3(BOSS3_HEALTH);
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss3.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            if (!boss1.isDestroyed()) {
                addEnemyUnit(boss1);
                levelView.showBossHealthBar(boss1);
            } else if (!boss2.isDestroyed() && !boss2Added) {
                addEnemyUnit(boss2);
                boss2Added = true;
                levelView.showBossHealthBar(boss2);
            } else if (!boss3.isDestroyed() && !boss3Added) {
                addEnemyUnit(boss3);
                boss3Added = true;
                levelView.showBossHealthBar(boss3);
            }
        }
    }

    @Override
    public void updateLevelView() {
        super.updateLevelView();

        // 更新 boss1 的血条
        if (boss1.isDestroyed()) {
            levelView.hideBossHealthBar(boss1);
        } else {
            levelView.updateShieldPosition(boss1);
            levelView.updateBossHealth(boss1.getHealth(), boss1);
            levelView.updateBossHealthPosition(boss1);

            if (boss1.getShielded()) {
                levelView.showShield();
            } else {
                levelView.hideShield();
            }
        }

        // 更新 boss2 的血条（仅当 boss2 已添加）
        if (boss2Added) {
            if (boss2.isDestroyed()) {
                levelView.hideBossHealthBar(boss2);
                boss2Added = false;  // 标记 boss2 已摧毁
            } else {
                levelView.updateShieldPosition(boss2);
                levelView.updateBossHealth(boss2.getHealth(), boss2);
                levelView.updateBossHealthPosition(boss2);

                if (boss2.getShielded()) {
                    levelView.showShield();
                } else {
                    levelView.hideShield();
                }
            }
        }

        // 更新 boss3 的血条（仅当 boss3 已添加）
        if (boss3Added) {
            if (boss3.isDestroyed()) {
                levelView.hideBossHealthBar(boss3);
                boss3Added = false;  // 标记 boss3 已摧毁
            } else {
                levelView.updateShieldPosition(boss3);
                levelView.updateBossHealth(boss3.getHealth(), boss3);
                levelView.updateBossHealthPosition(boss3);

                if (boss3.getShielded()) {
                    levelView.showShield();
                } else {
                    levelView.hideShield();
                }
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, BOSS1_HEALTH);
        return levelView;
    }
}