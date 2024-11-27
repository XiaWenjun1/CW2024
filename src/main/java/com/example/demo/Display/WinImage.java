package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents the "You Win" image that appears when the player wins the game.
 * The image is initially hidden and can be shown when the player completes a level or achieves victory.
 */
public class WinImage extends ImageView {

	// The file path for the "You Win" image
	/**
	 * The path to the "You Win" image file.
	 * This is used to load the image resource for display when the player wins.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	// The dimensions for the "You Win" image
	/**
	 * The height of the "You Win" image.
	 * This defines how tall the image will be displayed on the screen.
	 */
	private static final int HEIGHT = 500;

	/**
	 * The width of the "You Win" image.
	 * This defines how wide the image will be displayed on the screen.
	 */
	private static final int WIDTH = 600;

	/**
	 * Constructor to initialize the WinImage at a specific position on the screen.
	 * The image is initially invisible and is positioned at the specified coordinates.
	 *
	 * @param xPosition The x-coordinate of the top-left corner of the image on the screen.
	 * @param yPosition The y-coordinate of the top-left corner of the image on the screen.
	 */
	public WinImage(double xPosition, double yPosition) {
		// Load the image from the resources folder
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));

		// Initially set the image to be invisible
		this.setVisible(false);

		// Set the dimensions of the image
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);

		// Set the position of the image on the screen
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Makes the "You Win" image visible on the screen.
	 * This method is called to display the image when the player wins the game.
	 */
	public void showWinImage() {
		// Set the image to be visible
		this.setVisible(true);
	}
}