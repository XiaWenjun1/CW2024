package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A class that manages the display of hearts in the game.
 * It creates and maintains a container (HBox) that holds a number of heart images.
 * This class can also add or remove hearts dynamically based on game events such as the player losing or gaining health.
 * <p>
 * Each heart represents a life, and the heart display is updated whenever the player's health changes.
 * </p>
 */
public class HeartDisplay {

	/**
	 * The X position of the heart display on the screen.
	 * This constant defines where the heart display (representing the player's health) will be positioned horizontally.
	 */
	private static final double DISPLAY_X_POSITION = 5;

	/**
	 * The Y position of the heart display on the screen.
	 * This constant defines where the heart display (representing the player's health) will be positioned vertically.
	 */
	private static final double DISPLAY_Y_POSITION = 25;

	/**
	 * The path to the heart image file.
	 * This image is used to represent a life in the game.
	 */
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";

	/**
	 * The height of the heart images in pixels.
	 * This constant defines the size of each heart image.
	 */
	private static final int HEART_HEIGHT = 50;

	/**
	 * The index used to remove the first heart from the container when a life is lost.
	 * The hearts are stored in a container, and the first heart is removed to represent the loss of a life.
	 */
	private static final int INDEX_OF_FIRST_ITEM = 0;

	/**
	 * The container that holds the heart images.
	 * It is an {@link HBox} layout used to organize the hearts horizontally on the screen.
	 */
	private HBox container;

	/**
	 * The x-coordinate of the heart container's position.
	 * This determines where the container will be displayed on the screen horizontally.
	 */
	private double containerXPosition;

	/**
	 * The y-coordinate of the heart container's position.
	 * This determines where the container will be displayed on the screen vertically.
	 */
	private double containerYPosition;

	/**
	 * The number of hearts to initially display.
	 * This value represents the initial number of lives the player has when starting the game.
	 */
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@link HeartDisplay} with the specified number of hearts.
	 * <p>
	 * The constructor initializes the heart container and the hearts based on the initial number of hearts to display.
	 * </p>
	 *
	 * @param heartsToDisplay the number of hearts (lives) to display at the start of the game
	 */
	public HeartDisplay(int heartsToDisplay) {
		this.containerXPosition = DISPLAY_X_POSITION;
		this.containerYPosition = DISPLAY_Y_POSITION;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container (HBox) that holds the hearts and sets its position on the screen.
	 * This method is called when the {@link HeartDisplay} object is created to prepare the container for displaying hearts.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes and displays the specified number of hearts (lives) in the container.
	 * Each heart is represented by an {@link ImageView} object, which is added to the container.
	 * This method is typically called during the initialization of the game to set up the player's initial lives.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			addHeart();
		}
	}

	/**
	 * Removes the first heart from the container, representing the loss of a life.
	 * <p>
	 * When the player's health decreases, this method removes the first heart (the left-most one) from the container.
	 * </p>
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
		}
	}

	/**
	 * Adds a heart (life) to the container, visually representing the gain of an extra life.
	 * <p>
	 * This method adds a new heart as an {@link ImageView} to the container. The heart image is loaded from the resources
	 * and displayed with the predefined size and aspect ratio.
	 * </p>
	 */
	public void addHeart() {
		ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * Gets the container holding the heart images.
	 * This container is an {@link HBox} that contains all the heart images representing the player's current health.
	 *
	 * @return the HBox containing the heart images
	 */
	public HBox getContainer() {
		return container;
	}
}