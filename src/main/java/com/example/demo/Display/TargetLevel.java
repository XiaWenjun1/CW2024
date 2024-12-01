package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Represents the target hint message displayed to the player during the game level.
 * This class displays a hint message to guide the player, such as the target to defeat a boss or complete an objective.
 */
public class TargetLevel extends Pane {

    // Default hint text and positioning
    /**
     * The default x-coordinate for positioning the hint label.
     * This value is used when no custom position is provided.
     */
    private static final double DEFAULT_HINT_X = 10;

    /**
     * The default y-coordinate for positioning the hint label.
     * This value is used when no custom position is provided.
     */
    private static final double DEFAULT_HINT_Y = 720;

    /**
     * The default hint text displayed to the player.
     * This message will appear when the TargetLevel is initialized with default settings.
     */
    private static final String DEFAULT_HINT_TEXT = "Target: Defeat the Boss!";

    // Style for the hint label
    /**
     * The style string applied to the hint label.
     * It defines the font size, color, and weight for the hint text.
     */
    private static final String HINT_STYLE = "-fx-font-size: 20px; -fx-text-fill: White; -fx-font-weight: bold";

    // Label to display the hint message
    /**
     * The Label that displays the hint message.
     * It is positioned on the screen and contains the text guiding the player.
     */
    private final Label hintLabel;

    /**
     * Default constructor using predefined text and position.
     * Initializes the TargetLevel with the default hint message and position.
     */
    public TargetLevel() {
        this(DEFAULT_HINT_TEXT, DEFAULT_HINT_X, DEFAULT_HINT_Y);
    }

    /**
     * Constructor allowing for custom hint text and position.
     * This constructor allows the developer to specify a custom hint message and position.
     *
     * @param hintText The text to be displayed as the hint.
     * @param x The x-coordinate for positioning the label.
     * @param y The y-coordinate for positioning the label.
     */
    public TargetLevel(String hintText, double x, double y) {
        // Initialize the hint label with the given text and position
        this.hintLabel = createHintLabel(hintText, x, y);
        // Add the label directly to the pane
        this.getChildren().addAll(hintLabel);
    }

    /**
     * Creates the label with the hint text and styles.
     * This method creates and styles the hint label to display the hint message.
     *
     * @param hintText The text to be displayed in the hint label.
     * @param x The x-position for the label.
     * @param y The y-position for the label.
     * @return A styled Label with the hint text.
     */
    private Label createHintLabel(String hintText, double x, double y) {
        Label label = new Label(hintText);
        label.setStyle(HINT_STYLE);   // Apply the predefined style to the label
        label.setLayoutX(x);          // Set the x-position of the label
        label.setLayoutY(y);          // Set the y-position of the label
        label.setVisible(false);      // Initially, the hint is hidden
        return label;
    }

    /**
     * Shows the hint label and brings it to the front of the game interface.
     * This method makes the hint label visible and ensures that it is displayed on top of other UI elements.
     */
    public void show() {
        hintLabel.setVisible(true); // Make the hint visible
        this.toFront();             // Bring the hint label to the front of other elements
    }
}