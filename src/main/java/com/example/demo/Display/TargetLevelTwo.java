package com.example.demo.Display;

import javafx.scene.Group;
import javafx.scene.control.Label;

public class TargetLevelTwo {

    private static final double HINT_TEXT_X_POSITION = 10;
    private static final double HINT_TEXT_Y_POSITION = 720;
    private final Group root;
    private final Label hintLabel;

    public TargetLevelTwo(Group root) {
        this.root = root;
        this.hintLabel = createHintLabel();
        addHintLabelToRoot();
    }

    private Label createHintLabel() {
        Label label = new Label("Target: Defeat the Boss!");
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold");
        label.setLayoutX(HINT_TEXT_X_POSITION);
        label.setLayoutY(HINT_TEXT_Y_POSITION);
        label.setVisible(false);
        return label;
    }

    public void showHint() {
        javafx.application.Platform.runLater(() -> {
            hintLabel.setVisible(true);
        });
    }

    public void hideHint() {
        javafx.application.Platform.runLater(() -> hintLabel.setVisible(false));
    }

    private void addHintLabelToRoot() {
        root.getChildren().add(hintLabel);
    }
}