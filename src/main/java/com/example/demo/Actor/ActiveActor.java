package com.example.demo.Actor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class representing an active actor in the game.
 * This class extends {@link ImageView} to allow actors to have images and positions.
 * It provides basic functionality for movement and updating images for subclasses.
 */
public abstract class ActiveActor extends ImageView {
	/**
	 * Constant for the image location path.
	 * This path is used to locate image files within the application resources.
	 * All game images should be placed in this directory.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an ActiveActor with the specified image and position.
	 *
	 * @param imageName The name of the image to be displayed for the actor.
	 * @param imageWidth The width of the actor's image.
	 * @param imageHeight The height of the actor's image.
	 * @param initialXPos The initial X position of the actor.
	 * @param initialYPos The initial Y position of the actor.
	 */
	public ActiveActor(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		// Set the image for the actor
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		// Set the initial position and size for the actor
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitWidth(imageWidth);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true); // Preserve the aspect ratio of the image
	}

	/**
	 * Abstract method to update the position of the actor.
	 * This method must be implemented by subclasses to update the actor's position.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified distance.
	 *
	 * @param horizontalMove The distance to move the actor horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified distance.
	 *
	 * @param verticalMove The distance to move the actor vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	/**
	 * Sets a new image for the actor.
	 *
	 * @param imageName The name of the new image to set for the actor.
	 */
	protected void setImage(String imageName) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
	}
}