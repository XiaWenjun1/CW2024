package com.example.demo.Level;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelFour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLevelFour {

    private LevelFour levelFour;

    @BeforeEach
    public void setUp() {
        // Initialize the LevelFour instance with some screen size
        levelFour = new LevelFour(600, 800);
    }

    @Test
    public void testSpawnEnemyUnits() {
        // Spawn enemy units and check if the bosses are spawned when there are no enemies
        levelFour.spawnEnemyUnits();
        assertEquals(1, levelFour.getCurrentNumberOfEnemies(), "There should be 1 enemy initially, which is boss1.");

        // Simulate boss1 being destroyed and check if boss2 is spawned
        levelFour.getBoss1().isDestroyed();
        levelFour.spawnEnemyUnits();
        assertEquals(1, levelFour.getCurrentNumberOfEnemies(), "There should be 1 enemy, which is boss2.");

        // Simulate boss2 being destroyed and check if boss3 is spawned
        levelFour.getBoss2().isDestroyed();
        levelFour.spawnEnemyUnits();
        assertEquals(1, levelFour.getCurrentNumberOfEnemies(), "There should be 1 enemy, which is boss3.");
    }

    @Test
    public void testConstructor() {
        // Test the constructor to ensure LevelFour is correctly initialized
        assertNotNull(levelFour, "LevelFour instance should not be null");
        assertEquals(5, levelFour.getUser().getHealth(), "Player health should be initialized to 5");
        assertNotNull(levelFour.getBoss1(), "Boss1 should be initialized");
        assertNotNull(levelFour.getBoss2(), "Boss2 should be initialized");
        assertNotNull(levelFour.getBoss3(), "Boss3 should be initialized");
    }

    @Test
    public void testBossHealth() {
        // Test if the bosses' health values are correctly initialized
        assertEquals(25, levelFour.getBoss1().getHealth(), "Boss1 health should be initialized to 25");
        assertEquals(35, levelFour.getBoss2().getHealth(), "Boss2 health should be initialized to 35");
        assertEquals(45, levelFour.getBoss3().getHealth(), "Boss3 health should be initialized to 45");
    }

    @Test
    public void testCheckIfGameOver_whenPlayerDestroyed() {
        // Simulate the player being destroyed by setting the userDestroyed flag to true
        levelFour.getUser().destroy();  // Assuming there's a method to set the player's destroyed state
        levelFour.checkIfGameOver();

        // Check if the game ends correctly when the player is destroyed
        assertTrue(levelFour.isGameOver(), "The game should be over when the player is destroyed.");
    }

    @Test
    public void testInstantiateLevelView() {
        // Test the level view instantiation and assert it's the correct type
        LevelView levelView = levelFour.instantiateLevelView();

        // Assert that the level view is of type LevelViewLevelFour
        assertTrue(levelView instanceof LevelViewLevelFour, "The level view should be of type LevelViewLevelFour.");
    }
}