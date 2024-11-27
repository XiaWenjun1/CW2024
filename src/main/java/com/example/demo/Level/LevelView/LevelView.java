package com.example.demo.Level.LevelView;

import com.example.demo.Display.GameOverImage;
import com.example.demo.Display.HeartDisplay;
import com.example.demo.Display.WinImage;
import javafx.scene.Group;

/**
 * Represents the view layer of the level, responsible for displaying hearts, win and game over images,
 * and updating the UI as the game progresses.
 * <p>
 * This class manages and displays the UI elements related to the game's status, including heart icons
 * (representing the player's health), the win image (shown when the player wins), and the game over image
 * (shown when the player loses). It also provides methods to update and remove hearts based on the player's health.
 * </p>
 */
public class LevelView {

	/**
	 * The X position of the heart display on the screen.
	 * This constant defines where the heart display (representing the player's health) will be positioned horizontally.
	 */
	private static final double HEART_DISPLAY_X_POSITION = 5;

	/**
	 * The Y position of the heart display on the screen.
	 * This constant defines where the heart display (representing the player's health) will be positioned vertically.
	 */
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	/**
	 * The X position of the win image on the screen.
	 * This constant defines where the win image will be positioned horizontally when the player wins.
	 */
	private static final int WIN_IMAGE_X_POSITION = 355;

	/**
	 * The Y position of the win image on the screen.
	 * This constant defines where the win image will be positioned vertically when the player wins.
	 */
	private static final int WIN_IMAGE_Y_POSITION = 175;

	/**
	 * The X position of the game over image on the screen.
	 * This constant defines where the game over image will be positioned horizontally when the player loses.
	 */
	private static final int LOSS_SCREEN_X_POSITION = -160;

	/**
	 * The Y position of the game over image on the screen.
	 * This constant defines where the game over image will be positioned vertically when the player loses.
	 */
	private static final int LOSS_SCREEN_Y_POSISITION = -375;

	/**
	 * The root container of the scene where the UI elements will be added.
	 * This is a {@link Group} that holds all the UI elements for the game level.
	 */
	private final Group root;

	/**
	 * The image displayed when the player wins the level.
	 * This represents the victory condition and shows an image indicating that the player has won the game.
	 */
	private final WinImage winImage;

	/**
	 * The image displayed when the player loses the game.
	 * This represents the game over condition and shows an image indicating that the player has lost the game.
	 */
	private final GameOverImage gameOverImage;

	/**
	 * The display for the player's hearts, representing the player's health.
	 * This object manages the heart icons that represent the player's current health.
	 */
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a LevelView instance with the specified root group and number of hearts to display.
	 * This view manages the display of heart icons, win image, and game over image.
	 * <p>
	 * The constructor initializes the heart display, win image, and game over image based on the provided
	 * root container and the number of hearts the player starts with.
	 * </p>
	 *
	 * @param root the root container of the scene where the UI elements will be added.
	 * @param heartsToDisplay the initial number of hearts to be displayed.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}

	/**
	 * Displays the heart display on the screen, showing the current number of hearts.
	 * <p>
	 * This method adds the heart display to the root container so that the player's health (represented
	 * by heart icons) is visible in the UI.
	 * </p>
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the win image on the screen when the player wins the level.
	 * <p>
	 * This method adds the win image to the root container and calls the method to show the win image.
	 * </p>
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
	 * Displays the game over image on the screen when the player loses the game.
	 * <p>
	 * This method adds the game over image to the root container to signal the end of the game.
	 * </p>
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Removes hearts from the display when the player's health decreases.
	 * <p>
	 * This method adjusts the heart display by removing heart icons based on the remaining number of hearts.
	 * </p>
	 *
	 * @param heartsRemaining the current number of hearts to display after removal.
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
	 * <p>
	 * This method adjusts the heart display by adding heart icons based on the remaining number of hearts.
	 * </p>
	 *
	 * @param heartsRemaining the current number of hearts to display after addition.
	 */
	public void addHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		int heartsToAdd = heartsRemaining - currentNumberOfHearts;
		for (int i = 0; i < heartsToAdd; i++) {
			heartDisplay.addHeart();
		}
	}
}