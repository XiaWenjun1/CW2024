package com.example.demo.Level.LevelView;

import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLevelView {

	private LevelView levelView;
	private Group root;

	@BeforeEach
	void setUp() {
		// Initialize the root group and the LevelView with 5 hearts to be displayed
		root = new Group();
		levelView = new LevelView(root, 5);
	}

	@Test
	void testShowHeartDisplay() {
		levelView.showHeartDisplay();
		assertEquals(5, levelView.getHeartDisplay().getContainer().getChildren().size(), "There should be 5 hearts initially.");
	}

	@Test
	void testAddHearts() {
		levelView.showHeartDisplay();
		levelView.addHearts(7);
		assertEquals(7, levelView.getHeartDisplay().getContainer().getChildren().size(), "There should be 7 hearts after adding.");
	}

	@Test
	void testRemoveHearts() {
		levelView.showHeartDisplay();
		levelView.removeHearts(3);
		assertEquals(3, levelView.getHeartDisplay().getContainer().getChildren().size(), "There should be 3 hearts after removing.");
	}

	@Test
	void testShowWinImage() {
		levelView.showWinImage();
		assertTrue(root.getChildren().contains(levelView.getWinImage()), "The win image should be displayed in the root group.");
	}

	@Test
	void testShowGameOverImage() {
		levelView.showGameOverImage();
		assertTrue(root.getChildren().contains(levelView.getGameOverImage()), "The game over image should be displayed in the root group.");
	}

	@Test
	void testDynamicHeartDisplay() {
		levelView.showHeartDisplay();
		levelView.removeHearts(3);
		assertEquals(3, levelView.getHeartDisplay().getContainer().getChildren().size(), "The heart display should have 3 hearts after removal.");

		levelView.addHearts(5);
		assertEquals(5, levelView.getHeartDisplay().getContainer().getChildren().size(), "The heart display should have 5 hearts after addition.");
	}
}