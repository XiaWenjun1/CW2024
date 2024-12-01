package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A class that manages the display of hearts in the game.
 * It creates and maintains a container (HBox) that holds a number of heart images.
 * This class can also add or remove hearts dynamically based on game events.
 */
public class HeartDisplay {

	/**
	 * The path to the heart image file.
	 * This image is used to represent a life in the game.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/**
	 * The height of the heart images in pixels.
	 */
	private static final int HEART_HEIGHT = 50;

	/**
	 * The index used to remove the first heart from the container when a life is lost.
	 */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/**
	 * The container that holds the heart images.
	 * It is used to organize the hearts horizontally on the screen.
	 */
	private HBox container;

	/**
	 * The x-coordinate of the heart container's position.
	 */
	private double containerXPosition;

	/**
	 * The y-coordinate of the heart container's position.
	 */
	private double containerYPosition;

	/**
	 * The number of hearts to initially display.
	 */
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay object, initializing the heart container and the hearts to be displayed.
	 *
	 * @param xPosition the x-coordinate of the heart container
	 * @param yPosition the y-coordinate of the heart container
	 * @param heartsToDisplay the initial number of hearts to display
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container that holds the hearts (HBox) and sets its position.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes and displays the specified number of hearts (lives) in the container.
	 * Each heart is represented by an {@link ImageView} object, styled and sized appropriately.
	 * This method is typically called during the initialization of the game to set up the initial lives.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			addHeart();
		}
	}

	/**
	 * Removes the first heart from the container, representing the loss of a life.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Adds a heart (life) to the container, visually representing the gain of an extra life.
	 * The heart is displayed as an {@link ImageView} with a predefined size and aspect ratio.
	 * The heart image is loaded from the specified resource defined by {@code HEART_IMAGE_NAME}.
	 */
	public void addHeart() {
		ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * Gets the container holding the hearts.
	 *
	 * @return the HBox containing the heart images
	 */
	public HBox getContainer() {
		return container;
	}
}