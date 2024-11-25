package com.example.demo.Display;

import javafx.application.Platform;
import javafx.scene.Group;

public class LevelViewLevelOne extends LevelView {

    private final Group root;
    private final ScoreBoard scoreBoard;

    public LevelViewLevelOne(Group root, int heartsToDisplay, int initialKills, int targetKills) {
        super(root, heartsToDisplay);
        this.root = root;

        this.scoreBoard = new ScoreBoard(initialKills, targetKills);
        initializeUI();
    }

    private void initializeUI() {
        root.getChildren().add(scoreBoard);
        Platform.runLater(() -> {
            scoreBoard.show();
        });
    }

    // Update the kills on the scoreboard
    public void updateKills(int currentKills, int targetKills) {
        scoreBoard.updateKills(currentKills, targetKills);
    }
}