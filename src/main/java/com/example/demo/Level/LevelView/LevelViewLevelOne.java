package com.example.demo.Level.LevelView;

import com.example.demo.Display.ScoreBoard;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the level view for Level 1, responsible for managing and displaying elements
 * such as the score board and other level-specific UI elements.
 */
public class LevelViewLevelOne extends LevelView {

    private final Group root;
    private final ScoreBoard scoreBoard;

    /**
     * Constructs a LevelViewLevelOne instance with the specified root group, hearts to display,
     * and the initial and target kills for the scoreboard.
     *
     * @param root the root container of the scene where the UI elements will be added.
     * @param heartsToDisplay the initial number of hearts to display on the UI.
     * @param initialKills the initial number of kills to display on the scoreboard.
     * @param targetKills the target number of kills required to complete the level.
     */
    public LevelViewLevelOne(Group root, int heartsToDisplay, int initialKills, int targetKills) {
        super(root, heartsToDisplay);
        this.root = root;

        this.scoreBoard = new ScoreBoard(initialKills, targetKills);
        initializeUI();
    }

    /**
     * Initializes the user interface for Level 1 by adding the score board to the root container.
     * This method runs on the JavaFX application thread to ensure UI updates are executed correctly.
     */
    private void initializeUI() {
        root.getChildren().add(scoreBoard);
        Platform.runLater(() -> {
            scoreBoard.show();  // Show the score board after UI initialization
        });
    }

    /**
     * Updates the kills displayed on the scoreboard. This method reflects the current and target kills.
     *
     * @param currentKills the current number of kills to display.
     * @param targetKills the target number of kills to display.
     */
    public void updateKills(int currentKills, int targetKills) {
        scoreBoard.updateKills(currentKills, targetKills);
    }
}