package com.example.demo.Object.Boss;

import com.example.demo.Level.LevelParent;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

/**
 * Represents the health bar for the Boss in the game.
 * The health bar visually displays the Boss's current health as a percentage.
 */
public class BossHealthBar {

    private static final int IMAGE_WIDTH = 300; // Width of the health bar
    private final ProgressBar healthBar;        // Progress bar representing the health
    private final Boss boss;                    // Reference to the Boss object

    /**
     * Constructs a BossHealthBar object.
     *
     * @param boss The Boss whose health is represented by this health bar.
     */
    public BossHealthBar(Boss boss) {
        this.boss = boss;
        this.healthBar = createHealthBar();
        this.healthBar.setProgress(1.0); // Start with full health
    }

    /**
     * Creates and styles the health bar.
     *
     * @return A styled ProgressBar object.
     */
    private ProgressBar createHealthBar() {
        ProgressBar bar = new ProgressBar(1.0);
        bar.setPrefWidth(IMAGE_WIDTH);          // Set width to match the boss image
        bar.setPrefHeight(10);                  // Set a fixed height for the health bar
        bar.setStyle("-fx-accent: red; -fx-background-color: lightgray;"); // Style the health bar
        return bar;
    }

    /**
     * Initializes the health bar and adds it to the game scene.
     *
     * @param boss        The Boss whose health is represented.
     * @param levelParent The parent level that manages the game scene.
     * @return The initialized BossHealthBar object.
     */
    public static BossHealthBar initialize(Boss boss, LevelParent levelParent) {
        BossHealthBar healthBar = new BossHealthBar(boss);
        if (levelParent != null && levelParent.getRoot() != null) {
            Platform.runLater(() -> levelParent.getRoot().getChildren().add(healthBar.getHealthBar()));
        }
        return healthBar;
    }

    /**
     * Updates the health bar's position and progress to reflect the Boss's current health.
     * The position is set slightly below the Boss's current position.
     */
    public void update() {
        double healthPercentage = Math.max(0, (double) boss.getHealth() / boss.getMAXHealth());
        healthBar.setProgress(healthPercentage);   // Update progress to match health percentage
        healthBar.setLayoutX(boss.getBossXPosition()); // Align the X position with the Boss
        healthBar.setLayoutY(boss.getBossYPosition() + 60); // Position slightly below the Boss
    }

    /**
     * Returns the ProgressBar representing the Boss's health.
     *
     * @return The health bar.
     */
    public ProgressBar getHealthBar() {
        return healthBar;
    }

    /**
     * Hides the health bar, making it invisible in the game scene.
     */
    public void hide() {
        healthBar.setVisible(false);
    }
}