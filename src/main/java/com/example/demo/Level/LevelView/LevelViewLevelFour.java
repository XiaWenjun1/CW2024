package com.example.demo.Level.LevelView;

import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Display.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the level view for Level 4, responsible for managing and displaying elements
 * such as the shield image, boss health bars, and other level-specific UI elements.
 * <p>
 * This class is designed to manage and display the UI elements for Level 4 of the game,
 * which includes multiple bosses with their corresponding health bars and a shield image
 * that can be toggled depending on the status of the bosses.
 * </p>
 */
public class LevelViewLevelFour extends LevelView {

    /**
     * The root container of the scene where the UI elements will be added.
     */
    private final Group root;

    /**
     * The shield image that is displayed when a boss has a shield.
     */
    private final ShieldImage shieldImage;

    /**
     * A map that stores the relationship between each boss and its corresponding health bar.
     */
    private final Map<Boss, BossHealthBar> bossHealthBars;

    /**
     * The hint that provides additional guidance to the player.
     */
    private final TargetLevel hint;

    /**
     * Constructs a LevelViewLevelFour instance with the specified root group and number of hearts to display.
     * This level view manages additional UI elements such as shield images and boss health bars.
     * <p>
     * The constructor initializes the shield image, boss health bars map, and the hint, and sets up the UI.
     * </p>
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
     * <p>
     * The hint and shield image are added to the root container, and their visibility is updated using {@link Platform#runLater}
     * to ensure the elements are displayed properly after initialization.
     * </p>
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
     * <p>
     * This method ensures that each boss has its own health bar, and it updates the health bar whenever the boss's health changes.
     * </p>
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
     * <p>
     * This method adjusts the Y position of the health bar to ensure it is placed correctly below the boss.
     * </p>
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
     * <p>
     * This method adjusts the shield's position based on the boss's position, ensuring that the shield is
     * correctly aligned with the boss's location.
     * </p>
     *
     * @param boss the boss whose shield position should be updated.
     */
    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX() + 90;
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 80;
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
     * Displays the shield image, typically used to indicate that a boss has a shield.
     * <p>
     * This method is called when the boss gains a shield or when the shield needs to be shown on the UI.
     * </p>
     */
    public void showShield() {
        shieldImage.show();
    }

    /**
     * Hides the shield image, typically used when the boss no longer has a shield.
     * <p>
     * This method is called when the shield needs to be removed from the UI.
     * </p>
     */
    public void hideShield() {
        shieldImage.hide();
    }

    /**
     * Hides the health bar of the specified boss.
     * <p>
     * This method is called when the health bar should be removed from the UI, such as when the boss is defeated.
     * </p>
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
     * <p>
     * This method is called when the health bar should be shown for a specific boss, typically when the boss is active.
     * </p>
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