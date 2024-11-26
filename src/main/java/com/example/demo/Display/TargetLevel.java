package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Represents the target hint message displayed to the player during the game level.
 */
public class TargetLevel extends Pane {

    // Default hint text and positioning
    private static final double DEFAULT_HINT_X = 10;
    private static final double DEFAULT_HINT_Y = 720;
    private static final String DEFAULT_HINT_TEXT = "Target: Defeat the Boss!";

    // Style for the hint label
    private static final String HINT_STYLE = "-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold";

    private final Label hintLabel;  // Label to display the hint message

    /**
     * Default constructor using predefined text and position.
     */
    public TargetLevel() {
        this(DEFAULT_HINT_TEXT, DEFAULT_HINT_X, DEFAULT_HINT_Y);
    }

    /**
     * Constructor allowing for custom hint text and position.
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
     */
    public void show() {
        hintLabel.setVisible(true); // Make the hint visible
        this.toFront();             // Bring the hint label to the front of other elements
    }
}