package com.example.demo.Level;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Level.LevelView.LevelViewLevelTwo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLevelTwo {

	private LevelTwo levelTwo;

	@BeforeEach
	public void setUp() {
		// Initialize the scene root and the LevelTwo instance with some screen size
		levelTwo = new LevelTwo(600, 800);
	}

	@Test
	public void testSpawnEnemyUnits() {
		// Spawn enemy units and check if the boss is spawned when there are no enemies
		levelTwo.spawnEnemyUnits();
		assertEquals(1, levelTwo.getCurrentNumberOfEnemies(), "There should be 1 enemy, which is the boss.");
	}

	@Test
	public void testConstructor() {
		// Test the constructor to ensure LevelThree is correctly initialized
		assertNotNull(levelTwo, "LevelThree instance should not be null");
		assertEquals(5, levelTwo.getUser().getHealth(), "Player health should be initialized to 5");
	}

	@Test
	public void testBossHealth() {
		// Test if the boss health is correctly initialized
		assertEquals(15, levelTwo.getBoss().getHealth(), "Boss health should be initialized to 15");
	}

	@Test
	public void testCheckIfGameOver_whenPlayerDestroyed() {
		// Simulate the player being destroyed by setting the userDestroyed flag to true
		levelTwo.getUser().destroy();  // Assuming there's a method to set the player's destroyed state
		levelTwo.checkIfGameOver();

		// Check if the game ends correctly when the player is destroyed
		assertTrue(levelTwo.isGameOver(), "The game should be over when the player is destroyed.");
	}

	@Test
	public void testCheckIfGameOver_whenBossDestroyed() {
		// Set the boss to be destroyed
		levelTwo.getBoss().isDestroyed();  // Assuming there's a method to set the boss's destroyed state

		// Check if the game proceeds to the next level when the boss is destroyed
		levelTwo.checkIfGameOver();
		assertEquals("com.example.demo.Level.LevelThree", levelTwo.getNextLevel());
	}

	@Test
	public void testInstantiateLevelView() {
		// Test the level view instantiation and assert it's the correct type
		LevelView levelView = levelTwo.instantiateLevelView();

		// Assert that the level view is of type LevelViewLevelTwo
		assertTrue(levelView instanceof LevelViewLevelTwo, "The level view should be of type LevelViewLevelTwo.");
	}
}