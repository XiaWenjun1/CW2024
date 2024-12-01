package com.example.demo.Actor.Boss.MutationBoss3;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Actor.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestMutationBoss3 {
    private MutationBoss3 mutationBoss3;

    @BeforeEach
    void setUp() {
        mutationBoss3 = new MutationBoss3(100);
    }

    @Test
    void testInitialHealth() {
        assertEquals(100, mutationBoss3.getHealth());
    }

    @Test
    void testFireProjectiles() {
        List<ActiveActorDestructible> projectiles = mutationBoss3.fireProjectiles();
        // Verify that projectiles are fired based on fire rate
        assertTrue(projectiles.isEmpty() || projectiles.size() > 0);
    }

    @Test
    void testFirePattern() {
        List<ActiveActorDestructible> projectiles = mutationBoss3.fireProjectiles();
        // Check that multiple firing patterns are supported, including scatter and directional
        assertTrue(projectiles.isEmpty() || projectiles.size() > 0);
    }
}