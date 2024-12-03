package com.example.demo.Level;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLevelThree {
    private LevelThree levelThree;

    @BeforeEach
    public void setUp() {
        // Initialize LevelThree with a screen height and width
        levelThree = new LevelThree(600, 800);
    }

    @Test
    public void testConstructor() {
        // Test the constructor to ensure LevelThree is correctly initialized
        assertNotNull(levelThree, "LevelThree instance should not be null");
        assertEquals(5, levelThree.getUser().getHealth(), "Player health should be initialized to 5");
    }

    @Test
    public void testBossHealth() {
        // Test if the boss health is correctly initialized
        assertEquals(20, levelThree.getBoss().getHealth(), "Boss health should be initialized to 20");
    }

    @Test
    public void testEnemySpawn() {
        // Test enemy spawning functionality
        int initialEnemyCount = levelThree.getCurrentNumberOfEnemies();
        levelThree.spawnEnemyUnits();

        // Since enemy spawn is probabilistic, we verify by checking the number of enemies
        assertTrue(levelThree.getCurrentNumberOfEnemies() > initialEnemyCount, "Enemies should be spawned after calling spawnEnemyUnits");
    }

    @Test
    public void testBossSpawn() {
        // Test if the boss is spawned when the kill target is reached
        levelThree.spawnEnemyUnits();

        // Check if the boss is added to the scene
        assertTrue(levelThree.getRoot().getChildren().contains(levelThree.getBoss()), "Boss should be added to the scene");
    }

    @Test
    public void testCheckIfGameOverPlayerDestroyed() {
        levelThree.getUser().destroy();  // Simulate player destruction
        levelThree.checkIfGameOver();

        // Assuming loseGame() will stop the game and change a flag
        assertTrue(levelThree.isGameOver());
    }

    @Test
    public void testCheckIfGameOverKillTargetReachedAndBossDestroyed() {
        for (int i = 0; i < 30; i++) {
            levelThree.getUser().incrementKillCount();
        }
        levelThree.getBoss().isDestroyed();
        levelThree.checkIfGameOver();

        // Verify the game goes to the next level
        assertEquals("com.example.demo.Level.LevelFour", levelThree.getNextLevel());
    }
}