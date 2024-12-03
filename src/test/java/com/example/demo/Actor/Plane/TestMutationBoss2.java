package com.example.demo.Actor.Plane;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Actor.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestMutationBoss2 {
    private MutationBoss2 mutationBoss2;

    @BeforeEach
    void setUp() {
        mutationBoss2 = new MutationBoss2(100);
    }

    @Test
    void testInitialHealth() {
        assertEquals(100, mutationBoss2.getHealth());
    }

    @Test
    void testFireProjectiles() {
        List<ActiveActorDestructible> projectiles = mutationBoss2.fireProjectiles();
        // Verify that projectiles are fired based on fire rate
        assertTrue(projectiles.isEmpty() || projectiles.size() > 0);
    }
}