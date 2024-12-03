package com.example.demo.Level.LevelView;

import com.example.demo.Display.ScoreBoard;
import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Display.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the level view for Level 3, responsible for managing and displaying elements
 * such as the boss health bar, shield, scoreboard, and other level-specific UI elements.
 * <p>
 * This class is designed to manage and display all the UI elements for Level 3 of the game, including
 * the boss's health bar, shield image, hint, and a scoreboard that tracks the player's kills.
 * </p>
 */
public class LevelViewLevelThree extends LevelView {

    /**
     * The root container of the scene where the UI elements will be added.
     */
    private final Group root;

    /**
     * The shield image displayed for the boss when it is shielded.
     */
    private final ShieldImage shieldImage;

    /**
     * The health bar that shows the current health of the boss.
     */
    private final BossHealthBar bossHealthBar;

    /**
     * The hint displayed on the level, typically used to guide the player.
     */
    private final TargetLevel hint;

    /**
     * The scoreboard that shows the current number of kills and the target kills for the level.
     */
    private final ScoreBoard scoreBoard;

    /**
     * Constructs a LevelViewLevelThree instance with the specified root group, hearts to display,
     * the boss's health, initial kills, and target kills for the scoreboard.
     * <p>
     * This constructor initializes all the necessary UI components for the level, including the
     * shield image, boss health bar, hint, and scoreboard, and adds them to the root container of the scene.
     * </p>
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
     * hint, and score board to the root container. This method is run on the JavaFX application thread
     * to ensure that the UI updates happen on the correct thread.
     * <p>
     * The shield image, boss health bar, hint, and scoreboard are added to the root container, and their
     * visibility is updated using {@link Platform#runLater} to ensure the elements are displayed properly.
     * </p>
     */
    private void initializeUI() {
        root.getChildren().addAll(shieldImage, bossHealthBar, hint, scoreBoard);
        Platform.runLater(() -> {
            bossHealthBar.show();  // Show the boss health bar
            hint.show();  // Show the hint
            scoreBoard.show();  // Show the scoreboard
        });
    }

    /**
     * Updates the position of the shield image relative to the boss's current position.
     * <p>
     * This method ensures that the shield image follows the boss's movement on the screen.
     * </p>
     *
     * @param boss the boss whose position is used to update the shield's position.
     */
    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY();
        shieldImage.setLayout(bossPositionX, bossPositionY);
    }

    /**
     * Retrieves the shield image.
     *
     * @return the shield image
     */
    protected ShieldImage getShieldImage() {
        return shieldImage;
    }

    /**
     * Shows the shield image on the UI.
     * <p>
     * This method is used to make the shield image visible when the boss is shielded.
     * </p>
     */
    public void showShield() {
        shieldImage.show();
    }

    /**
     * Hides the shield image from the UI.
     * <p>
     * This method is used to hide the shield image when the boss is no longer shielded.
     * </p>
     */
    public void hideShield() {
        shieldImage.hide();
    }

    /**
     * Updates the position of the boss health bar relative to the boss's current position.
     * <p>
     * This method ensures that the health bar remains positioned correctly above the boss.
     * </p>
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
     * <p>
     * This method is used to update the health bar to reflect the current health of the boss.
     * </p>
     *
     * @param bossHealth the current health of the boss.
     */
    public void updateBossHealth(int bossHealth) {
        bossHealthBar.updateHealth(bossHealth);
    }

    /**
     * Hides the boss's health bar from the UI.
     * <p>
     * This method is used to hide the health bar, for example, when the boss is defeated or the level ends.
     * </p>
     */
    public void hideHealthBar() {
        bossHealthBar.hide();
    }

    /**
     * Retrieves the boss's health bar.
     * <p>
     * This method is used to access the boss's health bar, for example, to update or customize its appearance during gameplay.
     * </p>
     *
     * @return the boss's health bar {@link BossHealthBar} instance
     */
    protected BossHealthBar getBossHealthBar() {
        return bossHealthBar;
    }

    /**
     * Updates the kills displayed on the scoreboard, reflecting the current and target kills.
     * <p>
     * This method is used to update the kills count on the scoreboard and adjust it based on the
     * player's progress in the level.
     * </p>
     *
     * @param currentKills the current number of kills to display.
     * @param targetKills the target number of kills to display.
     */
    public void updateKills(int currentKills, int targetKills) {
        scoreBoard.updateKills(currentKills, targetKills);
    }

    /**
     * Returns the scoreboard associated with this level view.
     *
     * @return the scoreboard for Level 1.
     */
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}