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

	// Path to the heart image file
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	// The height of the heart images
	private static final int HEART_HEIGHT = 50;

	// The index used to remove the first heart from the container
	private static final int INDEX_OF_FIRST_ITEM = 0;

	// The container that holds the heart images
	private HBox container;

	// The x and y positions of the heart container
	private double containerXPosition;
	private double containerYPosition;

	// The number of hearts to initially display
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
	 * Initializes the specified number of hearts and adds them to the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
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
	 * Adds a heart to the container, representing the addition of a life.
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