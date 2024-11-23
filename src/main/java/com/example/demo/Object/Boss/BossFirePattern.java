package com.example.demo.Object.Boss;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

import java.util.ArrayList;
import java.util.List;

/**
 * The BossFirePattern class represents the firing patterns for the boss in the game.
 * It handles the generation of projectiles for different attack types, such as straight, scatter, and directional.
 */
public class BossFirePattern {

    private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0; // Vertical offset for projectile spawn

    private final LevelParent levelParent; // The level parent managing the game scene
    private final Boss boss; // The boss object that fires projectiles

    /**
     * Constructs a BossFirePattern object that controls the firing patterns of the boss.
     *
     * @param levelParent The level parent that manages the game scene and root elements.
     * @param boss The boss object that will fire projectiles.
     */
    public BossFirePattern(LevelParent levelParent, Boss boss) {
        this.levelParent = levelParent;
        this.boss = boss;
    }

    /**
     * Randomly selects an attack type for the boss.
     *
     * @return An integer representing the attack type:
     *         1 for straight, 2 for scatter, 3 for directional.
     */
    public int selectAttackType() {
        return (int) (Math.random() * 3) + 1;
    }

    /**
     * Creates a straight line of projectiles fired by the boss.
     *
     * @return A list containing a single straight projectile.
     */
    public List<ActiveActorDestructible> createStraightProjectile() {
        double projectileYPosition = boss.getBossYPosition() + PROJECTILE_Y_POSITION_OFFSET;
        ActiveActorDestructible projectile = new BossProjectile(boss.getBossXPosition(), projectileYPosition, levelParent);

        return List.of(projectile);
    }

    /**
     * Creates a scatter shot of projectiles fired by the boss.
     *
     * @return A list of projectiles spread out vertically.
     */
    public List<ActiveActorDestructible> createScatterProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double[] yOffsets = {-50, 0, 50};

        for (double yOffset : yOffsets) {
            double projectileYPosition = boss.getBossYPosition() + yOffset;
            BossProjectile projectile = new BossProjectile(boss.getBossXPosition(), projectileYPosition, levelParent);
            projectile.setVelocity(-5, 0);

            if (!levelParent.getRoot().getChildren().contains(projectile.getHitbox())) {
                levelParent.getRoot().getChildren().add(projectile.getHitbox());
            }

            projectiles.add(projectile);
        }
        return projectiles;
    }

    /**
     * Creates a directional shot of projectiles fired by the boss in multiple directions.
     *
     * @return A list of projectiles fired straight, up-left, and down-left.
     */
    public List<ActiveActorDestructible> createDirectionalProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();

        double straightY = boss.getBossYPosition() + PROJECTILE_Y_POSITION_OFFSET;
        double leftUpY = straightY - 50;
        double leftDownY = straightY + 50;

        BossProjectile straightProjectile = new BossProjectile(boss.getBossXPosition(), straightY, levelParent);
        straightProjectile.setVelocity(-4, 0);
        projectiles.add(straightProjectile);

        BossProjectile leftUpProjectile = new BossProjectile(boss.getBossXPosition(), leftUpY, levelParent);
        leftUpProjectile.setVelocity(-4, -1);
        projectiles.add(leftUpProjectile);

        BossProjectile leftDownProjectile = new BossProjectile(boss.getBossXPosition(), leftDownY, levelParent);
        leftDownProjectile.setVelocity(-4, 1);
        projectiles.add(leftDownProjectile);

        for (BossProjectile projectile : List.of(straightProjectile, leftUpProjectile, leftDownProjectile)) {
            if (!levelParent.getRoot().getChildren().contains(projectile.getHitbox())) {
                levelParent.getRoot().getChildren().add(projectile.getHitbox());
            }
        }

        return projectiles;
    }
}