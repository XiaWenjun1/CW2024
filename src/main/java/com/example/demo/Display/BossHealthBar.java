package com.example.demo.Display;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

/**
 * A standalone class representing the Boss's health bar using an {@link HBox} layout.
 * The health bar is represented visually with a {@link ProgressBar}, without any text.
 */
public class BossHealthBar extends HBox {

    /**
     * The initial health value of the Boss.
     * This is used to calculate the health percentage for the progress bar.
     */
    private int initialHealth;

    /**
     * The {@link ProgressBar} used to represent the Boss's health.
     * This visual element is updated as the Boss's health changes.
     */
    private final ProgressBar healthBar;

    /**
     * The width of the health bar in pixels.
     * It defines how wide the progress bar will be displayed.
     */
    private static final int WIDTH = 290;

    /**
     * The height of the health bar in pixels.
     * It defines how tall the progress bar will be displayed.
     */
    private static final int HEIGHT = 10;

    /**
     * Constructor for the {@code BossHealthBar}.
     * Initializes the health bar with the specified initial health of the Boss.
     *
     * @param initialHealth The initial health of the Boss.
     */
    public BossHealthBar(int initialHealth) {
        this.initialHealth = initialHealth;
        this.healthBar = createHealthBar();
        this.getChildren().addAll(healthBar);
    }

    /**
     * Creates and styles the {@link ProgressBar} to represent the Boss's health.
     *
     * @return A styled {@link ProgressBar} object.
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
     * The health bar's progress will be updated based on the current health.
     *
     * @param bossHealth The current health of the Boss.
     */
    public void updateHealth(int bossHealth) {
        double progress = (double) bossHealth / initialHealth;
        this.healthBar.setProgress(progress);
    }

    /**
     * Returns the current health of the boss.
     *
     * @return the current health of the boss.
     */
    public int getCurrentHealth() {
        return initialHealth;
    }

    /**
     * Sets the position of the health bar on the screen.
     *
     * @param xPosition The X position for the health bar.
     * @param yPosition The Y position for the health bar.
     */
    public void setLayout(double xPosition, double yPosition) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Hides the Boss's health bar from the screen.
     */
    public void hide() {
        this.setVisible(false);
    }

    /**
     * Shows the Boss's health bar on the screen.
     * The health bar will be brought to the front of the view.
     */
    public void show() {
        this.setVisible(true);
        this.toFront();
    }
}