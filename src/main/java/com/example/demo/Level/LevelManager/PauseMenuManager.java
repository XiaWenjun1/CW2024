package com.example.demo.Level.LevelManager;

import com.example.demo.controller.Control_PauseMenu;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;

/**
 * Manages the pause menu in the game, including pausing and resuming the game,
 * and applying/removing the blur effect to elements when the game is paused.
 */
public class PauseMenuManager {

    /** The root node of the pause menu layout. */
    private Parent pauseMenuRoot;

    /** The controller for the pause menu, responsible for handling UI interactions. */
    private Control_PauseMenu controlPauseMenu;

    /** Flag indicating whether the game is currently paused. */
    private boolean isPaused = false;

    /** The timeline controlling the game's animation. */
    private Timeline timeline;

    /** The current scene of the game. */
    private Scene scene;

    /** The user input manager to handle input when the game is paused or resumed. */
    private UserInputManager userInputManager;

    /**
     * Constructor to initialize the PauseMenuManager with the timeline, scene, and user input manager.
     *
     * @param timeline the game timeline to pause/resume
     * @param scene the current scene
     * @param userInputManager the user input manager to disable/enable input during pause
     */
    public PauseMenuManager(Timeline timeline, Scene scene, UserInputManager userInputManager) {
        this.timeline = timeline;
        this.scene = scene;
        this.userInputManager = userInputManager;
    }

    /**
     * Loads the pause menu layout from FXML and initializes the control.
     */
    public void loadPauseMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/PauseMenu/PauseMenu.fxml"));
            pauseMenuRoot = loader.load();
            controlPauseMenu = loader.getController();
            controlPauseMenu.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Toggles the pause state of the game. If the game is paused, it will continue; otherwise, it will pause.
     */
    public void togglePause() {
        if (isPaused) {
            continueGame();
        } else {
            pauseGame();
        }
    }

    /**
     * Pauses the game by stopping the timeline, disabling user input, and applying a blur effect to all elements
     * except the pause menu root.
     */
    public void pauseGame() {
        isPaused = true;
        timeline.stop();
        userInputManager.setPaused(true);

        // Apply blur to all elements except the pause menu root
        applyBlurToAllElementsExceptPauseMenu(pauseMenuRoot, 10);

        controlPauseMenu.showPauseMenu();
    }

    /**
     * Resumes the game by playing the timeline, enabling user input, and removing the blur effect from all elements
     * except the pause menu root.
     */
    public void continueGame() {
        isPaused = false;
        timeline.play();
        userInputManager.setPaused(false);

        // Remove blur from all elements except the pause menu root
        removeBlurFromAllElementsExceptPauseMenu(pauseMenuRoot);

        controlPauseMenu.hidePauseMenu();
    }

    /**
     * Apply Gaussian blur to all elements except the pause menu root.
     *
     * @param root The root node of the pause menu to exclude from blurring.
     * @param radius The radius of the blur effect.
     */
    private void applyBlurToAllElementsExceptPauseMenu(Node root, double radius) {
        for (Node node : scene.getRoot().getChildrenUnmodifiable()) {
            if (node != root) {
                setBlurEffect(node, radius);
            }
        }
    }

    /**
     * Remove Gaussian blur from all elements except the pause menu root.
     *
     * @param root The root node of the pause menu to exclude from un-blurring.
     */
    private void removeBlurFromAllElementsExceptPauseMenu(Node root) {
        for (Node node : scene.getRoot().getChildrenUnmodifiable()) {
            if (node != root) {
                removeBlurEffect(node);
            }
        }
    }

    /**
     * Set Gaussian blur effect on a node.
     *
     * @param node The node to apply the effect on.
     * @param radius The radius of the blur effect.
     */
    private void setBlurEffect(Node node, double radius) {
        node.setEffect(new GaussianBlur(radius)); // Apply blur to all nodes
    }

    /**
     * Remove the Gaussian blur effect from a node.
     *
     * @param node The node to remove the effect from.
     */
    private void removeBlurEffect(Node node) {
        node.setEffect(null); // Remove blur from all nodes
    }

    /**
     * Returns the root node of the pause menu.
     *
     * @return the pause menu root node
     */
    public Parent getPauseMenuRoot() {
        return pauseMenuRoot;
    }
}