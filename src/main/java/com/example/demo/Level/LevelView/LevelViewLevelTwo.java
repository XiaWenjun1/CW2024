package com.example.demo.Level.LevelView;

import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Actor.Boss.ParentBoss.Boss;
import com.example.demo.Actor.Boss.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the level view for Level 2, responsible for managing and displaying elements
 * such as the boss health bar, shield, hint, and other level-specific UI elements.
 */
public class LevelViewLevelTwo extends LevelView {

    private final Group root;
    private final ShieldImage shieldImage;
    private final BossHealthBar bossHealthBar;
    private final TargetLevel hint;

    /**
     * Constructs a LevelViewLevelTwo instance with the specified root group, hearts to display,
     * and the boss's health for the health bar.
     *
     * @param root the root container of the scene where the UI elements will be added.
     * @param heartsToDisplay the initial number of hearts to display on the UI.
     * @param bossHealth the health of the boss to be displayed in the health bar.
     */
    public LevelViewLevelTwo(Group root, int heartsToDisplay, int bossHealth) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthBar = new BossHealthBar(bossHealth);
        this.hint = new TargetLevel();

        initializeUI();
    }

    /**
     * Initializes the user interface for Level 2 by adding the shield image, boss health bar,
     * and hint to the root container. This method is run on the JavaFX application thread.
     */
    private void initializeUI() {
        root.getChildren().addAll(shieldImage, bossHealthBar, hint);
        Platform.runLater(() -> {
            bossHealthBar.show();  // Show the boss health bar
            hint.show();  // Show the hint
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
}