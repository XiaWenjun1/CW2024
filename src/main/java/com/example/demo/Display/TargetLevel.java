package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TargetLevel extends Pane {

    private static final double DEFAULT_HINT_X = 10;
    private static final double DEFAULT_HINT_Y = 720;
    private static final String DEFAULT_HINT_TEXT = "Target: Defeat the Boss!";
    private static final String HINT_STYLE = "-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold";

    private final Label hintLabel;

    public TargetLevel() {
        this(DEFAULT_HINT_TEXT, DEFAULT_HINT_X, DEFAULT_HINT_Y);
    }

    public TargetLevel(String hintText, double x, double y) {
        this.hintLabel = createHintLabel(hintText, x, y);
        this.getChildren().addAll(hintLabel); // Add label directly to the pane
    }

    private Label createHintLabel(String hintText, double x, double y) {
        Label label = new Label(hintText);
        label.setStyle(HINT_STYLE);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setVisible(false); // Initially hidden
        return label;
    }

    public void show() {
        hintLabel.setVisible(true); // Show the hint
        this.toFront();  // Bring it to the front in case other elements overlap
    }
}