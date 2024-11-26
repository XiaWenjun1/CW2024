package com.example.demo.Level.LevelView;

import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Actor.Boss.ParentBoss.Boss;
import com.example.demo.Actor.Boss.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the level view for Level 4, responsible for managing and displaying elements
 * such as the shield image, boss health bars, and other level-specific UI elements.
 */
public class LevelViewLevelFour extends LevelView {

    private final Group root;
    private final ShieldImage shieldImage;
    private final Map<Boss, BossHealthBar> bossHealthBars;  // Stores the mapping between bosses and their corresponding health bars
    private final TargetLevel hint;

    /**
     * Constructs a LevelViewLevelFour instance with the specified root group and number of hearts to display.
     * This level view manages additional UI elements such as shield images and boss health bars.
     *
     * @param root the root container of the scene where the UI elements will be added.
     * @param heartsToDisplay the initial number of hearts to display on the UI.
     */
    public LevelViewLevelFour(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthBars = new HashMap<>();
        this.hint = new TargetLevel();

        initializeUI();  // Initialize other UI elements (excluding health bars)
    }

    /**
     * Initializes the user interface for the level, adding the shield image and hint to the root.
     * This method runs on the JavaFX application thread to ensure UI updates are executed correctly.
     */
    private void initializeUI() {
        root.getChildren().addAll(shieldImage, hint);
        Platform.runLater(() -> {
            hint.show();  // Show the hint after UI initialization
        });
    }

    /**
     * Updates the health of the specified boss. If the boss does not yet have a health bar, one is created.
     * The health bar is updated to reflect the current health of the boss.
     *
     * @param bossHealth the current health of the boss.
     * @param boss the boss whose health bar should be updated.
     */
    public void updateBossHealth(int bossHealth, Boss boss) {
        if (!bossHealthBars.containsKey(boss)) {
            // If the health bar does not exist, create and add it to the UI
            BossHealthBar healthBar = new BossHealthBar(bossHealth);
            bossHealthBars.put(boss, healthBar);
            root.getChildren().add(healthBar);
        }

        // Update the health of the existing health bar
        BossHealthBar healthBar = bossHealthBars.get(boss);
        Platform.runLater(() -> {
            healthBar.updateHealth(bossHealth);
        });
    }

    /**
     * Updates the position of the health bar for the specified boss based on the boss's position.
     * The health bar is positioned slightly below the boss.
     *
     * @param boss the boss whose health bar position should be updated.
     */
    public void updateBossHealthPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 235;

        if (bossHealthBars.containsKey(boss)) {
            BossHealthBar healthBar = bossHealthBars.get(boss);
            healthBar.setLayout(bossPositionX, bossPositionY);
        }
    }

    /**
     * Updates the position of the shield image relative to the position of the specified boss.
     * The shield is positioned slightly to the right and above the boss.
     *
     * @param boss the boss whose shield position should be updated.
     */
    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX() + 90;
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 80;
        shieldImage.setLayout(bossPositionX, bossPositionY);
    }

    /**
     * Displays the shield image, typically used to indicate that a boss has a shield.
     */
    public void showShield() {
        shieldImage.show();
    }

    /**
     * Hides the shield image, typically used when the boss no longer has a shield.
     */
    public void hideShield() {
        shieldImage.hide();
    }

    /**
     * Hides the health bar of the specified boss.
     *
     * @param boss the boss whose health bar should be hidden.
     */
    public void hideBossHealthBar(Boss boss) {
        if (bossHealthBars.containsKey(boss)) {
            BossHealthBar healthBar = bossHealthBars.get(boss);
            healthBar.hide();
        }
    }

    /**
     * Displays the health bar for the specified boss.
     *
     * @param boss the boss whose health bar should be shown.
     */
    public void showBossHealthBar(Boss boss) {
        if (bossHealthBars.containsKey(boss)) {
            BossHealthBar healthBar = bossHealthBars.get(boss);
            healthBar.show();
        }
    }
}