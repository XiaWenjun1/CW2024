package com.example.demo.Object.Boss;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

/**
 * A standalone class representing the Boss's health bar using HBox, without text.
 */
public class BossHealthBar extends HBox {

    private int initialHealth;      // Initial health of the Boss
    private final ProgressBar healthBar; // ProgressBar for visualizing health

    private static final int WIDTH = 290;  // Width of the health bar
    private static final int HEIGHT = 10;  // Height of the health bar

    /**
     * Constructor for the BossHealthBar.
     *
     * @param initialHealth The initial health of the Boss.
     */
    public BossHealthBar(int initialHealth) {
        this.initialHealth = initialHealth;
        this.healthBar = createHealthBar();
        this.getChildren().addAll(healthBar);
    }

    /**
     * Creates and styles the ProgressBar for the health bar.
     *
     * @return A styled ProgressBar object.
     */
    private ProgressBar createHealthBar() {
        ProgressBar bar = new ProgressBar(1.0); // Full health initially
        bar.setPrefWidth(WIDTH);
        bar.setPrefHeight(HEIGHT);
        bar.setStyle("-fx-accent: red; -fx-control-inner-background: gray;"); // Custom styles
        return bar;
    }

    /**
     * Updates the Boss's health and refreshes the health bar display.
     */
    public void updateHealth(int bossHealth) {
        double progress = (double) bossHealth / initialHealth;
        this.healthBar.setProgress(progress);
    }

    public void setLayout(double xPosition, double yPosition) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Hides the Boss's health bar.
     */
    public void hide() {
        this.setVisible(false);
    }

    /**
     * Shows the Boss's health bar.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();
    }
}