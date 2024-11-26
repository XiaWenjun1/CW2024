package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A class representing a ScoreBoard that displays the player's current kills and target kills.
 * The ScoreBoard is modeled using an HBox and contains a label to show kill statistics.
 */
public class ScoreBoard extends HBox {

    // Width and height constants for the ScoreBoard
    private static final int WIDTH = 200;
    private static final int HEIGHT = 50;

    // Initial position constants for the ScoreBoard
    private static final double INITIAL_X_POSITION = 10;
    private static final double INITIAL_Y_POSITION = 690;

    // Label to display the current and target kill information
    private final Label killInfoLabel;

    // Current number of kills and the target number of kills to reach
    private int currentKills;
    private int targetKills;

    /**
     * Constructor for the ScoreBoard. It initializes the current and target kills and creates the label.
     *
     * @param currentKills The initial number of kills.
     * @param targetKills  The target number of kills required to reach the next stage or level.
     */
    public ScoreBoard(int currentKills, int targetKills) {
        this.currentKills = currentKills;
        this.targetKills = targetKills;

        // Create and style the label for displaying kills information
        this.killInfoLabel = createKillInfoLabel();

        // Add the label to the ScoreBoard container (HBox)
        this.getChildren().add(killInfoLabel);

        // Set the dimensions and layout position of the ScoreBoard
        this.setPrefSize(WIDTH, HEIGHT);
        this.setLayoutX(INITIAL_X_POSITION);
        this.setLayoutY(INITIAL_Y_POSITION);
    }

    /**
     * Creates and styles the label that displays the kills information.
     *
     * @return A Label object that displays the current kills and target kills.
     */
    private Label createKillInfoLabel() {
        Label label = new Label("Kills: " + currentKills + " / " + targetKills);
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;");
        return label;
    }

    /**
     * Updates the displayed kills and target kills on the scoreboard.
     * This method is called when the player's kills or the target kills change.
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
     * Makes the ScoreBoard visible and brings it to the front of the scene.
     * This method is called when the ScoreBoard should be displayed during the game.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();  // Brings the ScoreBoard to the front of other nodes
    }
}