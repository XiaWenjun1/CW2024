package com.example.demo.Display;

import com.example.demo.Level.LevelManager.AudioManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Handles explosion effects in the game, including visual animations and sound effects.
 * This class is responsible for displaying explosion animations at specific locations
 * and playing the corresponding explosion sound.
 */
public class Explosion {

    /**
     * Path template for explosion animation frames.
     * The frames should be named sequentially, e.g., explosion1.png, explosion2.png, ..., explosion20.png.
     */
    private static final String EXPLOSION_IMAGE_PATH = "/com/example/demo/images/Explosion/explosion%d.png";

    /**
     * No-argument constructor for Explosion.
     * This constructor initializes the class but requires no parameters.
     */
    public Explosion() {
        // Default constructor
    }

    /**
     * Triggers an explosion animation at the location of the specified actor.
     * The animation consists of a sequence of 20 frames and is displayed on the root node.
     * An explosion sound effect is also played when the animation starts.
     *
     * @param root  the root group where the explosion animation will be displayed
     * @param actor the actor (e.g., an enemy or object) that triggers the explosion
     *              the explosion will occur at this actor's location
     */
    public static void triggerExplosionEffect(Group root, Node actor) {
        if (actor == null || root == null) return;

        // Get the actor's position to place the explosion animation
        double x = actor.localToScene(actor.getBoundsInLocal()).getMinX();
        double y = actor.localToScene(actor.getBoundsInLocal()).getMinY();

        // Create an ImageView for displaying the explosion animation
        ImageView explosionImage = new ImageView();
        explosionImage.setFitWidth(150);
        explosionImage.setFitHeight(150);
        explosionImage.setX(x);
        explosionImage.setY(y);
        root.getChildren().add(explosionImage);

        // Total number of frames and duration of each frame (in milliseconds)
        int totalFrames = 20;
        double frameDuration = 50;

        // Create a Timeline to cycle through the explosion frames
        Timeline explosionTimeline = new Timeline();

        // Add KeyFrames to the Timeline for each explosion frame
        for (int i = 0; i < totalFrames; i++) {
            int frameIndex = i + 1; // Frame index starts from 1
            explosionTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(i * frameDuration), // Set frame timing
                            e -> explosionImage.setImage(new Image(Explosion.class.getResource(
                                    String.format(EXPLOSION_IMAGE_PATH, frameIndex)).toExternalForm())))
            );
        }

        // Remove the explosion image from the root after the animation finishes
        explosionTimeline.setOnFinished(e -> root.getChildren().remove(explosionImage));
        explosionTimeline.setCycleCount(1); // Play the animation once
        explosionTimeline.play();

        // Play explosion sound effect
        AudioManager.getInstance().triggerExplosionAudio();
    }
}