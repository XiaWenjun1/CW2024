package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.Boss.Boss;
import com.example.demo.controller.Control_PauseMenu;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;

public class PauseMenuManager {

    private Parent pauseMenuRoot;
    private Control_PauseMenu controlPauseMenu;
    private boolean isPaused = false;
    private Timeline timeline;
    private Scene scene;
    private UserInputManager userInputManager;

    public PauseMenuManager(Timeline timeline, Scene scene, UserInputManager userInputManager) {
        this.timeline = timeline;
        this.scene = scene;
        this.userInputManager = userInputManager;
    }

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

    public void togglePause() {
        if (isPaused) {
            continueGame();
        } else {
            pauseGame();
        }
    }

    public void pauseGame() {
        isPaused = true;
        timeline.stop();
        userInputManager.setPaused(true);

        // Apply blur to all elements except the pause menu root
        applyBlurToAllElementsExceptPauseMenu(pauseMenuRoot, 10);

        controlPauseMenu.showPauseMenu();
    }

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
     * @param node The node to apply the effect on.
     * @param radius The radius of the blur effect.
     */
    private void setBlurEffect(Node node, double radius) {
        if (node instanceof ActiveActorDestructible) {
            ((ActiveActorDestructible) node).setEffect(new GaussianBlur(radius));
        } else if (node instanceof Boss) {
            Boss boss = (Boss) node;
            if (boss.getHealthBar() != null) {
                boss.getHealthBar().setEffect(new GaussianBlur(radius));
            }
            if (boss.getShieldImage() != null) {
                boss.getShieldImage().setEffect(new GaussianBlur(radius));
            }
        } else {
            node.setEffect(new GaussianBlur(radius));
        }
    }

    /**
     * Remove the Gaussian blur effect from a node.
     * @param node The node to remove the effect from.
     */
    private void removeBlurEffect(Node node) {
        if (node instanceof ActiveActorDestructible) {
            ((ActiveActorDestructible) node).setEffect(null);
        } else if (node instanceof Boss) {
            Boss boss = (Boss) node;
            if (boss.getHealthBar() != null) {
                boss.getHealthBar().setEffect(null);
            }
            if (boss.getShieldImage() != null) {
                boss.getShieldImage().setEffect(null);
            }
        } else {
            node.setEffect(null);
        }
    }

    public Parent getPauseMenuRoot() {
        return pauseMenuRoot;
    }
}