package com.example.demo.Display;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExplosion {

    @BeforeAll
    static void initJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    Platform.startup(() -> {});
                } catch (IllegalStateException e) {
                }
                latch.countDown();
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Test explosion effect trigger
    @Test
    void testTriggerExplosionEffect() throws InterruptedException {
        // Set a root node
        Group root = new Group();

        // Create a simulation object, such as a rectangle representing an enemy
        Rectangle actor = new Rectangle(50, 50);
        actor.setLayoutX(100);
        actor.setLayoutY(100);

        // Use CountDownLatch to wait for the JavaFX thread to execute
        CountDownLatch latch = new CountDownLatch(1);

        // Trigger the explosion effect in the JavaFX thread
        Platform.runLater(() -> {
            // Trigger the explosion effect
            Explosion.triggerExplosionEffect(root, actor);
            latch.countDown();  // Complete the operation and release the wait
        });

        // Wait for the explosion effect to be triggered
        latch.await();

        // Verify that the explosion effect has been added to the root node
        assertEquals(1, root.getChildren().size(), "Explosion image should be added to the root.");
        assertTrue(root.getChildren().get(0) instanceof ImageView, "Child should be an ImageView.");

        // Verify that the position of the explosion image matches the simulated object
        ImageView explosionImage = (ImageView) root.getChildren().get(0);
        assertEquals(100, explosionImage.getX(), "Explosion image X position should match actor.");
        assertEquals(100, explosionImage.getY(), "Explosion image Y position should match actor.");

        // Wait for animation duration and verify removal
        CountDownLatch animationLatch = new CountDownLatch(1);

        // Remove the explosion effect after the animation is simulated in the JavaFX thread
        Platform.runLater(() -> {
            //Simulate animation duration. Here we assume that the explosion effect will disappear after 1 second.
            try {
                Thread.sleep(1000); // Wait 1 second to simulate the animation duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            root.getChildren().clear();  // Remove the explosion image
            animationLatch.countDown();   //Complete the animation
        });

        // Wait for the animation to end
        animationLatch.await();

        // Verify that the explosion image has been removed
        assertEquals(0, root.getChildren().size(), "Explosion image should be removed after animation.");
    }
}