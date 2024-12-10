package com.example.demo.Level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLevelOne {

	private LevelOne levelOne;

	@BeforeEach
	public void setUp() {
		// Initialize the LevelOne instance with test screen dimensions.
		levelOne = new LevelOne(600, 800);  // example screen dimensions
	}

	@Test
	public void testLevelInitialization() {
		assertNotNull(levelOne);
		assertEquals(600, levelOne.getScreenHeight());
		assertEquals(800, levelOne.getScreenWidth());
	}

	@Test
	public void testInitializeFriendlyUnits() {
		levelOne.initializeFriendlyUnits();
		assertNotNull(levelOne.getUser());
		assertTrue(levelOne.getRoot().getChildren().contains(levelOne.getUser()));
	}

	@Test
	public void testConstructor() {
		// Test the constructor to ensure LevelThree is correctly initialized
		assertNotNull(levelOne, "LevelThree instance should not be null");
		assertEquals(5, levelOne.getUser().getHealth(), "Player health should be initialized to 5");
	}

	@Test
	public void testSpawnEnemyUnits() {
		levelOne.spawnEnemyUnits();
		int numberOfEnemies = levelOne.getCurrentNumberOfEnemies();
		assertTrue(numberOfEnemies <= 5);
	}

	@Test
	public void testCheckIfGameOverPlayerDestroyed() {
		levelOne.getUser().destroy();  // Simulate player destruction
		levelOne.checkIfGameOver();

		// Assuming loseGame() will stop the game and change a flag
		assertTrue(levelOne.isGameOver());
	}

	@Test
	public void testCheckIfGameOverKillTargetReached() {
		for (int i = 0; i < 10; i++) {
			levelOne.getUser().incrementKillCount();
		}
		levelOne.checkIfGameOver();

		// Verify the game goes to the next level
		assertEquals("com.example.demo.Level.LevelTwo", levelOne.getNextLevel());
	}

	@Test
	public void testInstantiateLevelView() {
		levelOne.instantiateLevelView();
		assertNotNull(levelOne.getLevelView());
	}
}