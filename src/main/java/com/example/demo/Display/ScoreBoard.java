package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A standalone class representing the ScoreBoard, modeled after BossHealthBar.
 */
public class ScoreBoard extends HBox {

    private static final int WIDTH = 200;  // Width of the ScoreBoard
    private static final int HEIGHT = 50; // Height of the ScoreBoard
    private static final double INITIAL_X_POSITION = 10;
    private static final double INITIAL_Y_POSITION = 690;

    private final Label killInfoLabel; // Label to display the kills information
    private int currentKills;          // Current number of kills
    private int targetKills;           // Target number of kills to reach

    /**
     * Constructor for the ScoreBoard.
     *
     * @param currentKills Initial number of kills.
     * @param targetKills  Total kills required to advance.
     */
    public ScoreBoard(int currentKills, int targetKills) {
        this.currentKills = currentKills;
        this.targetKills = targetKills;

        // Initialize and style the label
        this.killInfoLabel = createKillInfoLabel();

        // Add the label to the HBox
        this.getChildren().add(killInfoLabel);

        // Set layout and dimensions
        this.setPrefSize(WIDTH, HEIGHT);
        this.setLayoutX(INITIAL_X_POSITION);
        this.setLayoutY(INITIAL_Y_POSITION);
    }

    /**
     * Creates and styles the label to display kill info.
     *
     * @return A styled Label object.
     */
    private Label createKillInfoLabel() {
        Label label = new Label("Kills: " + currentKills + " / " + targetKills);
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;");
        return label;
    }

    /**
     * Updates the current and target kills displayed on the scoreboard.
     *
     * @param currentKills The updated number of kills.
     * @param targetKills  The updated target number of kills.
     */
    public void updateKills(int currentKills, int targetKills) {
        this.currentKills = currentKills;
        this.targetKills = targetKills;
        this.killInfoLabel.setText("Kills: " + currentKills + " / " + targetKills);
    }

    /**
     * Shows the ScoreBoard and brings it to the front.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();
    }
}