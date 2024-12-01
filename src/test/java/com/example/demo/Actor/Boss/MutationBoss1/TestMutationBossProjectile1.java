package com.example.demo.Actor.Boss.MutationBoss1;

import com.example.demo.Actor.Boss.ParentBoss.BossProjectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutationBossProjectile1Test {

    private MutationBossProjectile1 mutationBossProjectile1;

    @BeforeEach
    void setUp() {
        mutationBossProjectile1 = new MutationBossProjectile1(150, 250);
    }

    @Test
    void testImageSetCorrectly() {
        try {
            var field = BossProjectile.class.getDeclaredField("imageName");
            field.setAccessible(true);
            String actualImageName = (String) field.get(mutationBossProjectile1);

            assertEquals("mutationBossProjectile1.png", actualImageName,
                    "Image name should be 'mutationBossProjectile1.png'");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Unable to access the imageName field in BossProjectile: " + e.getMessage());
        }
    }
}