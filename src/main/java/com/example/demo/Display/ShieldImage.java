package com.example.demo.Display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class representing a shield image in the game.
 * The shield is displayed as an ImageView, and it can be shown or hidden when necessary.
 * This class allows the shield image to be positioned and controlled in terms of visibility.
 */
public class ShieldImage extends ImageView {

    /**
     * Path to the shield image file.
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";

    /**
     * The size of the shield image (both width and height).
     */
    public static final int SHIELD_SIZE = 100;

    /**
     * Constructor to create a ShieldImage instance.
     * It loads the image, sets its initial properties (size and visibility), and prepares it for display.
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
     * This method positions the shield image at the specified coordinates on the screen.
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
     * This method is typically called when the shield needs to be shown in the game.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();
    }

    /**
     * Hides the shield image.
     * This method is used to make the shield invisible when it is no longer needed.
     */
    public void hide() {
        this.setVisible(false);
    }
}