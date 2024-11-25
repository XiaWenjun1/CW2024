package com.example.demo.Object.Boss;

import com.example.demo.Actor.ActiveActorDestructible;

import java.util.ArrayList;
import java.util.List;

public class MutationBoss1 extends Boss {

    private static final String MUTATION_BOSS_IMAGE_NAME = "mutation1.png";
    private static final int IMAGE_WIDTH = 250; // Width of the boss image
    private static final int IMAGE_HEIGHT = 250; // Height of the boss image
    private static final double BOSS_FIRE_RATE = 0.05; // Probability of firing a projectile in each frame

    private final BossFirePattern firePattern; // The boss's fire pattern manager

    public MutationBoss1(int initialHealth) {
        super(initialHealth);
        this.firePattern = new BossFirePattern(this);
        changeImage(MUTATION_BOSS_IMAGE_NAME);
        setImageSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    private void setImageSize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }

    @Override
    public List<ActiveActorDestructible> fireProjectiles() {
        if (!bossFiresInCurrentFrame()) {
            return new ArrayList<>();
        }

        int attackType = firePattern.selectAttackType();
        return switch (attackType) {
            case 1 -> firePattern.createStraightProjectile();
            case 2 -> firePattern.createTwoProjectiles();
            default -> new ArrayList<>();
        };
    }

    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE;
    }
}