package com.example.demo.Level;

import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelFour;
import com.example.demo.Actor.Boss.MutationBoss1.MutationBoss1;
import com.example.demo.Actor.Boss.MutationBoss2.MutationBoss2;
import com.example.demo.Actor.Boss.MutationBoss3.MutationBoss3;

/**
 * Represents the fourth level of the game.
 * This class manages the game logic, including spawning bosses in sequence,
 * checking if the game is over, and updating the boss health status.
 */
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

    /**
     * Constructs a LevelFour instance with the specified screen dimensions and initializes the bosses.
     *
     * @param screenHeight the height of the game screen.
     * @param screenWidth the width of the game screen.
     */
    public LevelFour(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss1 = new MutationBoss1(BOSS1_HEALTH);
        boss2 = new MutationBoss2(BOSS2_HEALTH);
        boss3 = new MutationBoss3(BOSS3_HEALTH);
    }

    /**
     * Initializes the friendly units in the level. This method adds the user (player) plane to the root node.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over. The game ends if the player is destroyed or if the final boss (boss3) is destroyed.
     * If the player is destroyed, the game will trigger a loss. If the final boss is destroyed, the game will be won.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss3.isDestroyed()) {
            winGame();
        }
    }

    /**
     * Spawns enemy units (bosses) in the current level. This method adds bosses in sequence:
     * first boss1, then boss2, and finally boss3. Bosses are added based on their destruction state.
     * Bosses are added only once and are removed when destroyed.
     */
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

    /**
     * Updates the level view, such as updating the health and shield status of bosses.
     * This method ensures that the boss health bars and shield indicators are displayed and updated accordingly.
     */
    @Override
    public void updateLevelView() {
        super.updateLevelView();

        // Update boss1 health bar
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

        // Update boss2 health bar (only if boss2 has been added)
        if (boss2Added) {
            if (boss2.isDestroyed()) {
                levelView.hideBossHealthBar(boss2);
                boss2Added = false;  // Mark boss2 as destroyed
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

        // Update boss3 health bar (only if boss3 has been added)
        if (boss3Added) {
            if (boss3.isDestroyed()) {
                levelView.hideBossHealthBar(boss3);
                boss3Added = false;  // Mark boss3 as destroyed
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

    /**
     * Instantiates the LevelView for this level. It creates an instance of LevelViewLevelFour
     * and sets it up with the current player's health and other relevant data.
     *
     * @return the instantiated LevelView for this level.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }
}