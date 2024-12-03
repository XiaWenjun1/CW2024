package com.example.demo.Level.LevelView;

import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Display.BossHealthBar;
import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LevelViewLevelFour} class without inheriting ApplicationTest.
 */
public class TestLevelViewLevelFour {

    private LevelViewLevelFour levelView;
    private ShieldImage shieldImage;
    private BossHealthBar bossHealthBar;
    private TargetLevel hint;
    private Group root;
    private Boss boss;

    @BeforeEach
    public void setUp() {
        // Initialize the root Group and the Boss object
        root = new Group();
        boss = new Boss(100); // Assuming Boss constructor takes position as parameters
        shieldImage = new ShieldImage();
        bossHealthBar = new BossHealthBar(100);
        hint = new TargetLevel();
        levelView = new LevelViewLevelFour(root, 5);
    }

    @Test
    public void testInitializeUI() {
        // Run the UI setup on the JavaFX application thread
        Platform.runLater(() -> {
            // Ensure that the UI components are initialized and added to root
            root.getChildren().add(shieldImage);  // Ensure shieldImage is added to root
            root.getChildren().add(bossHealthBar); // Ensure bossHealthBar is added to root
            root.getChildren().add(hint);  // Ensure hint is added to root

            // Check if the UI components are initialized properly
            assertTrue(root.getChildren().contains(shieldImage), "Shield image should be added to root");
            assertTrue(root.getChildren().contains(bossHealthBar), "Boss health bar should be added to root");
            assertTrue(root.getChildren().contains(hint), "Hint should be added to root");

            // Verify if UI components are visible after initialization
            assertTrue(bossHealthBar.isVisible(), "Boss health bar should be visible");
            assertTrue(hint.isVisible(), "Hint should be visible");
        });
    }

    @Test
    public void testUpdateShieldPosition() {
        // Set position for the boss
        boss.setLayoutX(50.0);
        boss.setLayoutY(100.0);

        // Update the shield position based on the boss's position
        levelView.updateShieldPosition(boss);

        // Ensure that the shield image's position is updated correctly
        // Assuming getLayoutX() and getLayoutY() return the actual positions of the shield
        assertEquals(50 + boss.getTranslateX() + 90, levelView.getShieldImage().getLayoutX());
        assertEquals(100 + boss.getTranslateY() + 80, levelView.getShieldImage().getLayoutY());
    }

    @Test
    void hideShieldMakesShieldInvisible() {
        Platform.runLater(() -> {
            levelView.showShield();
            levelView.hideShield();
            assertFalse(levelView.getShieldImage().isVisible());
        });
    }
}