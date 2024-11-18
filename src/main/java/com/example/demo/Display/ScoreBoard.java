package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ScoreBoard {

    private static final double WIDTH = 200;
    private static final double HEIGHT = 50;

    private final Pane container;
    private final Label currentKillsLabel;
    private final Label targetKillsLabel;
    private int currentKills;
    private final int targetKills;

    public ScoreBoard(double x, double y, int targetKills) {
        this.targetKills = targetKills;
        this.currentKills = 0;

        container = new Pane();
        container.setLayoutX(x);
        container.setLayoutY(y);
        container.setPrefSize(WIDTH, HEIGHT);

        currentKillsLabel = new Label("Current Kills: 0");
        currentKillsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        currentKillsLabel.setLayoutY(0);

        targetKillsLabel = new Label("Target Kills: " + targetKills);
        targetKillsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        targetKillsLabel.setLayoutY(30);

        container.getChildren().addAll(currentKillsLabel, targetKillsLabel);
    }

    public Pane getContainer() {
        return container;
    }

    public void updateCurrentKills(int kills) {
        this.currentKills = kills;
        currentKillsLabel.setText("Current Kills: " + currentKills);
    }
}