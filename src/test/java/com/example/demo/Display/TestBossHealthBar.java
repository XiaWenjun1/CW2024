package com.example.demo.Display;

import javafx.scene.control.ProgressBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBossHealthBar {

    private BossHealthBar healthBar;

    @BeforeEach
    void setUp() {
        // Create a BossHealthBar instance, assuming the initial health value is 100
        healthBar = new BossHealthBar(100);
    }

    @Test
    void testInitialHealthBar() {
        // Check initial progress of health bar
        ProgressBar bar = (ProgressBar) healthBar.getChildren().get(0);
        assertEquals(1.0, bar.getProgress(), "The health bar should be full initially.");
    }

    @Test
    void testUpdateHealth() {
        // Update the health value and check the progress of the progress bar
        healthBar.updateHealth(50);
        ProgressBar bar = (ProgressBar) healthBar.getChildren().get(0);
        assertEquals(0.5, bar.getProgress(), "The health bar should reflect the new health progress.");

        healthBar.updateHealth(0);
        assertEquals(0.0, bar.getProgress(), "The health bar should reflect 0 progress when health is 0.");
    }

    @Test
    void testSetLayout() {
        // Set the position and verify it is correct
        healthBar.setLayout(100, 200);
        assertEquals(100, healthBar.getLayoutX(), "The X position should be set correctly.");
        assertEquals(200, healthBar.getLayoutY(), "The Y position should be set correctly.");
    }

    @Test
    void testShowAndHide() {
        // Test the show and hide functions
        assertTrue(healthBar.isVisible(), "The health bar should be visible initially.");

        healthBar.hide();
        assertFalse(healthBar.isVisible(), "The health bar should be hidden after calling hide().");

        healthBar.show();
        assertTrue(healthBar.isVisible(), "The health bar should be visible after calling show().");
    }
}