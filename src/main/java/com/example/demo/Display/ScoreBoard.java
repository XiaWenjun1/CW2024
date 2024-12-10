package com.example.demo.Display;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A class representing a ScoreBoard that displays the player's current kills.
 * In endless mode, only the current kills are displayed, while in normal mode,
 * both current and target kills are shown.
 */
public class ScoreBoard extends HBox {

    /**
     * The width of the scoreboard in pixels.
     */
    private static final int WIDTH = 200;

    /**
     * The height of the scoreboard in pixels.
     */
    private static final int HEIGHT = 50;

    /**
     * The initial x-coordinate for positioning the scoreboard on the screen.
     */
    private static final double INITIAL_X_POSITION = 10;

    /**
     * The initial y-coordinate for positioning the scoreboard on the screen.
     */
    private static final double INITIAL_Y_POSITION = 690;

    /**
     * Label used to display kill statistics on the scoreboard.
     */
    private final Label killInfoLabel;

    /**
     * The current number of kills achieved by the player.
     */
    private int currentKills;

    /**
     * The target number of kills required for level progression.
     * This is set to 0 in endless mode.
     */
    private int targetKills;

    /**
     * Indicates whether the scoreboard is operating in endless mode.
     * If true, only the current kills are displayed, and target kills are ignored.
     */
    private final boolean endlessMode;

    /**
     * Constructor for normal mode ScoreBoard.
     *
     * @param currentKills The initial number of kills.
     * @param targetKills  The target number of kills required for progression.
     */
    public ScoreBoard(int currentKills, int targetKills) {
        this.currentKills = currentKills;
        this.targetKills = targetKills;
        this.endlessMode = false;

        this.killInfoLabel = createKillInfoLabel();
        initializeScoreBoard();
    }

    /**
     * Constructor for endless mode ScoreBoard.
     *
     * @param currentKills The initial number of kills.
     */
    public ScoreBoard(int currentKills) {
        this.currentKills = currentKills;
        this.targetKills = 0; // Target kills are not used in endless mode
        this.endlessMode = true;

        this.killInfoLabel = createKillInfoLabel();
        initializeScoreBoard();
    }

    /**
     * Initializes the ScoreBoard with the appropriate layout and label.
     */
    private void initializeScoreBoard() {
        this.getChildren().add(killInfoLabel);
        this.setPrefSize(WIDTH, HEIGHT);
        this.setLayoutX(INITIAL_X_POSITION);
        this.setLayoutY(INITIAL_Y_POSITION);
    }

    /**
     * Creates and styles the label that displays the kills information.
     *
     * @return A Label object for displaying kills.
     */
    private Label createKillInfoLabel() {
        String text = endlessMode ? "Kills: " + currentKills : "Kills: " + currentKills + " / " + targetKills;
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-text-fill: White; -fx-font-weight: bold;");
        return label;
    }

    /**
     * Updates the displayed kills on the scoreboard.
     *
     * @param currentKills The updated number of kills.
     */
    public void updateKills(int currentKills) {
        this.currentKills = currentKills;
        String text = endlessMode ? "Kills: " + currentKills : "Kills: " + currentKills + " / " + targetKills;
        this.killInfoLabel.setText(text);
    }

    /**
     * Updates the displayed kills and target kills in normal mode.
     * This method is not used in endless mode.
     *
     * @param currentKills The updated number of kills.
     * @param targetKills  The updated target number of kills.
     */
    public void updateKills(int currentKills, int targetKills) {
        if (endlessMode) {
            throw new UnsupportedOperationException("updateKills with targetKills is not supported in endless mode.");
        }
        this.currentKills = currentKills;
        this.targetKills = targetKills;
        this.killInfoLabel.setText("Kills: " + currentKills + " / " + targetKills);
    }

    /**
     * Makes the ScoreBoard visible and brings it to the front of the scene.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();
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
     * Returns the target number of kills. Always returns 0 in endless mode.
     *
     * @return the target number of kills.
     */
    public int getTargetKills() {
        return endlessMode ? 0 : targetKills;
    }
}