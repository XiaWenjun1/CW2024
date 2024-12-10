package com.example.demo.Level.LevelView;

import com.example.demo.Display.ScoreBoard;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LevelViewLevelOne.
 * This class verifies the initialization and functionality of the LevelViewLevelOne class.
 */
public class TestLevelViewLevelOne {

    private LevelViewLevelOne levelView;
    private Group root;

    @BeforeEach
    public void setUp() {
        root = new Group();
        levelView = new LevelViewLevelOne(root, 5, 0, 10);  // Hearts to display: 5, initial kills: 0, target kills: 10
    }

    @Test
    public void testScoreBoardInitialization() {
        // Access the scoreboard and verify its initial state
        ScoreBoard scoreBoard = levelView.getScoreBoard();
        assertNotNull(scoreBoard, "Scoreboard should be initialized.");
        assertEquals(0, scoreBoard.getCurrentKills(), "Initial kills should be 0.");
        assertEquals(10, scoreBoard.getTargetKills(), "Target kills should be 10.");
    }

    @Test
    public void testUpdateKills() {
        // Update the kills count and verify the update
        Platform.runLater(() -> {
            levelView.updateKills(5, 10);  // Update kills to 5
            ScoreBoard scoreBoard = levelView.getScoreBoard();
            assertEquals(5, scoreBoard.getCurrentKills(), "Kills should be updated to 5.");
            assertEquals(10, scoreBoard.getTargetKills(), "Target kills should remain 10.");
        });

        // Allow the UI thread to process the update
        try {
            Thread.sleep(100);  // Wait for the JavaFX application thread to process
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}