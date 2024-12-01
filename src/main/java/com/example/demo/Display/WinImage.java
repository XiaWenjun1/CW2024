package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents the "You Win" image that appears when the player wins the game.
 * The image is initially hidden and can be shown when the player completes a level or achieves victory.
 * This class manages the display of the "You Win" image, positioning it on the screen and controlling its visibility.
 */
public class WinImage extends ImageView {

	/**
	 * The X position of the win image on the screen.
	 * This constant defines where the win image will be positioned horizontally when the player wins.
	 */
	private static final int X_POSITION = 355;

	/**
	 * The Y position of the win image on the screen.
	 * This constant defines where the win image will be positioned vertically when the player wins.
	 */
	private static final int Y_POSITION = 175;

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
	 * The path to the "You Win" image file.
	 * This is used to load the image resource for display when the player wins.
	 */
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	/**
	 * Constructs a {@link WinImage} instance and initializes the "You Win" image.
	 * The image is loaded from the resources, initially set to be invisible, and positioned on the screen at the specified coordinates.
	 * The dimensions of the image are also set at this stage.
	 */
	public WinImage() {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(X_POSITION);
		this.setLayoutY(Y_POSITION);
	}
}