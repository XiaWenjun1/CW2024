package com.example.demo.Object;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * The Boundary class represents the boundaries of the game area.
 * It extends {@link Rectangle} and is used to create the left and right boundary markers for the game.
 * The boundaries ensure that the objects within the game do not move outside the defined area.
 */
public class Boundary extends Rectangle {

    // Constants for the boundary dimensions and positions
    public static final double RIGHT_BOUNDARY_X = 1350;
    public static final double LEFT_BOUNDARY_X = -50;
    public static final double BOUNDARY_Y = 0;
    public static final double BOUNDARY_WIDTH = 1;
    public static final double BOUNDARY_HEIGHT = 1000;

    /**
     * Constructs a new Boundary with the specified position and dimensions.
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
     * Creates and returns a new right boundary at the specified position and size.
     *
     * @return A new {@link Boundary} representing the right boundary.
     */
    public static Boundary createRightBoundary() {
        return new Boundary(RIGHT_BOUNDARY_X, BOUNDARY_Y, BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
    }

    /**
     * Creates and returns a new left boundary at the specified position and size.
     *
     * @return A new {@link Boundary} representing the left boundary.
     */
    public static Boundary createLeftBoundary() {
        return new Boundary(LEFT_BOUNDARY_X, BOUNDARY_Y, BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
    }

    /**
     * Updates the position of the boundary.
     * This method is currently not used but can be extended for dynamic boundary adjustments.
     */
    public void updatePosition() {
        // Method can be extended for dynamic position updates.
    }
}