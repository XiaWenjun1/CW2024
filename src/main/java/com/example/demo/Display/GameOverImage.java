package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A custom class that displays a "Game Over" image on the screen.
 * This class extends `ImageView` and positions the image at a specified (x, y) coordinate.
 * It is typically used to show a "Game Over" screen when the game ends.
 * The image is loaded from the resources directory and displayed at a fixed position on the screen.
 * The image will also be resized to a fixed width and height to fit the screen layout.
 */
public class GameOverImage extends ImageView {

	/**
	 * The X position of the "Game Over" image on the screen.
	 * This constant defines the horizontal position where the image will be placed when the game ends.
	 */
	private static final int X_POSITION = 355;

	/**
	 * The Y position of the "Game Over" image on the screen.
	 * This constant defines the vertical position where the image will be placed when the game ends.
	 */
	private static final int Y_POSITION = 140;

	/**
	 * The height to which the "Game Over" image will be resized.
	 * This constant defines the height of the image displayed on the screen.
	 */
	private static final int HEIGHT = 500;

	/**
	 * The width to which the "Game Over" image will be resized.
	 * This constant defines the width of the image displayed on the screen.
	 */
	private static final int WIDTH = 600;

	/**
	 * The path to the "Game Over" image file.
	 * This image is displayed when the game ends, indicating that the player has lost.
	 * The image is loaded from the resources directory of the application.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Constructs a `GameOverImage` object.
	 * This constructor loads the "Game Over" image, sets its position on the screen,
	 * and resizes it to the predefined width and height.
	 */
	public GameOverImage() {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		setLayoutX(X_POSITION);
		setLayoutY(Y_POSITION);
		setFitHeight(HEIGHT);
		setFitWidth(WIDTH);
	}
}