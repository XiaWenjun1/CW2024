package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ScoreBoard {

    private static final double WIDTH = 200;
    private static final double HEIGHT = 50;
    private static final double SCOREBOARD_INITIALX = 10;
    private static final double SCOREBOARD_INITIALY = 690;

    private final Pane container;
    private final Label killInfoLabel;
    private int currentKills;
    private final int targetKills;

    public ScoreBoard(double x, double y, int targetKills) {
        this.targetKills = targetKills;
        this.currentKills = 0;

        container = new Pane();
        container.setLayoutX(x);
        container.setLayoutY(y);
        container.setPrefSize(WIDTH, HEIGHT);

        killInfoLabel = new Label(currentKills + "/" + targetKills);
        killInfoLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold");
        killInfoLabel.setLayoutY(0);

        container.getChildren().add(killInfoLabel);
    }

    public Pane getContainer() {
        return container;
    }

    public void updateCurrentKills(int kills) {
        this.currentKills = kills;
        killInfoLabel.setText("Target kill:" + currentKills + "/" + targetKills);
    }

    public static ScoreBoard createScoreBoard(int targetKills) {
        return new ScoreBoard(SCOREBOARD_INITIALX, SCOREBOARD_INITIALY, targetKills);
    }
}