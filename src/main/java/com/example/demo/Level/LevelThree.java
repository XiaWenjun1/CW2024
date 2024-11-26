package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelThree;
import com.example.demo.Actor.Boss.ParentBoss.Boss;
import com.example.demo.Actor.EnemyPlane.EnemyPlane;

/**
 * Represents the third level of the game. It contains logic for spawning enemies,
 * managing the boss, and progressing to the next level based on the player's actions.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Level.LevelFour";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int BOSS_HEALTH = 20;
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 30;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private final Boss boss;
    private LevelViewLevelThree levelView;
    private boolean bossAdded = false;

    /**
     * Constructs the third level with the specified screen height and width.
     * It also initializes the boss with the specified health.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = new Boss(BOSS_HEALTH);
    }

    /**
     * Initializes friendly units (the player) and adds them to the root of the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over by verifying whether the player is destroyed
     * or if the player has reached the kill target and the boss is destroyed.
     * If the conditions are met, the game advances to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else {
            if (userHasReachedKillTarget() && boss.isDestroyed()) {
                goToNextLevel(NEXT_LEVEL);
            }
        }
    }

    /**
     * Spawns enemy units with a specified probability and adds them to the level.
     * The boss is added to the level once certain conditions are met.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        if (getUser().getNumberOfKills() < KILLS_TO_ADVANCE) {
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

        if (!isBossAdded()) {
            addEnemyUnit(boss);
            if (!getRoot().getChildren().contains(boss)) {
                getRoot().getChildren().add(boss);
            }
            setBossAdded(true);
        }
    }

    /**
     * Updates the level view, including displaying the number of kills, boss health, position, and shield status.
     * If the boss is destroyed, the health bar is hidden.
     */
    @Override
    public void updateLevelView() {
        super.updateLevelView();
        levelView.updateKills(getUser().getNumberOfKills(), KILLS_TO_ADVANCE);

        if (!boss.isDestroyed()) {
            levelView.updateBossHealth(boss.getHealth());
            levelView.updateBossHealthPosition(boss);
            levelView.updateShieldPosition(boss);
            if (boss.getShielded()) {
                levelView.showShield();
            } else {
                levelView.hideShield();
            }
        } else {
            levelView.hideHealthBar();
        }
    }

    /**
     * Checks whether the boss has been added to the level.
     *
     * @return true if the boss has been added, otherwise false.
     */
    private boolean isBossAdded() {
        return bossAdded;
    }

    /**
     * Sets whether the boss has been added to the level.
     *
     * @param added true if the boss has been added, false otherwise.
     */
    private void setBossAdded(boolean added) {
        this.bossAdded = added;
    }

    /**
     * Checks if the player has reached the kill target to advance to the next level.
     *
     * @return true if the player has reached the kill target, otherwise false.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

    /**
     * Instantiates the level view for Level Three, which includes the player's health, boss's health, and kill target.
     *
     * @return the level view for Level Three.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH, 0, KILLS_TO_ADVANCE);
        return levelView;
    }
}