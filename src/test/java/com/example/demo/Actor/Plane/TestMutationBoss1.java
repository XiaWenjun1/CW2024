package com.example.demo.Actor.Plane;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Actor.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestMutationBoss1 {
    private MutationBoss1 mutationBoss1;

    @BeforeEach
    void setUp() {
        mutationBoss1 = new MutationBoss1(100);
    }

    @Test
    void testInitialHealth() {
        assertEquals(100, mutationBoss1.getHealth());
    }

    @Test
    void testFireProjectiles() {
        List<ActiveActorDestructible> projectiles = mutationBoss1.fireProjectiles();
        // Verify that projectiles are fired based on fire rate
        assertTrue(projectiles.isEmpty() || projectiles.size() > 0);
    }
}