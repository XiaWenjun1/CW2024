package com.example.demo.Level.LevelManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * Manages the explosion effects, including the explosion image and sound.
 * This class handles the triggering of explosion effects and managing the explosion sound.
 */
public class ExplosionEffectManager {

    /** Path to the explosion sound file. */
    private static final String EXPLOSION_SOUND_PATH = "/com/example/demo/sounds/explosion.mp3";

    /** Path to the explosion image file. */
    private static final String EXPLOSION_IMAGE_PATH = "/com/example/demo/images/explosion.png";

    /** The explosion sound effect. */
    private static AudioClip explosionSound = new AudioClip(
            ExplosionEffectManager.class.getResource(EXPLOSION_SOUND_PATH).toExternalForm());

    /** Flag indicating whether the explosion sound is enabled. */
    private static boolean explosionSoundEnabled = true;

    /**
     * No-argument constructor for ExplosionEffectManager.
     * Initializes the class without needing any parameters.
     */
    public ExplosionEffectManager() {
        // Default constructor, no initialization required for static fields
    }

    /**
     * Triggers an explosion effect at the specified location for the given actor.
     * This includes displaying the explosion image and playing the explosion sound.
     *
     * @param root the root group where the explosion effect will be displayed
     * @param actor the actor that the explosion effect is triggered for
     */
    public static void triggerExplosionEffect(Group root, Node actor) {
        if (actor == null || root == null) return;

        // Get the position of the actor to place the explosion effect
        double x = actor.localToScene(actor.getBoundsInLocal()).getMinX();
        double y = actor.localToScene(actor.getBoundsInLocal()).getMinY();

        // Create the explosion image
        ImageView explosionImage = new ImageView(new Image(
                ExplosionEffectManager.class.getResource(EXPLOSION_IMAGE_PATH).toExternalForm()));
        explosionImage.setFitWidth(150);
        explosionImage.setFitHeight(150);
        explosionImage.setX(x);
        explosionImage.setY(y);

        // Add the explosion image to the scene
        root.getChildren().add(explosionImage);

        // Set a timeline to remove the explosion image after 1 second
        Timeline removeExplosion = new Timeline(new KeyFrame(Duration.seconds(1), e -> root.getChildren().remove(explosionImage)));
        removeExplosion.setCycleCount(1);
        removeExplosion.play();

        // Play the explosion sound if enabled
        if (explosionSoundEnabled) {
            explosionSound.play();
        }
    }

    /**
     * Enables or disables the explosion sound.
     *
     * @param enabled true to enable the sound, false to disable it
     */
    public static void setExplosionSoundEnabled(boolean enabled) {
        explosionSoundEnabled = enabled;
    }

    /**
     * Returns whether the explosion sound is currently enabled.
     *
     * @return true if the explosion sound is enabled, false otherwise
     */
    public static boolean isExplosionSoundEnabled() {
        return explosionSoundEnabled;
    }
}