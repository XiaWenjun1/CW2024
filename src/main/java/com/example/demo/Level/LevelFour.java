package com.example.demo.Level;

import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelFour;
import com.example.demo.Actor.Plane.MutationBoss1;
import com.example.demo.Actor.Plane.MutationBoss2;
import com.example.demo.Actor.Plane.MutationBoss3;

/**
 * Represents the fourth level of the game.
 * <p>
 * This class manages the game logic for the fourth level, including spawning bosses in sequence,
 * checking if the game is over, and updating the boss health status.
 * </p>
 */
public class LevelFour extends LevelParent {

    /**
     * The background image used for the fourth level.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";

    /**
     * The initial health of the player in this level.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * The health value for the first boss in the level.
     */
    private static final int BOSS1_HEALTH = 25;

    /**
     * The health value for the second boss in the level.
     */
    private static final int BOSS2_HEALTH = 35;

    /**
     * The health value for the third boss in the level.
     */
    private static final int BOSS3_HEALTH = 45;

    /**
     * The first boss of the level.
     */
    private MutationBoss1 boss1;

    /**
     * The second boss of the level.
     */
    private MutationBoss2 boss2;

    /**
     * The third boss of the level.
     */
    private MutationBoss3 boss3;

    /**
     * The level view object that handles displaying information about the level, such as health bars for the bosses.
     */
    private LevelViewLevelFour levelView;

    /**
     * A flag indicating whether the second boss has been added to the level.
     */
    private boolean boss2Added = false;

    /**
     * A flag indicating whether the third boss has been added to the level.
     */
    private boolean boss3Added = false;

    /**
     * Constructs a LevelFour instance with the specified screen dimensions and initializes the bosses.
     * <p>
     * The constructor creates the three bosses with their specified health values and passes them to the superclass.
     * </p>
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
     * <p>
     * It ensures that the player's plane is visible on the screen.
     * </p>
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over. The game ends if the player is destroyed or if the final boss (boss3) is destroyed.
     * <p>
     * If the player is destroyed, the game will trigger a loss. If the final boss is destroyed, the game will be won.
     * </p>
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
     * first boss1, then boss2, and finally boss3.
     * <p>
     * Bosses are added only once and are removed when destroyed. The bosses are added based on their destruction state.
     * </p>
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
     * <p>
     * This method ensures that the boss health bars and shield indicators are displayed and updated accordingly.
     * </p>
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

    /**
     * Returns the first boss of this level.
     * This boss is represented by {@link MutationBoss1}.
     *
     * @return the first boss (boss1) of this level.
     */
    public MutationBoss1 getBoss1() { return boss1; }

    /**
     * Returns the second boss of this level.
     * This boss is represented by {@link MutationBoss2}.
     *
     * @return the second boss (boss2) of this level.
     */
    public MutationBoss2 getBoss2() { return boss2; }

    /**
     * Returns the third boss of this level.
     * This boss is represented by {@link MutationBoss3}.
     *
     * @return the third boss (boss3) of this level.
     */
    public MutationBoss3 getBoss3() { return boss3; }
}