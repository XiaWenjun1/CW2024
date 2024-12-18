package com.example.demo.Level.LevelView;

import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Display.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the level view for Level 2, responsible for managing and displaying elements
 * such as the boss health bar, shield, hint, and other level-specific UI elements.
 * This class handles the user interface (UI) components for the second level, such as the shield image,
 * the boss's health bar, and the hint. It also provides methods to update the positions of these elements
 * based on the boss's current position and updates to the boss's health.
 */
public class LevelViewLevelTwo extends LevelView {

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
     * Constructs a LevelViewLevelTwo instance with the specified root group, hearts to display,
     * and the boss's health for the health bar.
     * This constructor initializes the necessary UI components, such as the shield image, boss health bar,
     * and the hint, and adds them to the root container of the scene.
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
     * and hint to the root container. This method is run on the JavaFX application thread to ensure
     * that UI updates are performed on the correct thread.
     * The shield image, boss health bar, and hint are added to the root container, and their visibility
     * is updated using the {@link Platform#runLater} method to ensure UI elements are shown on the screen.
     */
    private void initializeUI() {
        root.getChildren().addAll(shieldImage, bossHealthBar, hint);
        Platform.runLater(() -> {
            bossHealthBar.hide();
            hint.show();
        });
    }

    /**
     * Updates the position of the shield image relative to the boss's current position.
     * This method ensures that the shield image follows the boss's movement on the screen.
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
     * This method is used to make the shield image visible when the boss is shielded.
     */
    public void showShield() {
        shieldImage.show();
    }

    /**
     * Hides the shield image from the UI.
     * This method is used to hide the shield image when the boss is no longer shielded.
     */
    public void hideShield() {
        shieldImage.hide();
    }

    /**
     * Updates the position of the boss health bar relative to the boss's current position.
     * This method ensures that the health bar remains positioned correctly above the boss.
     *
     * @param boss the boss whose position is used to update the health bar's position.
     */
    public void updateBossHealthPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 65;  // Adjusted Y position
        bossHealthBar.setLayout(bossPositionX, bossPositionY);
    }

    /**
     * Retrieves the boss's health bar.
     * This method is used to access the boss's health bar, for example, to update or customize its appearance during gameplay.
     *
     * @return the boss's health bar {@link BossHealthBar} instance
     */
    protected BossHealthBar getBossHealthBar() {
        return bossHealthBar;
    }

    /**
     * Shows the boss health bar on the UI.
     * This method is used to make the boss health bar visible.
     */
    public void showBossHealthBar() {
        bossHealthBar.show();
    }

    /**
     * Hides the boss's health bar from the UI.
     * This method is used to hide the health bar, for example, when the boss is defeated or the level ends.
     */
    public void hideBossHealthBar() {
        bossHealthBar.hide();
    }

    /**
     * Updates the health displayed on the boss's health bar.
     * This method is used to update the health bar to reflect the current health of the boss.
     *
     * @param bossHealth the current health of the boss.
     */
    public void updateBossHealth(int bossHealth) {
        bossHealthBar.updateHealth(bossHealth);
    }
}