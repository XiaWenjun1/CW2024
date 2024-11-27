package com.example.demo.Object;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * The {@code Boundary} class represents the boundaries of the game area.
 * It extends {@link Rectangle} and is used to create the left and right boundary markers for the game.
 * The boundaries ensure that the objects within the game do not move outside the defined area.
 * <p>
 * This class provides methods to create the left and right boundaries with predefined positions and sizes.
 * It also allows for potential future extension to dynamically update the boundary's position.
 * </p>
 */
public class Boundary extends Rectangle {

    /**
     * The X position for the right boundary.
     * This constant is used to set the position of the right boundary at the edge of the game area.
     */
    public static final double RIGHT_BOUNDARY_X = 1350;

    /**
     * The X position for the left boundary.
     * This constant is used to set the position of the left boundary at the left edge of the game area.
     */
    public static final double LEFT_BOUNDARY_X = -50;

    /**
     * The Y position for both boundaries.
     * This constant is used to set the vertical position for both the left and right boundaries,
     * ensuring they are aligned along the same horizontal line.
     */
    public static final double BOUNDARY_Y = 0;

    /**
     * The width of the boundary.
     * This constant defines the width of the boundary, which is set to 1px to make it invisible.
     */
    public static final double BOUNDARY_WIDTH = 1;

    /**
     * The height of the boundary.
     * This constant defines the height of the boundary, ensuring it extends from the top to the bottom of the game area.
     */
    public static final double BOUNDARY_HEIGHT = 1000;

    /**
     * Constructs a new {@code Boundary} with the specified position and dimensions.
     * <p>
     * The boundary is created as an invisible rectangle with the specified width and height.
     * </p>
     *
     * @param initialXPos The initial X position of the boundary.
     * @param initialYPos The initial Y position of the boundary.
     * @param width The width of the boundary.
     * @param height The height of the boundary.
     */
    public Boundary(double initialXPos, double initialYPos, double width, double height) {
        super(width, height);
        this.setX(initialXPos);
        this.setY(initialYPos);
        this.setFill(Color.TRANSPARENT);  // Set the boundary as invisible
    }

    /**
     * Creates and returns a new {@code Boundary} representing the right boundary.
     * <p>
     * The right boundary is positioned at the {@code RIGHT_BOUNDARY_X} constant with the predefined height and width.
     * </p>
     *
     * @return A new {@code Boundary} object representing the right boundary.
     */
    public static Boundary createRightBoundary() {
        return new Boundary(RIGHT_BOUNDARY_X, BOUNDARY_Y, BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
    }

    /**
     * Creates and returns a new {@code Boundary} representing the left boundary.
     * <p>
     * The left boundary is positioned at the {@code LEFT_BOUNDARY_X} constant with the predefined height and width.
     * </p>
     *
     * @return A new {@code Boundary} object representing the left boundary.
     */
    public static Boundary createLeftBoundary() {
        return new Boundary(LEFT_BOUNDARY_X, BOUNDARY_Y, BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
    }

    /**
     * Updates the position of the boundary.
     * <p>
     * This method is currently not in use but is provided for potential future extensions
     * where dynamic position updates might be required (e.g., for moving boundaries).
     * </p>
     */
    public void updatePosition() {
        // Method can be extended for dynamic position updates.
    }
}