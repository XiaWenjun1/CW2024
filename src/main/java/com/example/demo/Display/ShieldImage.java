package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class representing a shield image in the game.
 * The shield is displayed as an ImageView, and it can be shown or hidden when necessary.
 */
public class ShieldImage extends ImageView {

    // Path to the shield image file
    private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";

    // Size of the shield image
    public static final int SHIELD_SIZE = 100;

    /**
     * Constructor to create a ShieldImage instance.
     * It loads the image and sets its initial properties (size and visibility).
     */
    public ShieldImage() {
        // Load the shield image from resources
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));

        // Initially hide the shield
        this.setVisible(false);

        // Set the size of the shield image
        this.setFitHeight(SHIELD_SIZE);
        this.setFitWidth(SHIELD_SIZE);
    }

    /**
     * Sets the layout position of the shield image.
     *
     * @param xPosition The X coordinate where the shield should be placed.
     * @param yPosition The Y coordinate where the shield should be placed.
     */
    public void setLayout(double xPosition, double yPosition) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Makes the shield image visible and brings it to the front.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();
    }

    /**
     * Hides the shield image.
     */
    public void hide() {
        this.setVisible(false);
    }

}