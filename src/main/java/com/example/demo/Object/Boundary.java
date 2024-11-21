package com.example.demo.Object;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Boundary extends Rectangle {
    public static final double RIGHT_BOUNDARY_X = 1350;
    public static final double LEFT_BOUNDARY_X = -50;
    public static final double BOUNDARY_Y = 0;
    public static final double BOUNDARY_WIDTH = 1;
    public static final double BOUNDARY_HEIGHT = 1000;

    public Boundary(double initialXPos, double initialYPos, double width, double height) {
        super(width, height);
        this.setX(initialXPos);
        this.setY(initialYPos);
        this.setFill(Color.TRANSPARENT);
    }

    public static Boundary createRightBoundary() {
        return new Boundary(RIGHT_BOUNDARY_X, BOUNDARY_Y, BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
    }

    public static Boundary createLeftBoundary() {
        return new Boundary(LEFT_BOUNDARY_X, BOUNDARY_Y, BOUNDARY_WIDTH, BOUNDARY_HEIGHT);
    }

    public void updatePosition() {}
}