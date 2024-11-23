package com.example.demo.Object.Boss;

import com.example.demo.Level.LevelParent;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The BossShield class manages the shield mechanics for the Boss,
 * including activation, deactivation, and visibility control.
 */
public class BossShield {
    private static final String IMAGE_NAME = "/com/example/demo/images/shield.png"; // Path to the shield image
    private static final int SHIELD_SIZE = 100; // Size of the shield
    private static final double SHIELD_PROBABILITY = 0.001; // Probability of shield activation per frame
    private static final int MAX_FRAMES_WITH_SHIELD = 200; // Maximum frames the shield remains active

    private final ImageView shieldImage; // The shield's visual representation
    private final Boss boss; // Reference to the associated Boss instance
    private boolean isShielded; // Indicates whether the shield is active
    private int framesWithShieldActivated; // Tracks the number of frames the shield has been active

    /**
     * Constructs a BossShield object for the given Boss.
     *
     * @param boss The Boss associated with this shield.
     */
    public BossShield(Boss boss) {
        this.boss = boss;
        this.shieldImage = initializeShieldImage();
    }

    /**
     * Creates and styles the shield's image representation.
     *
     * @return An ImageView object representing the shield.
     */
    private ImageView initializeShieldImage() {
        ImageView imageView = new ImageView(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        imageView.setFitWidth(SHIELD_SIZE);
        imageView.setFitHeight(SHIELD_SIZE);
        imageView.setVisible(false);
        return imageView;
    }

    /**
     * Initializes the BossShield and adds its visual representation to the game scene.
     *
     * @param boss        The Boss associated with this shield.
     * @param levelParent The LevelParent managing the game scene.
     * @return The initialized BossShield object.
     */
    public static BossShield initialize(Boss boss, LevelParent levelParent) {
        BossShield shield = new BossShield(boss);
        if (levelParent != null && levelParent.getRoot() != null) {
            Platform.runLater(() -> levelParent.getRoot().getChildren().add(shield.getShieldImage()));
        }
        return shield;
    }

    /**
     * Updates the shield's state, including its position, activation, and deactivation logic.
     */
    public void update() {
        // Center the shield on the Boss
        shieldImage.setLayoutX(boss.getBossXPosition() - (SHIELD_SIZE - boss.getBossWidth()) / 2);
        shieldImage.setLayoutY(boss.getBossYPosition());

        if (isShielded) {
            framesWithShieldActivated++;
            if (framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD) {
                deactivateShield(); // Deactivate shield after maximum duration
            }
        } else if (shouldActivateShield()) {
            activateShield(); // Activate shield based on probability
        }
    }

    /**
     * Determines if the shield should activate based on a random probability.
     *
     * @return True if the shield should activate, false otherwise.
     */
    private boolean shouldActivateShield() {
        return Math.random() < SHIELD_PROBABILITY;
    }

    /**
     * Activates the shield, making it visible and resetting its active frame counter.
     */
    private void activateShield() {
        isShielded = true;
        shieldImage.setVisible(true); // Show the shield
        shieldImage.toFront(); // Ensure the shield appears above other elements
        framesWithShieldActivated = 0; // Reset activation duration
    }

    /**
     * Deactivates the shield, making it invisible.
     */
    private void deactivateShield() {
        isShielded = false;
        shieldImage.setVisible(false); // Hide the shield
    }

    /**
     * Returns the ImageView object representing the shield.
     *
     * @return The shield's ImageView.
     */
    public ImageView getShieldImage() {
        return shieldImage;
    }

    /**
     * Checks if the shield is currently active.
     *
     * @return True if the shield is active, false otherwise.
     */
    public boolean isShielded() {
        return isShielded;
    }
}