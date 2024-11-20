package com.example.demo.Object;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Boundary extends Rectangle {

    public Boundary(double initialXPos, double initialYPos, double width, double height) {
        super(width, height);
        this.setX(initialXPos);
        this.setY(initialYPos);
        this.setFill(Color.TRANSPARENT);
    }

    public void updatePosition() {}
}