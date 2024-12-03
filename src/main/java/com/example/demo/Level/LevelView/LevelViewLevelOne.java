package com.example.demo.Level.LevelView;

import com.example.demo.Display.ScoreBoard;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the level view for Level 1, responsible for managing and displaying elements
 * such as the score board and other level-specific UI elements.
 * This class is designed to manage and display the UI elements for Level 1 of the game, which primarily
 * consists of a scoreboard that tracks the number of kills achieved by the player and the target kills
 * needed to complete the level.
 */
public class LevelViewLevelOne extends LevelView {

    /**
     * The root container of the scene where the UI elements will be added.
     */
    private final Group root;

    /**
     * The scoreboard that shows the current number of kills and the target kills for the level.
     */
    private final ScoreBoard scoreBoard;

    /**
     * Constructs a LevelViewLevelOne instance with the specified root group, hearts to display,
     * and the initial and target kills for the scoreboard.
     * This constructor initializes the scoreboard and adds it to the root container of the scene.
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
     * The scoreboard is added to the root container, and its visibility is updated using {@link Platform#runLater}
     * to ensure the element is displayed properly after initialization.
     */
    private void initializeUI() {
        root.getChildren().add(scoreBoard);
        Platform.runLater(() -> {
            scoreBoard.show();  // Show the score board after UI initialization
        });
    }

    /**
     * Updates the kills displayed on the scoreboard. This method reflects the current and target kills.
     * This method is used to update the scoreboard when the player makes progress in the level,
     * and the number of kills increases.
     *
     * @param currentKills the current number of kills to display.
     * @param targetKills the target number of kills to display.
     */
    public void updateKills(int currentKills, int targetKills) {
        scoreBoard.updateKills(currentKills, targetKills);
    }

    /**
     * Returns the scoreboard associated with this level view.
     *
     * @return the scoreboard for Level 1.
     */
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}