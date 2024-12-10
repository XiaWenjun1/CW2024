package com.example.demo.Level.LevelView;

import com.example.demo.Actor.Plane.Boss.Boss;
import com.example.demo.Display.ScoreBoard;
import com.example.demo.Display.ShieldImage;
import com.example.demo.Display.TargetLevel;
import com.example.demo.Display.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestLevelViewLevelThree {

    private LevelViewLevelThree levelView;
    private Group root;
    private Boss boss;
    private ShieldImage shieldImage;
    private BossHealthBar bossHealthBar;
    private TargetLevel hint;

    @BeforeEach
    public void setUp() {
        // Initialize the root Group and the Boss object
        root = new Group();
        boss = new Boss(100); // Assuming Boss constructor takes position as parameters
        shieldImage = new ShieldImage();
        bossHealthBar = new BossHealthBar(100);
        hint = new TargetLevel();

        // Create an instance of LevelViewLevelTwo
        levelView = new LevelViewLevelThree(root, 3, 100, 0, 30);
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

        // Verify that the shield image's position is updated correctly
        assertEquals(50 + boss.getTranslateX(), levelView.getShieldImage().getLayoutX());
        assertEquals(100 + boss.getTranslateY(), levelView.getShieldImage().getLayoutY());
    }

    @Test
    void hideShieldMakesShieldInvisible() {
        Platform.runLater(() -> {
            levelView.showShield();
            levelView.hideShield();
            assertFalse(levelView.getShieldImage().isVisible());
        });
    }

    @Test
    public void testUpdateBossHealthPosition() {
        // Set position for the boss
        boss.setLayoutX(100.0);
        boss.setLayoutY(200.0);
        // Update the health bar position based on the boss's position
        levelView.updateBossHealthPosition(boss);

        // Verify that the boss health bar's position is updated correctly
        assertEquals(100 + boss.getTranslateX(), levelView.getBossHealthBar().getLayoutX());
        assertEquals(200 + boss.getTranslateY() + 65, levelView.getBossHealthBar().getLayoutY());
    }

    @Test
    public void testScoreBoardInitialization() {
        // Access the scoreboard and verify its initial state
        ScoreBoard scoreBoard = levelView.getScoreBoard();
        assertNotNull(scoreBoard, "Scoreboard should be initialized.");
        assertEquals(0, scoreBoard.getCurrentKills(), "Initial kills should be 0.");
        assertEquals(30, scoreBoard.getTargetKills(), "Target kills should be 30.");
    }

    @Test
    public void testUpdateKills() {
        // Update the kills count and verify the update
        Platform.runLater(() -> {
            levelView.updateKills(5, 30);  // Update kills to 5
            ScoreBoard scoreBoard = levelView.getScoreBoard();
            assertEquals(5, scoreBoard.getCurrentKills(), "Kills should be updated to 5.");
            assertEquals(30, scoreBoard.getTargetKills(), "Target kills should remain 30.");
        });

        // Allow the UI thread to process the update
        try {
            Thread.sleep(100);  // Wait for the JavaFX application thread to process
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}