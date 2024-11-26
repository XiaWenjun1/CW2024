package com.example.demo.Level.LevelView;

import com.example.demo.Display.ScoreBoard;
import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Actor.Boss.ParentBoss.Boss;
import com.example.demo.Actor.Boss.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the level view for Level 3, responsible for managing and displaying elements
 * such as the boss health bar, shield, scoreboard, and other level-specific UI elements.
 */
public class LevelViewLevelThree extends LevelView {

    private final Group root;
    private final ShieldImage shieldImage;
    private final BossHealthBar bossHealthBar;
    private final TargetLevel hint;
    private final ScoreBoard scoreBoard;

    /**
     * Constructs a LevelViewLevelThree instance with the specified root group, hearts to display,
     * the boss's health, initial kills, and target kills for the scoreboard.
     *
     * @param root the root container of the scene where the UI elements will be added.
     * @param heartsToDisplay the initial number of hearts to display on the UI.
     * @param bossHealth the health of the boss to be displayed in the health bar.
     * @param initialKills the initial number of kills to display on the scoreboard.
     * @param targetKills the target number of kills required to complete the level.
     */
    public LevelViewLevelThree(Group root, int heartsToDisplay, int bossHealth, int initialKills, int targetKills) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthBar = new BossHealthBar(bossHealth);
        this.hint = new TargetLevel();
        this.scoreBoard = new ScoreBoard(initialKills, targetKills);

        initializeUI();
    }

    /**
     * Initializes the user interface for Level 3 by adding the shield image, boss health bar,
     * hint, and score board to the root container. This method is run on the JavaFX application thread.
     */
    private void initializeUI() {
        root.getChildren().addAll(shieldImage, bossHealthBar, hint, scoreBoard);
        Platform.runLater(() -> {
            bossHealthBar.show();  // Show the boss health bar
            hint.show();  // Show the hint
            scoreBoard.show();  // Show the score board
        });
    }

    /**
     * Updates the position of the shield image relative to the boss's current position.
     *
     * @param boss the boss whose position is used to update the shield's position.
     */
    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY();
        shieldImage.setLayout(bossPositionX, bossPositionY);
    }

    /**
     * Shows the shield image on the UI.
     */
    public void showShield() {
        shieldImage.show();
    }

    /**
     * Hides the shield image from the UI.
     */
    public void hideShield() {
        shieldImage.hide();
    }

    /**
     * Updates the position of the boss health bar relative to the boss's current position.
     *
     * @param boss the boss whose position is used to update the health bar's position.
     */
    public void updateBossHealthPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 65;  // Adjusted Y position
        bossHealthBar.setLayout(bossPositionX, bossPositionY);
    }

    /**
     * Updates the health displayed on the boss's health bar.
     *
     * @param bossHealth the current health of the boss.
     */
    public void updateBossHealth(int bossHealth) {
        bossHealthBar.updateHealth(bossHealth);
    }

    /**
     * Hides the boss's health bar from the UI.
     */
    public void hideHealthBar() {
        bossHealthBar.hide();
    }

    /**
     * Updates the kills displayed on the scoreboard, reflecting the current and target kills.
     *
     * @param currentKills the current number of kills to display.
     * @param targetKills the target number of kills to display.
     */
    public void updateKills(int currentKills, int targetKills) {
        scoreBoard.updateKills(currentKills, targetKills);
    }
}