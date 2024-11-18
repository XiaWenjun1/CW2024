package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import com.example.demo.Level.LevelParent;
import com.example.demo.Level.PauseMenuManager;

public class Control_PauseMenu {

    private PauseMenuManager pauseMenuManager;

    @FXML
    private AnchorPane pauseMenuPane;

    @FXML
    private Button continueButton;

    private Pane mainRoot;

    public void initialize(PauseMenuManager pauseMenuManager) {
        this.pauseMenuManager = pauseMenuManager;

        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
    }

    public void showPauseMenu() {
        boolean isVisible = pauseMenuPane.isVisible();
        pauseMenuPane.setVisible(!isVisible);
        continueButton.setVisible(!isVisible);
    }

    @FXML
    private void handleContinue() {
        pauseMenuManager.continueGame();
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);

        if (mainRoot != null) {
            mainRoot.setEffect(null);
        }
    }

    public void hidePauseMenu() {
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
    }
}