package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A class representing a ScoreBoard that displays the player's current kills and target kills.
 * The ScoreBoard is modeled using an HBox and contains a label to show kill statistics.
 * It is used to display the player's progress in terms of kills during gameplay.
 */
public class ScoreBoard extends HBox {

    /**
     * Width constant for the ScoreBoard.
     */
    private static final int WIDTH = 200;

    /**
     * Height constant for the ScoreBoard.
     */
    private static final int HEIGHT = 50;

    /**
     * Initial x-coordinate position for the ScoreBoard on the screen.
     */
    private static final double INITIAL_X_POSITION = 10;

    /**
     * Initial y-coordinate position for the ScoreBoard on the screen.
     */
    private static final double INITIAL_Y_POSITION = 690;

    /**
     * Label to display the current and target kill information.
     */
    private final Label killInfoLabel;

    /**
     * The current number of kills.
     */
    private int currentKills;

    /**
     * The target number of kills required to reach the next stage or level.
     */
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
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: White; -fx-font-weight: bold;");
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

    /**
     * Returns the current number of kills displayed on the scoreboard.
     *
     * @return the current number of kills.
     */
    public int getCurrentKills() {
        return currentKills;
    }

    /**
     * Returns the target number of kills required for progression.
     *
     * @return the target number of kills.
     */
    public int getTargetKills() {
        return targetKills;
    }
}