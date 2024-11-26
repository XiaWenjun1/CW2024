package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A custom class for displaying the "Game Over" image on the screen.
 * This class extends `ImageView` and positions the image at a given (x, y) coordinate.
 */
public class GameOverImage extends ImageView {

	// The path to the Game Over image file
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a new GameOverImage and positions it at the specified coordinates.
	 *
	 * @param xPosition the x-coordinate where the Game Over image will be displayed
	 * @param yPosition the y-coordinate where the Game Over image will be displayed
	 */
	public GameOverImage(double xPosition, double yPosition) {
		// Load the image from the resources and set it as the image for this ImageView
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));

		// Set the layout position of the image on the screen
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}