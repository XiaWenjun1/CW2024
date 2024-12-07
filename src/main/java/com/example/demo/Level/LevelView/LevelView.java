package com.example.demo.Level.LevelView;

import com.example.demo.Display.GameOverImage;
import com.example.demo.Display.HeartDisplay;
import com.example.demo.Display.WinImage;
import com.example.demo.Level.LevelManager.AudioManager;
import javafx.scene.Group;

/**
 * Represents the view layer of the level in the game, responsible for managing and displaying various UI elements
 * such as heart icons (representing the player's health), win and game over images, and updating the UI as the game progresses.
 * This class is responsible for displaying important game status elements, including:
 * - Heart icons that represent the player's current health.
 * - A win image displayed when the player successfully completes the level.
 * - A game over image shown when the player loses the game.
 * It also provides methods to dynamically update the UI elements, such as adding or removing hearts based on the player's health.
 */
public class LevelView {

	/**
	 * The root container of the scene where the UI elements will be added.
	 * This is a {@link Group} that holds all the UI elements for the game level.
	 */
	private final Group root;

	/**
	 * The image displayed when the player wins the level.
	 * This image represents the victory condition and indicates that the player has won the game.
	 */
	private final WinImage winImage;

	/**
	 * The image displayed when the player loses the game.
	 * This image represents the game over condition and indicates that the player has lost the game.
	 */
	private final GameOverImage gameOverImage;

	/**
	 * The display for the player's hearts, representing the player's health.
	 * This object manages the heart icons that represent the player's current health status.
	 */
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a LevelView instance with the specified root group and number of hearts to display.
	 * This constructor initializes the heart display, win image, and game over image based on the provided
	 * root container and the number of hearts the player starts with.
	 *
	 * @param root the root container of the scene where the UI elements will be added.
	 * @param heartsToDisplay the initial number of hearts to be displayed, representing the player's health.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(heartsToDisplay);
		this.winImage = new WinImage();
		this.gameOverImage = new GameOverImage();
	}

	/**
	 * Displays the heart display on the screen, showing the current number of hearts.
	 * This method adds the heart display to the root container so that the player's health (represented
	 * by heart icons) is visible in the UI.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen when the player wins the game.
	 * This method adds the win image to the root container to signal the end of the game.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
	}

	/**
	 * Displays the game over image on the screen when the player loses the game.
	 * This method adds the game over image to the root container to signal the end of the game.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Updates the heart display when the player's health decreases.
	 * This method removes heart icons from the display to reflect the player's current health
	 * and plays an audio cue for each heart removed.
	 *
	 * @param heartsRemaining the current number of hearts to display, representing the player's remaining health.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		int heartsToRemove = currentNumberOfHearts - heartsRemaining;
		for (int i = 0; i < heartsToRemove; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Adds hearts to the display when the player's health increases.
	 * This method adjusts the heart display by adding heart icons based on the remaining number of hearts.
	 * @param heartsRemaining the current number of hearts to display after addition.
	 *                         This represents the player's current health.
	 */
	public void addHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		int heartsToAdd = heartsRemaining - currentNumberOfHearts;
		for (int i = 0; i < heartsToAdd; i++) {
			heartDisplay.addHeart();
		}
	}

	/**
	 * Returns the HeartDisplay object.
	 *
	 * @return the HeartDisplay instance.
	 */
	public HeartDisplay getHeartDisplay() {
		return heartDisplay;
	}

	/**
	 * Returns the WinImage object.
	 *
	 * @return the WinImage instance.
	 */
	public WinImage getWinImage() {
		return winImage;
	}

	/**
	 * Returns the GameOverImage object.
	 *
	 * @return the GameOverImage instance.
	 */
	public GameOverImage getGameOverImage() {
		return gameOverImage;
	}
}